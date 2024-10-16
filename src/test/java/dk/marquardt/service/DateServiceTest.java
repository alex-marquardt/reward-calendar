package dk.marquardt.service;

import dk.marquardt.jpa.Date;
import dk.marquardt.jpa.DateRepository;
import dk.marquardt.jpa.DateStateDB;
import dk.marquardt.model.DateState;
import dk.marquardt.model.outgoing.DateResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateServiceTest {

    @InjectMocks
    DateService dateService;
    @Mock
    DateRepository dateRepository;

    @Test
    void testListOfAllDates() {
        // Arrange
        when(dateRepository.listAll()).thenReturn(List.of(getDate(LocalDate.now(), DateStateDB.SUCCESS), getDate(LocalDate.now().minusDays(1), DateStateDB.FAIL)));

        // Act
        List<DateResponse> dates = dateService.getAllDates();

        // Assert
        assertEquals(2, dates.size());
        assertEquals(DateState.SUCCESS, dates.getFirst().getState());
        assertEquals(LocalDate.now(), dates.getFirst().getDate());
        assertEquals(DateState.FAIL, dates.getLast().getState());
        assertEquals(LocalDate.now().minusDays(1), dates.getLast().getDate());
    }

    private Date getDate(LocalDate localDate, DateStateDB state) {
        Date date = new Date();
        date.setDate(localDate);
        date.setState(state);
        return date;
    }

}