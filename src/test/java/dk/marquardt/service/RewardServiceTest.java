package dk.marquardt.service;

import dk.marquardt.jpa.*;
import dk.marquardt.model.RewardState;
import dk.marquardt.model.ingoing.RewardRequest;
import dk.marquardt.model.outgoing.RewardResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @InjectMocks
    RewardService rewardService;
    @Mock
    RewardRepository rewardRepository;

    @Test
    public void test_GetListOfAllRewards() {
        // Arrange
        when(rewardRepository.listAll()).thenReturn(List.of(getReward(RewardStateDB.USABLE), getReward(RewardStateDB.SPEND)));

        // Act
        List<RewardResponse> rewards = rewardService.getAllRewards();

        // Assert
        assertEquals(2, rewards.size());
        assertEquals(RewardState.USABLE, rewards.getFirst().getState());
        assertEquals(RewardState.SPEND, rewards.getLast().getState());
    }

    @Test
    public void test_UpdateReward() {
        // Arrange
        String id = "rewardId";
        RewardRequest request = new RewardRequest();
        request.setState(RewardState.SPEND);
        PanacheQuery query = mock(PanacheQuery.class);
        when(rewardRepository.find("id", id)).thenReturn(query);
        when(query.firstResultOptional()).thenReturn(Optional.of(getReward(id)));

        // Act
        RewardResponse rewardResponse = rewardService.updateReward(id, request);

        // Assert
        assertEquals("rewardId", rewardResponse.getId());
        assertEquals(RewardState.SPEND, rewardResponse.getState());
    }

    private Reward getReward(String id) {
        Reward reward = getReward(RewardStateDB.USABLE);
        reward.setId(id);
        return reward;
    }

    private Reward getReward(RewardStateDB state) {
        Date date = new Date();
        date.setState(DateStateDB.SUCCESS);
        Reward reward = new Reward();
        reward.setState(state);
        reward.setDates(List.of(date));
        return reward;
    }
}