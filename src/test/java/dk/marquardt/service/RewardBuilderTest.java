package dk.marquardt.service;

import dk.marquardt.jpa.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RewardBuilderTest {

    @InjectMocks
    RewardBuilder rewardBuilder;
    @Mock
    RewardRepository rewardRepository;
    @Mock
    DateRepository dateRepository;

    @Test
    void test_NoRewardSaved_WithNoDates() {
        // Arrange
        LocalDate startDate = LocalDate.now();
        setupDatesForBuildingRewards(startDate, List.of());

        // Act
        rewardBuilder.buildRewards();

        // Assert
        verify(rewardRepository, times(0)).persist(any(Reward.class));
    }

    @Test
    void test_NoRewardSaved_WithOnlySuccessDates() {
        // Arrange
        LocalDate startDate = LocalDate.now().minusDays(2);
        Date date1 = getDate(LocalDate.now().minusDays(1), DateStateDB.SUCCESS);
        Date date2 = getDate(LocalDate.now(), DateStateDB.SUCCESS);
        setupDatesForBuildingRewards(startDate, List.of(date1, date2));

        // Act
        rewardBuilder.buildRewards();

        // Assert
        verify(rewardRepository, times(0)).persist(any(Reward.class));
    }

    @Test
    void test_NoRewardSaved_WithOnlyFailDates() {
        // Arrange
        LocalDate startDate = LocalDate.now().minusDays(2);
        Date date1 = getDate(LocalDate.now().minusDays(1), DateStateDB.FAIL);
        Date date2 = getDate(LocalDate.now(), DateStateDB.FAIL);
        setupDatesForBuildingRewards(startDate, List.of(date1, date2));

        // Act
        rewardBuilder.buildRewards();

        // Assert
        verify(rewardRepository, times(0)).persist(any(Reward.class));
    }

    @Test
    void test_NoRewardSaved_WithCorruptedDates() {
        // Arrange
        LocalDate startDate = LocalDate.now().minusDays(3);
        Date date1 = getDate(LocalDate.now().minusDays(3), DateStateDB.SUCCESS);
        Date date2 = getDate(LocalDate.now(), DateStateDB.FAIL);
        setupDatesForBuildingRewards(startDate, List.of(date1, date2));

        // Act
        rewardBuilder.buildRewards();

        // Assert
        verify(rewardRepository, times(0)).persist(any(Reward.class));
    }

    private void setupDatesForBuildingRewards(LocalDate localDate, List<Date> dates) {
        Date startDate = getDate(localDate);
        when(dateRepository.getLatestDateWithReward()).thenReturn(startDate);
        when(dateRepository.getDatesFromSpecificDate(startDate.getDate())).thenReturn(dates);
    }

    private Date getDate(LocalDate localDate) {
        Date date = new Date();
        date.setDate(localDate);
        return date;
    }

    private Date getDate(LocalDate localDate, DateStateDB state) {
        Date date = new Date();
        date.setDate(localDate);
        date.setState(state);
        return date;
    }
}