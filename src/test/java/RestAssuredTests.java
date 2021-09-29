import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class RestAssuredTests {

    @Test
    void baseUrlAndBasePath() {
        RestAssured.basePath = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2/user";

        RestAssured
                .given()
                .baseUri("https://petstore.swagger.io")
                .basePath("/v2/user");
    }

    @Test
    void headerTests() {
        RestAssured
                .given()
                .baseUri("https://petstore.swagger.io")
                .basePath("/v2/user")
                .header("accept", "application/json")
                .header("Content-Type", "application/json");

        var headers = new HashMap<String, String>();

        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");

        RestAssured
                .given()
                .baseUri("https://petstore.swagger.io")
                .basePath("/v2/user")
                .headers(headers);
    }

    @Test
    void queryParameterTest() {

        RequestSpecification request = RestAssured
                .given()
                .baseUri("https://petstore.swagger.io")
                .basePath("/v2/user/login")
                .header("accept", "application/json");

        request.queryParam("username", "serhatozdursun")
                .queryParam("password", "1q2w3e4r5t");

        var queryParameters = new HashMap<String, String>();

        queryParameters.put("username", "serhatozdursun");
        queryParameters.put("password", "1q2w3e4r5t");

        request.headers(queryParameters);

        var response = request
                .get();

        response.prettyPrint();

        response.then().statusCode(200);

    }

    @Test
    void formParametersTest() {

        RequestSpecification request = RestAssured
                .given()
                .baseUri("https://petstore.swagger.io")
                .basePath("v2/pet/123")
                .header("accept", "application/json");

        request.formParam("petId", 123);
        request.formParam("name", "karabaş");
        request.formParam("status", "available");

        var formParams = new HashMap<String, Object>();

        formParams.put("petId", 123);
        formParams.put("name", "karabaş");
        formParams.put("status", "available");

        request.formParams(formParams);
    }

    @Test
    void pathParamsTest() {

        RequestSpecification request = RestAssured
                .given()
                .baseUri("https://petstore.swagger.io")
                .basePath("/{version}/user/{name}")
                .header("accept", "application/json");

        request.pathParam("version", "v2")
                .pathParam("name", "serhatozdursun");

        var pathParams = new HashMap<String, String>();

        pathParams.put("version", "v2");
        pathParams.put("name", "serhat");

        request.pathParams(pathParams);

        request.body(getClass().getClassLoader().getResource("userPost.json").getFile());
    }

    @Test
    void bodyAsString() {
        RequestSpecification request = RestAssured
                .given()
                .baseUri("https://petstore.swagger.io")
                .basePath("/{version}/user/{name}")
                .header("accept", "application/json");

        request.body("{\n" +
                "  \"id\": 0,\n" +
                "  \"username\": \"string\",\n" +
                "  \"firstName\": \"string\",\n" +
                "  \"lastName\": \"string\",\n" +
                "  \"email\": \"string\",\n" +
                "  \"password\": \"string\",\n" +
                "  \"phone\": \"string\",\n" +
                "  \"userStatus\": 0\n" +
                "}");
    }

    @Test
    void bodyAsMap() {
        RequestSpecification request = RestAssured
                .given()
                .baseUri("https://petstore.swagger.io")
                .basePath("/v2/user")
                .header("accept", "application/json");

        var bodyMap = new HashMap<String, Object>();

        bodyMap.put("id", 1);
        bodyMap.put("username", "serhatozdursun");
        bodyMap.put("firstName", "serhat");
        bodyMap.put("lastName", "özdursun");
        bodyMap.put("email", "serhat.ozdursun@testinium.com");
        bodyMap.put("phone", "555234234234");
        bodyMap.put("password", "1q2w3e4r");
        bodyMap.put("userStatus", 1);
        request.body(bodyMap);
    }

    @Test
    void bodyAsFile() {
        RequestSpecification request = RestAssured
                .given()
                .baseUri("https://petstore.swagger.io")
                .basePath("/v2/user")
                .header("accept", "application/json");

        var bodyFile = getClass().getClassLoader().getResource("userPost.json").getFile();

        request.body(bodyFile);
    }

    @Test
    void bodyAsMapArray() {
        RequestSpecification request = RestAssured
                .given()
                .baseUri("https://petstore.swagger.io")
                .basePath("/v2/user/createWithArray")
                .header("accept", "application/json");

        var user1 = new HashMap<String, Object>();

        user1.put("id", 1);
        user1.put("username", "serhatozdursun");
        user1.put("firstName", "serhat");
        user1.put("lastName", "özdursun");
        user1.put("email", "serhat.ozdursun@testinium.com");
        user1.put("phone", "555234234234");
        user1.put("password", "1q2w3e4r");
        user1.put("userStatus", 1);

        var user2 = new HashMap<String, Object>();

        user1.put("id", 1);
        user1.put("username", "mehmetozdursun");
        user1.put("firstName", "serhat");
        user1.put("lastName", "özdursun");
        user1.put("email", "serhat.ozdursun@testinium.com");
        user1.put("phone", "5552334234");
        user1.put("password", "1q2w3e4r");
        user1.put("userStatus", 1);

        var body = new ArrayList<HashMap<String,Object>>();
        body.add(user1);
        body.add(user2);

        request.body(body);
    }
}
