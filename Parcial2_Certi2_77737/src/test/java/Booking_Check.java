import lombok.Getter;
import lombok.Setter;

    public class Booking_Check {

        @Getter
        @Setter
        private Boolean checkin;

        public Boolean getCheckout() {
            return checkout;
        }

        public Boolean getCheckin() {
            return checkin;
        }

        @Getter @Setter
        private Boolean checkout;

        public void setCheckin(Boolean checkin) {
            this.checkin = checkin;
        }

        public void setCheckout(Boolean checkout) {
            this.checkout = checkout;
        }
    }

