package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

public enum ReservationState {

    CREATED(1),
    CHECKEDIN(2),
    CHECKEDOUT(3);

    private final int value;
    private ReservationState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ReservationState getState(int value) {
        switch (value) {
            case 1:
                return CREATED;
            case 2:
                return CHECKEDIN;
            default:
                return CHECKEDOUT;
        }
    }
}
