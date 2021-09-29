import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;


class TestRestAssuredAuth {

    @Test
    void bearerTokenTest() {
        RestAssured
                .given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJjdXN0b21lcklkIjoiOTMzNTQ3NyIsImlzcyI6IjI3bmRpb2NRU1BjMW1XYjcxTG8zZVdMNGxJUm1UVTkwIiwiZXhwIjoxNjMyODE1MTgzLCJ1c2VySWQiOiIxIiwiaWF0IjoxNjMyODE0ODgzLCJyZWZyZXNoLWNvdW50IjoiMSJ9.We7q4N64TarAm-4egeJi0jx1nztD04gYPZnfFHF4SHk")
                .header("Host", "corporate-banking-dev.ibar.az")
                .get("https://corporate-banking-dev.ibar.az/statement/v1/statement/download/82")
                .then()
                .statusCode(200);
    }

    @Test
    void basicAuthTest() {
        var response = RestAssured
                .given()
                .basePath("/user")
                .baseUri("https://api.github.com")
                .auth()
                .preemptive()
                .basic("serhat.ozdursun@gmail.com", "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")
                .get();

        response.prettyPrint();
        response.then().statusCode(200);
    }
}
