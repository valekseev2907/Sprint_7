import io.qameta.allure.junit4.DisplayName;
import order.Order;
import order.OrderClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import specifications.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertNotEquals;

public class GetOrderTests {

    Order order;
    OrderClient orderClient;

    @Before
    public void setup() {
    order = Order.randomOrder();
    orderClient = new OrderClient();
    }

    @Test
    @DisplayName("В ответе на запрос получения списка заказов должен вернуться НЕ пустой список")
    public void responseShouldNotReturnEmptyArrayList() {
        ArrayList <String> orderResponse = orderClient.getOrder()
                .spec(Specification.responseSpecOK200())
                .extract().path("orders");
        assertNotEquals(new ArrayList<>(0), orderResponse);
    }

    @Test
    @DisplayName("В ответе на запрос заказа по трек номеру должно вернуться НЕ пустое тело")
    public void attemptToGetOrderViaTrackShouldNotReturnEmptyMap() {
        //Создаем заказ и вытаскиваем трек номер из ответа
        int track = orderClient.create(order)
                .spec(Specification.responseSpecOK201())
                .extract().path("track");
        Assert.assertNotEquals(0, track);
        //Отправляем запрос на получение заказа по трек номеру
        Map<String, String> orderBody = orderClient.getOrderViaTrack(track)
                .spec(Specification.responseSpecOK200())
                .extract().path("order");
        assertNotEquals(Collections.emptyMap(), orderBody);
    }

    @DisplayName("В ответе на запрос ПУСТОГО заказа по трек номеру должно вернуться НЕ пустое тело (но это не точно)")
    @Test
    public void attemptToGetEmptyOrderShouldSucceed() {
        //Создаем "пустой" заказ, отправляем и вытаскиваем трек номер из тела ответа
        int track = orderClient.createEmptyOrder()
                .spec(Specification.responseSpecOK201())
                .extract().path("track");
        assertNotEquals(0, track);
        //Отправляем запрос на получение заказа по трек номеру
        Map<String, String> orderBody = orderClient.getOrderViaTrack(track)
                .spec(Specification.responseSpecOK200())
                .extract().path("order");
        assertNotEquals(Collections.emptyMap(), orderBody);
    }
}
