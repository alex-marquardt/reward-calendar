package dk.marquardt.model.outgoing;

import dk.marquardt.jpa.Date;
import dk.marquardt.jpa.Reward;
import dk.marquardt.model.RewardState;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(name = "Reward response")
public class RewardResponse {

    @Schema(description = "Id of reward", required = true)
    private String id;
    @Schema(description = "State of reward", required = true)
    private RewardState state;
    @Schema(description = "List of dates", required = true)
    private List<DateResponse> dates;

    public RewardResponse(Reward reward) {
        this.id = reward.getId();
        this.state = reward.getState();
        this.dates = getDates(reward.getDates());
    }

    private List<DateResponse> getDates(List<Date> dates) {
        return dates.stream().map(DateResponse::new).toList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RewardState getState() {
        return state;
    }

    public void setState(RewardState state) {
        this.state = state;
    }

    public List<DateResponse> getDates() {
        return dates;
    }

    public void setDates(List<DateResponse> dates) {
        this.dates = dates;
    }
}
