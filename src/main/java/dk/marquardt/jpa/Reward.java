package dk.marquardt.jpa;

import dk.marquardt.model.RewardState;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "REWARD")
public class Reward {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "STATE", nullable = false)
    private RewardStateDB state;

    @OneToMany(mappedBy = "reward", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Date> dates;

    public Reward() {
    }

    public Reward(List<Date> dates) {
        setDates(dates);
    }

    @PrePersist
    public void onInsert() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.state == null) {
            this.state = RewardStateDB.USABLE;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public RewardStateDB getState() {
        return state;
    }

    public void setState(RewardStateDB state) {
        this.state = state;
    }

    public void setState(RewardState state) {
        this.state = RewardStateDB.mapToRewardStateDB(state);
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
        if (dates != null) {
            dates.forEach(date -> date.setReward(this));
        }
    }


}
