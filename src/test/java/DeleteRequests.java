import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;

public class DeleteRequests {

    public Response deleteRequests(HashMap<String,Object> headers){
        return RestAssured
                .given()
                .headers(headers)
                .delete();
    }
}
