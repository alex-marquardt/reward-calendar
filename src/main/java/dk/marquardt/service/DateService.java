package dk.marquardt.service;

import dk.marquardt.jpa.Date;
import dk.marquardt.jpa.DateRepository;
import dk.marquardt.model.ingoing.DateRequest;
import dk.marquardt.model.outgoing.DateResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ApplicationScoped
public class DateService {

    @Inject
    DateRepository dateRepository;

    public DateResponse getDate(String id) {
        Date dateFromDB = getDateFromDB(id);
        return new DateResponse(dateFromDB);
    }

    public List<DateResponse> getAllDates() {
        List<Date> datesFromDB = dateRepository.listAll();
        List<DateResponse> allDates = new ArrayList<>();
        for (Date date : datesFromDB) {
            allDates.add(new DateResponse(date));
        }
        return allDates;
    }

    @Transactional
    public DateResponse upsertDate(DateRequest request) {
        Optional<Date> optionalDate = dateRepository.find("date", request.getDate()).firstResultOptional();

        Date date = new Date();
        if (optionalDate.isEmpty()) {
            date = new Date(request);
            dateRepository.persist(date);
        } else {
            date = optionalDate.get();
            date.setState(request.getState());
        }
        return new DateResponse(date);
    }

    @Transactional
    public void deleteDate(String id) {
        dateRepository.delete("id", id);
    }

    private Date getDateFromDB(String id) {
        Optional<Date> dateFromDB = dateRepository.find("date", id).firstResultOptional();
        if (dateFromDB.isEmpty()) {
            throw new NoSuchElementException("Date " + id + " does not exist");
        }
        return dateFromDB.get();
    }
}
