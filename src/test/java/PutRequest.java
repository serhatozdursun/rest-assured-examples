import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.Map;

public class PutRequest {

    public Response putRequest(Map<String, String> headers, File body) {
        return RestAssured
                .given()
                .headers(headers)
                .body(body)
                .put();
    }
}
