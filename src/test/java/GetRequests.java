import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;

public class GetRequests {

    public Response getRequests(HashMap<String,Object> headers){
        return RestAssured
                .given()
                .headers(headers)
                .get();
    }
}
