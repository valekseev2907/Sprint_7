import courier.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import specifications.Response;
import specifications.Specification;

import static org.junit.Assert.*;

public class PostCourierTests {
    Courier courier;
    CourierClient courierClient;

    @Before
    public void setup() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Попытка создать курьера без имени должна завершиться успешно")
    public void attemptToCreateCourierWithoutFirstNameShouldSucceed() {
        Courier newCourier = Courier.getDatafrom(courier);
        newCourier.setFirstName("");

        boolean isOk = courierClient.create(newCourier)
                .spec(Specification.responseSpecOK201())
                .extract().path("ok");

        assertTrue("В параметре ожитается значение - true",isOk);
    }

    @Test
    @DisplayName("Попытка создать двух одикаковых курьеров должна завершиться ошибкой")
    public void attemptToCreateSameCourierShouldReturnError() {
        //Создаем нововую учетную запись курьера, отправляем запрос на сервис и провеяем ответ
        boolean isOk = courierClient.create(courier)
                .spec(Specification.responseSpecOK201())
                .extract().path("ok");
        assertTrue("В параметре ожитается значение - true",isOk);
        //Забираем данные из сгенерированной учетной записи и отправляем повторно на сервис
        Courier sameAccount = Courier.getDatafrom(courier);
        Response responseData = courierClient.create(sameAccount)
                .spec(Specification.responseSpecConflict409())
                .extract().body().as(Response.class);
        //Проверяем сообщение в теле ответа
        Assert.assertEquals(409, responseData.getCode());
        Assert.assertEquals("Этот логин уже используется. Попробуйте другой.", responseData.getMessage());
    }

    @Test
    @DisplayName("Попытка создать курьера без логина должна завершиться ошибкой")
    public void attemptToCreateCourierWithoutLoginShouldReturnError() {
        Courier courier = Courier.getCourierWithoutLogin();
        Response responseData = courierClient.create(courier)
                .spec(Specification.responseSpecBadRequest400())
                .extract().body().as(Response.class);

        Assert.assertEquals(400, responseData.getCode());
        Assert.assertEquals("Недостаточно данных для создания учетной записи", responseData.getMessage());
    }

    @Test
    @DisplayName("Попытка создать курьера без пароля должна завершиться ошибкой")
    public void attemptToCreateCourierWithoutPasswordShouldReturnError() {
        Courier courier = Courier.getCourierWithoutPassword();
        Response responseData = courierClient.create(courier)
                .spec(Specification.responseSpecBadRequest400())
                .extract().body().as(Response.class);

        Assert.assertEquals(400, responseData.getCode());
        Assert.assertEquals("Недостаточно данных для создания учетной записи", responseData.getMessage());
    }

    @Test
    @DisplayName("Попытка создать курьера с уже существующим логином должна завершиться ошибкой")
    public void attemptToCreateCourierWithExistedLoginShouldReturnError() {
        boolean isOk = courierClient.create(courier)
                .spec(Specification.responseSpecOK201())
                .extract().path("ok");
        assertTrue("В параметре ожитается значение - true",isOk);
        //Создаем нового курьера с существующими учетными данными, но меняем имя
        Courier courierWithSameLogin = Courier.getDatafrom(courier);
        courierWithSameLogin.setFirstName("Eugene");
        Response responseData = courierClient.create(courierWithSameLogin)
                .spec(Specification.responseSpecConflict409())
                .extract().body().as(Response.class);

        Assert.assertEquals(409, responseData.getCode());
        Assert.assertEquals("Этот логин уже используется. Попробуйте другой.", responseData.getMessage());
    }

    @Test
    @DisplayName("Попытка авторизации под существующей учетной записью должна завершиться успешно")
    public void attemptToAuthoriseAsExistedCourierShouldSucceed() {
        boolean isOk = courierClient.create(courier)
                .spec(Specification.responseSpecOK201())
                .extract().path("ok");
        assertTrue("В параметре ожитается значение - true",isOk);

        CourierCredentials creds = CourierCredentials.from(courier);
        int courierId = courierClient.login(creds)
                .spec(Specification.responseSpecOK200())
                .extract().path("id");

        assertNotEquals(0, courierId);
        //Удаляем учетную запись курьера по его id
        boolean isCourierDeleted = courierClient.delete(courierId)
                .spec(Specification.responseSpecOK200())
                .extract().path("ok");
        assertTrue("В параметре ожитается значение - true", isCourierDeleted);
    }

    @Test
    @DisplayName("Попытка авторизации с неверным логином должна завершиться ошибкой")
    public void attemptToAuthoriseWithWrongLoginShouldReturnError() {
        boolean isOk = courierClient.create(courier)
                .spec(Specification.responseSpecOK201())
                .extract().path("ok");
        assertTrue("В параметре ожитается значение - true",isOk);

        CourierCredentials creds = CourierCredentials.from(courier);
        creds.setLogin("newlogin123");
        Response responseData = courierClient.login(creds)
                .spec(Specification.responseSpecNotFound404())
                .extract().body().as(Response.class);

        Assert.assertEquals(404, responseData.getCode());
        Assert.assertEquals("Учетная запись не найдена", responseData.getMessage());
    }

    @Test
    @DisplayName("Попытка авторизации с неверным паролем должна завершиться ошибкой")
    public void attemptToAuthoriseWithWrongPasswordShouldReturnError() {
        boolean isOk = courierClient.create(courier)
                .spec(Specification.responseSpecOK201())
                .extract().path("ok");
        assertTrue("В параметре ожитается значение - true",isOk);

        CourierCredentials creds = CourierCredentials.from(courier);
        creds.setPassword("newpassword123");
        Response responseData = courierClient.login(creds)
                .spec(Specification.responseSpecNotFound404())
                .extract().body().as(Response.class);

        Assert.assertEquals(404, responseData.getCode());
        Assert.assertEquals("Учетная запись не найдена", responseData.getMessage());
    }

    @Test
    @DisplayName("Попытка авторизации без ввода логина должна завершиться ошибкой")
    public void attemptToAuthoriseWithoutLoginShouldReturnError() {
        CourierCredentials creds = CourierCredentials.from(courier);
        creds.setLogin("");
        Response responseData = courierClient.login(creds)
                .spec(Specification.responseSpecBadRequest400())
                .extract().body().as(Response.class);

        Assert.assertEquals(400, responseData.getCode());
        Assert.assertEquals("Недостаточно данных для входа", responseData.getMessage());
    }

    @Test
    @DisplayName("Попытка авторизации без ввода пароля должна завершиться ошибкой")
    public void attemptToAuthoriseWithoutPasswordShouldReturnError() {
        CourierCredentials creds = CourierCredentials.from(courier);
        creds.setPassword("");
        Response responseData = courierClient.login(creds)
                .spec(Specification.responseSpecBadRequest400())
                .extract().body().as(Response.class);

        Assert.assertEquals(400, responseData.getCode());
        Assert.assertEquals("Недостаточно данных для входа", responseData.getMessage());
    }
}

