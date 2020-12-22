package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

public final class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

