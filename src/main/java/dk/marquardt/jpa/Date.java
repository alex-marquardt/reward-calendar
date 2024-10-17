package dk.marquardt.jpa;

import dk.marquardt.model.ingoing.DateRequest;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "DATE")
public class Date {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "STATE", nullable = false)
    private DateStateDB state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REWARD")
    private Reward reward;

    public Date() {
    }

    public Date(DateRequest dateRequest) {
        this.id = UUID.randomUUID().toString();
        this.date = dateRequest.getDate();
        this.state = DateStateDB.mapToDateStateDB(dateRequest.getState());
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

    public DateStateDB getState() {
        return state;
    }

    public void setState(DateStateDB state) {
        this.state = state;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }
}
