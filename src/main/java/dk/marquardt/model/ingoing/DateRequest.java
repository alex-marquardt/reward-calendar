package dk.marquardt.model.ingoing;

import dk.marquardt.model.DateState;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "Date request")
public class DateRequest {

    @Schema(description = "Specific date", required = true)
    private LocalDate date;
    @Schema(description = "State of date", required = true)
    private DateState state;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DateState getState() {
        return state;
    }

    public void setState(DateState state) {
        this.state = state;
    }
}
