package sql.models.car.exceptions;

public class YearException extends IllegalArgumentException {
    public YearException() {
    }

    public YearException(String message) {
        super(message);
    }

    public YearException(String message, Throwable cause) {
        super(message, cause);
    }

    public YearException(Throwable cause) {
        super(cause);
    }
}
