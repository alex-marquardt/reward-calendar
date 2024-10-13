package dk.marquardt.jpa;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class DateRepository implements PanacheRepository<Date> {

    public Date getLatestDateWithReward() {
        return find("select latest_date from Date latest_date where latest_date.reward is not null order by date desc").firstResult();
    }

    public List<Date> getDatesFromSpecificDate(LocalDate fromDate) {
        return list("select reward_date from Date reward_date where ?1 < reward_date.date order by date asc", fromDate);
    }
}
