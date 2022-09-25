import io.qameta.allure.junit4.DisplayName;
import order.Order;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import specifications.Specification;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class PostOrderTests {

    OrderClient orderClient;
    Order order;
    private final String[] color;

    public PostOrderTests(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getDataForParametrizedOrder() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{""}}
        };
    }

    @Before
    public void setup() {
        order = Order.parametrizedOrder(color);
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Попытка создать заказ с выбором конкретного цвета/ов или без цвета должна завершиться успешно")
    public void attemptToCreateOrderShouldSucceed() {
        int getTrack = orderClient.create(order)
                .spec(Specification.responseSpecOK201())
                .extract().path("track");
        //Проверяем что в ответе вернулся корректный трек номер
        assertNotEquals(0, getTrack);
    }
}
