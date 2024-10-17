package dk.marquardt.service;

import dk.marquardt.jpa.Reward;
import dk.marquardt.jpa.RewardRepository;
import dk.marquardt.model.ingoing.RewardRequest;
import dk.marquardt.model.outgoing.RewardResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ApplicationScoped
public class RewardService {

    @Inject
    RewardRepository rewardRepository;

    public List<RewardResponse> getAllRewards() {
        List<Reward> rewardsFromDB = rewardRepository.listAll();
        return mapRewards(rewardsFromDB);
    }

    @Transactional
    public RewardResponse updateReward(String id, RewardRequest request) {
        Optional<Reward> optionalReward = rewardRepository.find("id", id).firstResultOptional();
        if (optionalReward.isEmpty()) {
            throw new NoSuchElementException("Reward with id " + id + " does not exist");
        }
        Reward reward = optionalReward.get();
        reward.setState(request.getState());
        return new RewardResponse(reward);
    }

    private List<RewardResponse> mapRewards(List<Reward> rewardsFromDB) {
        List<RewardResponse> allRewards = new ArrayList<>();
        for (Reward reward : rewardsFromDB) {
            allRewards.add(new RewardResponse(reward));
        }
        return allRewards;
    }
}
