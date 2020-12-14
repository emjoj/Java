package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

public enum ReservationState {

    CREATED(1),
    CHECKED_IN(2),
    CHECKED_OUT(3);

    private final int value;

    ReservationState(int value) {
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
                return CHECKED_IN;
            default:
                return CHECKED_OUT;
        }
    }
}
