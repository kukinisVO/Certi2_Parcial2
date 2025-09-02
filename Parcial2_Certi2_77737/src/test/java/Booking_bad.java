import lombok.Getter;
import lombok.Setter;

public class Booking_bad {


    @Getter
    @Setter
    private Integer firstname;
    @Getter
    @Setter
    private Integer lastname;
    @Getter
    @Setter
    private Boolean totalprice;
    @Getter
    @Setter
    private Integer depositpaid;
    @Getter
    @Setter
    private Booking_BadCheck bookingdates;
    @Getter
    @Setter
    private Integer additionalneeds;


}
