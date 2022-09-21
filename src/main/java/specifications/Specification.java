package specifications;

import config.Config;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class Specification {

    public static RequestSpecification requestSpec() {
        return given().log().all()
                .baseUri(Config.BASE_URL)
                .contentType(ContentType.JSON);
    }
    public static ResponseSpecification responseSpecOK200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
    public static ResponseSpecification responseSpecOK201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }
    public static ResponseSpecification responseSpecConflict409() {
        return new ResponseSpecBuilder()
                .expectStatusCode(409)
                .build();
    }
    public static ResponseSpecification responseSpecBadRequest400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }
    public static ResponseSpecification responseSpecNotFound404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }
}
