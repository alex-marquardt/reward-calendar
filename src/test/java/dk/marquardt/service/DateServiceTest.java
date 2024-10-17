package dk.marquardt.service;

import dk.marquardt.jpa.Date;
import dk.marquardt.jpa.DateRepository;
import dk.marquardt.jpa.DateStateDB;
import dk.marquardt.model.DateState;
import dk.marquardt.model.ingoing.DateRequest;
import dk.marquardt.model.outgoing.DateResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DateServiceTest {

    @InjectMocks
    DateService dateService;
    @Mock
    DateRepository dateRepository;

    @Test
    void test_GetListOfAllDates() {
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

    @Test
    void test_GetDateById() {
        // Arrange
        String id = "dateId";
        PanacheQuery query = mock(PanacheQuery.class);
        when(dateRepository.find("date", id)).thenReturn(query);
        when(query.firstResultOptional()).thenReturn(Optional.of(getDate(id)));

        // Act
        DateResponse date = dateService.getDate(id);

        // Assert
        assertEquals("dateId", date.getId());
    }

    @Test
    void test_UpsertDate_Create() {
        // Arrange
        DateRequest request = new DateRequest();
        request.setDate(LocalDate.now());
        request.setState(DateState.SUCCESS);
        PanacheQuery query = mock(PanacheQuery.class);
        when(dateRepository.find("date", request.getDate())).thenReturn(query);
        when(query.firstResultOptional()).thenReturn(Optional.empty());

        // Act
        DateResponse dateResponse = dateService.upsertDate(request);

        // Assert
        verify(dateRepository, times(1)).persist(any(Date.class));
        assertNotNull(dateResponse.getId());
        assertEquals(DateState.SUCCESS, dateResponse.getState());
        assertEquals(LocalDate.now(), dateResponse.getDate());
    }

    @Test
    void test_UpsertDate_Update() {

        // Arrange
        DateRequest request = new DateRequest();
        request.setDate(LocalDate.now());
        request.setState(DateState.FAIL);
        PanacheQuery query = mock(PanacheQuery.class);
        when(dateRepository.find("date", request.getDate())).thenReturn(query);
        when(query.firstResultOptional()).thenReturn(Optional.of(getDate("dateId")));

        // Act
        DateResponse dateResponse = dateService.upsertDate(request);

        // Assert
        assertEquals("dateId", dateResponse.getId());
        assertEquals(DateState.FAIL, dateResponse.getState());
        assertEquals(LocalDate.now(), dateResponse.getDate());
    }

    @Test
    void test_DeleteDateById() {
        // Arrange
        String dateId = "dateId";

        // Act
        dateService.deleteDate(dateId);

        // Assert
        verify(dateRepository, times(1)).delete("id", dateId);
    }

    private Date getDate(String id) {
        Date date = getDate(LocalDate.now(), DateStateDB.SUCCESS);
        date.setId(id);
        return date;
    }

    private Date getDate(LocalDate localDate, DateStateDB state) {
        Date date = new Date();
        date.setDate(localDate);
        date.setState(state);
        return date;
    }
}