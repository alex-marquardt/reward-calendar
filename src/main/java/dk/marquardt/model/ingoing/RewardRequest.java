package dk.marquardt.model.ingoing;

import dk.marquardt.model.RewardState;
import org.eclipse.microprofile.openapi.annotations.media.Schema;;

@Schema(description = "Reward request")
public class RewardRequest {

    @Schema(description = "State of date", required = true)
    private RewardState state;

    public RewardState getState() {
        return state;
    }
}
