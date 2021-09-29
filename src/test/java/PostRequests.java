import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;

public class PostRequests {

    public Response postRequests(HashMap<String,Object> params, File body){
        return RestAssured
                .given()
                .headers(params)
                .body(body)
                .post();
    }
}
