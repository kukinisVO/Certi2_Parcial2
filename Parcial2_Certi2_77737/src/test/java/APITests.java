import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasKey;

public class APITests {
    @Test
    public void getBookingValidId(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking/";
        Response response = RestAssured.given().pathParam("id", "3812")
                .when().get("{id}");

        response.then().assertThat().statusCode(200);

        response.then().log().body();

        response.then().assertThat().body("$", hasKey("firstname"));
        response.then().assertThat().body("$", hasKey("lastname"));
        response.then().assertThat().body("$", hasKey("totalprice"));
        response.then().assertThat().body("$", hasKey("depositpaid"));
        response.then().assertThat().body("$", hasKey("bookingdates"));
        response.then().assertThat().body("bookingdates", hasKey("checkin"));
        response.then().assertThat().body("bookingdates", hasKey("checkout"));
        response.then().assertThat().body("$", hasKey("additionalneeds"));


        response.then().assertThat().body("firstname", Matchers.equalTo("Leon"));
        response.then().assertThat().body("lastname", Matchers.equalTo("Kennedy"));
        response.then().assertThat().body("totalprice", Matchers.equalTo(111));
        response.then().assertThat().body("depositpaid", Matchers.equalTo(true));
        response.then().assertThat().body("bookingdates.checkin", Matchers.equalTo("2018-01-01"));
        response.then().assertThat().body("bookingdates.checkout", Matchers.equalTo("2019-01-01"));
        response.then().assertThat().body("additionalneeds", Matchers.equalTo("Magnum ammo"));
    }


    @Test
    public void getBookingInvalidId(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking/";
        Response response = RestAssured.given().pathParam("id", "10000")
                .when().get("{id}");

        response.then().assertThat().statusCode(404);

        response.then().log().body();

    }

    @Test
    public  void PostBookingValidData() throws JsonProcessingException {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Booking booking = new Booking();
        booking.setFirstname("Leon");
        booking.setLastname("Kennedy");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Magnum ammo");

        Booking_Check dates = new Booking_Check();
        dates.setCheckin("2018-01-01");
        dates.setCheckout("2019-01-01");
        booking.setBookingdates(dates);

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);
        System.out.println(payload);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("User-Agent", "PostmanRuntime/7.45.0")
                .body(payload)
                .when()
                .post("/booking");
        response.then().log().body();

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("booking.firstname", Matchers.equalTo(booking.getFirstname()));
        response.then().assertThat().body("booking.lastname", Matchers.equalTo(booking.getLastname()));
        response.then().assertThat().body("booking.totalprice", Matchers.equalTo(booking.getTotalprice()));
        response.then().assertThat().body("booking.depositpaid", Matchers.equalTo(booking.getDepositpaid()));
        response.then().assertThat().body("booking.bookingdates.checkin", Matchers.equalTo(booking.getBookingdates().getCheckin()));
        response.then().assertThat().body("booking.bookingdates.checkout", Matchers.equalTo(booking.getBookingdates().getCheckout()));
        response.then().assertThat().body("booking.additionalneeds", Matchers.equalTo(booking.getAdditionalneeds()));


    }

    @Test
    public  void PostBookingBlankOrNullData() throws JsonProcessingException {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking/";

        Booking booking = new Booking();
        booking.setFirstname("");
        booking.setLastname("");
        booking.setTotalprice(null);
        booking.setDepositpaid(null);
        booking.setAdditionalneeds("");

        Booking_Check dates = new Booking_Check();
        dates.setCheckin("");
        dates.setCheckout("");
        booking.setBookingdates(dates);

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);
        System.out.println(payload);

        Response response = RestAssured.given().accept(ContentType.JSON).body(payload)
                .when().post();

        response.then().log().body();

        response.then().assertThat().statusCode(400);


    }


    @Test
    public  void PostBookingBadData() throws JsonProcessingException {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking/";

        Booking booking = new Booking();
        booking.setFirstname(0);
        booking.setLastname(0);
        booking.setTotalprice(false);
        booking.setDepositpaid(0);
        booking.setAdditionalneeds(0);

        Booking_Check dates = new Booking_Check();
        dates.setCheckin(true);
        dates.setCheckout(true);
        booking.setBookingdates(dates);

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);
        System.out.println(payload);

        Response response = RestAssured.given().accept(ContentType.JSON).body(payload)
                .when().post();

        response.then().log().body();

        response.then().assertThat().statusCode(400);


    }
}
