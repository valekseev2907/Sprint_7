package order;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;


import java.text.SimpleDateFormat;
import java.util.Date;


@Getter @Setter
public class Order {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order parametrizedOrder(String[] color) {
        return new Order(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                "5",
                "2020-06-06",
                "Saske, come back to Konoha",
                color
        );
    }
        public static Order randomOrder() {
            return new Order(
                    RandomStringUtils.randomAlphabetic(10),
                    RandomStringUtils.randomAlphabetic(10),
                    RandomStringUtils.randomAlphanumeric(15),
                    RandomStringUtils.randomNumeric(2),
                    "+7" + RandomStringUtils.randomNumeric(10),
                    RandomStringUtils.randomNumeric(1),
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                    RandomStringUtils.randomAlphabetic(15),
                    new String[]{"BLACK"}
            );
        }
}
