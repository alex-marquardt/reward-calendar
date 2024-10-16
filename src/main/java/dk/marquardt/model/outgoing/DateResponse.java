package dk.marquardt.model.outgoing;

import dk.marquardt.jpa.Date;
import dk.marquardt.model.DateState;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "Date response")
public class DateResponse {

    @Schema(description = "Id of date", required = true)
    private String id;
    @Schema(description = "Specific date", required = true)
    private LocalDate date;
    @Schema(description = "State of date", required = true)
    private DateState state;

    public DateResponse(Date date) {
        this.id = date.getId();
        this.date = date.getDate();
        this.state = DateState.mapToDateState(date.getState());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
