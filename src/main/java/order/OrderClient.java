package order;

import io.restassured.response.ValidatableResponse;
import specifications.Specification;

public class OrderClient extends Specification {
    private final String ORDER = "/orders";
    private final String ORDER_TRACK = "/orders/track";


    public ValidatableResponse create(Order order) {
        return requestSpec()
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    public ValidatableResponse createEmptyOrder() {
        return requestSpec()
                .body("")
                .when()
                .post(ORDER)
                .then().log().all();
    }

    public ValidatableResponse getOrderViaTrack(int track) {
        return requestSpec()
                .queryParam("t", track)
                .get(ORDER_TRACK)
                .then().log().all();
    }
    public ValidatableResponse getOrder() {
        return requestSpec()
                .get(ORDER)
                .then().log().all();
    }
    public ValidatableResponse getOrderViaCourierId(int courierId) {
        return requestSpec()
                .queryParam("courierId", courierId)
                .get(ORDER)
                .then().log().all();
    }
}
