package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

public enum ReservationState {

    CREATED(),
    CHECKED_IN(),
    CHECKED_OUT();

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
