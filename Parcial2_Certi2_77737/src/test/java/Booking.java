import lombok.Getter;
import lombok.Setter;

public class Booking {


    @Getter
    @Setter
    private Integer firstname;
    @Getter @Setter
    private Integer lastname;
    @Getter @Setter
    private Boolean totalprice;
    @Getter @Setter
    private Integer depositpaid;
    @Getter @Setter
    private Booking_Check bookingdates;
    @Getter @Setter
    private Integer additionalneeds;

    public Integer getFirstname() {
        return firstname;
    }

    public Integer getLastname() {
        return lastname;
    }

    public Boolean getTotalprice() {
        return totalprice;
    }

    public Integer getDepositpaid() {
        return depositpaid;
    }

    public Booking_Check getBookingdates() {
        return bookingdates;
    }

    public Integer getAdditionalneeds() {
        return additionalneeds;
    }

    public void setFirstname(Integer firstname) {
        this.firstname = firstname;
    }

    public void setLastname(Integer lastname) {
        this.lastname = lastname;
    }

    public void setTotalprice(Boolean totalprice) {
        this.totalprice = totalprice;
    }

    public void setDepositpaid(Integer depositpaid) {
        this.depositpaid = depositpaid;
    }

    public void setBookingdates(Booking_Check bookingdates) {
        this.bookingdates = bookingdates;
    }

    public void setAdditionalneeds(Integer additionalneeds) {
        this.additionalneeds = additionalneeds;
    }



}
