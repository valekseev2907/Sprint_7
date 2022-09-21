package courier;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter @Setter
public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier (String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    public static Courier getDatafrom(Courier courier) {
        return new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
    }
    // Генерируем пользователя с рандомизиролванными данными
    public static Courier getRandomCourier() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10),
                "Pa$$w0rd!",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static Courier getCourierWithoutLogin() {
        return new Courier(
                "",
                "Pa$$w0rd!",
                "Ivan"
        );
    }

    public static Courier getCourierWithoutPassword() {
        return new Courier(
                "deadinside145",
                "",
                "Ivan"
        );
    }
}
