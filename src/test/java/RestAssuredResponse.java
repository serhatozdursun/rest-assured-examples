import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestAssuredResponse {

    RestAssuredResponse() {
        RestAssured.baseURI = "https://petstore.swagger.io";
    }

    @Test
    void responseVerify() {

        var header = new HashMap<String, Object>();
        header.put("accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Cache-Control", "max-age=0");

        RestAssured.basePath = "/v2/pet";
        var postBody = new File(getClass().getClassLoader().getResource("petPost.json").getFile());
        Response postResponse = new PostRequests().postRequests(header, postBody);

        postResponse.prettyPrint();
        postResponse.then().statusCode(200);

        postResponse.then().body("id", CoreMatchers.equalTo(2020202112));
        postResponse.then().body("category.id", CoreMatchers.equalTo(0));
        postResponse.then().body("category.name", CoreMatchers.equalTo("Dog"));
        postResponse.then().body("tags[0].id", CoreMatchers.equalTo(2));

        int id = postResponse.jsonPath().get("id");
        int tagId = postResponse.jsonPath().get("tags[0].id");
        assertEquals(2020202112, id);
        assertEquals(2, tagId);

        var responseMap = postResponse.getBody().as(new TypeRef<HashMap<String, Object>>() {
        });

        assertEquals(2020202112, responseMap.get("id"));
        String url = ((ArrayList<String>) responseMap.get("photoUrls")).get(0);
        System.out.println(url);
        assertEquals("https://www.karabas.com/karabas.jpg", url);
    }


    @Test
    void schemaValidator() {

        var header = new HashMap<String, Object>();
        header.put("accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Cache-Control", "max-age=0");

        RestAssured.basePath = "/v2/pet";
        var postBody = new File(getClass().getClassLoader().getResource("petPost.json").getFile());
        Response postResponse = new PostRequests().postRequests(header, postBody);

        postResponse.then().body(matchesJsonSchemaInClasspath("postSchema.json"));

        var fileSchema = new File(getClass().getClassLoader().getResource("postSchema.json").getFile());
        postResponse.then().body(matchesJsonSchema(fileSchema));

        var responseString = postResponse.getBody().asString();

        try {
            var schemaString = new String(Files.readAllBytes(Path.of(fileSchema.getPath())));

            MatcherAssert.assertThat(responseString, matchesJsonSchema(schemaString));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void soapTest() {
        var file = new File(getClass().getClassLoader().getResource("soapBody.xml").getFile());
        var response = RestAssured
                .given()
                .baseUri("http://www.dneonline.com")
                .basePath("/calculator.asmx")
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "http://tempuri.org/Add")
                .body(file)
                .post();

        response.prettyPrint();
        response.then().statusCode(200);

        var xpath = response.xmlPath().get("//AddResult/text()");
        int result = Integer.parseInt(String.valueOf(xpath));

        assertEquals(14, result);

    }
}
