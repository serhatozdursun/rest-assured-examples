import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestRestAssuredRequests {

    private final String BAS_URL = "https://petstore.swagger.io";

    @Test
    void postRequests() {
        var body = new File(getClass().getClassLoader().getResource("petPost.json").getFile());
        var response = RestAssured
                .given()
                .baseUri(BAS_URL)
                .basePath("/v2/pet")
                .headers("Cache-Control", "no-cache")
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(body)
                .post();

        response.prettyPrint();
        response.then().statusCode(200);
        var statusCode = response.statusCode();
        assertEquals(200, statusCode);
    }

    @Test
    void getRequests() throws InterruptedException {
        Thread.sleep(500);
        var response = RestAssured
                .given()
                .baseUri(BAS_URL)
                .header("accept", "application/json")
                .header("Cache-Control", "no-cache")
                .basePath("/{version}/pet/{name}")
                .pathParam("version", "v2")
                .pathParam("name", "2020202112")
                .get();

        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    void putRequest() {
        var body = new File(getClass().getClassLoader().getResource("put.json").getFile());
        var response = RestAssured
                .given()
                .baseUri(BAS_URL)
                .headers("Cache-Control", "no-cache")
                .basePath("/v2/pet")
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(body)
                .put();

        response.prettyPrint();
        response.then().statusCode(200);
        var statusCode = response.statusCode();
        assertEquals(200, statusCode);
    }

    @Test
    void deleteRequests(){

        var response = RestAssured
                .given()
                .baseUri(BAS_URL)
                .header("accept", "application/json")
                .headers("Cache-Control", "no-cache")
                .basePath("/{version}/pet/{name}")
                .pathParam("version", "v2")
                .pathParam("name", "2020202112")
                .log()
                .all()
                .delete();

        response.prettyPrint();
        response.then().statusCode(200);
    }

}
