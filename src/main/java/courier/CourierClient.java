package courier;

import io.restassured.response.ValidatableResponse;
import specifications.Specification;

public class CourierClient extends Specification {

    private final String ROOT = "/courier";
    private final String LOGIN = "/courier/login";
    private final String COURIER = "/courier/{courierId}";

    public ValidatableResponse create(Courier courier) {
        return requestSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();

    }

    public ValidatableResponse login(CourierCredentials creds) {
        return requestSpec()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public ValidatableResponse delete(int courierId) {
        return requestSpec()
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all();
    }
}
