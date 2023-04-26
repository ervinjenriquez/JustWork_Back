package com.example.JustWork.serviceTests;

import com.example.JustWork.entity.Day;
import com.example.JustWork.repository.DayRepository;
import com.example.JustWork.service.DayService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class DayServiceTest {

    @Mock
    private DayRepository dayRepository;

    @InjectMocks
    private DayService dayService;

    @Test
    public void shouldGetAllDays() {
        dayService.getDays();
        Mockito.verify(dayRepository).findAll();
    }

    @Test
    public void shouldGetDayById() {
        Day sampleDay1 = new Day("Push A", "Chest-Tri-Shoulders");
        sampleDay1.setId(1L);

        Day sampleDay2 = new Day("Pull A", "Back and Biceps");
        sampleDay2.setId(2L);

        Day sampleDay3 = new Day("Leg A", "Legs (Quad-focused)");
        sampleDay3.setId(3L);

        Mockito.when(dayRepository.findById(1L)).thenReturn(Optional.of(sampleDay1));
        Mockito.when(dayRepository.findById(2L)).thenReturn(Optional.of(sampleDay2));
        Mockito.when(dayRepository.findById(3L)).thenReturn(Optional.of(sampleDay3));

        Optional<Day> foundDay = dayService.getDayById(2L);

        if (assertDayGood(foundDay.get())) {
            Assertions.assertEquals(foundDay.get().getId(), Optional.of(sampleDay2).get().getId(), "Assert that the foundDay equals sampleDay2 and not sampleDay1 or sampleDay3");
        }
    }

    @Test
    public void shouldNotGetDayByIdWhenDayDoesNotExist() {
        Day sampleDay = new Day("Push A", "Chest-Tri-Shoulders");
        sampleDay.setId(1L);

        Optional<Day> testDay = Optional.of(sampleDay);

        Mockito.when(dayRepository.findById(2L)).thenReturn(testDay);
        Optional<Day> foundDay = dayService.getDayById(2L);

        Assertions.assertTrue(testDay == foundDay);
        Assertions.assertNotNull(foundDay, "Assert that foundDay does not equal Null when that day does not exist");
    }

    @Test
    public void shouldNotGetDayWhenNegativeNumberIsPassed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayById(-1L);
        }, "Assert that a IllegalArgumentException is thrown when calling getDayById(-1L) (negative number case)");
    }


    @Test
    public void shouldNotGetDayByIdWhenPassedNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayById(null);
        }, "Assert that a IllegalArgumentException is thrown when calling getDayById(null) (null case)");
    }

    @Test
    public void shouldNotGetDayByIdWhenPassedZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayById(0L);
        }, "Assert that a IllegalArgumentException is thrown when calling getDayById(0L) (0 case)");
    }

    @Test
    public void shouldAddDay() {
        Day sampleDay = new Day("Push A", "Chest-Tri-Shoulders");
        dayService.addDay(sampleDay);
        Mockito.verify(dayRepository).save(sampleDay);
    }

    @Test
    public void shouldDeleteDay() {
        dayService.deleteDay(1L);
        Mockito.verify(dayRepository).deleteById(1L);
    }

    @Test
    public void shouldUpdateDayIfFound() {
        Day foundDay = new Day("Push A", "Chest-Tri-Shoulders");
        Day newDay = new Day();
        newDay.setTitle("Pull A");
        newDay.setDescription("Back-Biceps");
        Mockito.when(dayRepository.findById(any(Long.class))).thenReturn(Optional.of(foundDay));
        Boolean result = dayService.updateDay(1L, newDay);

        Assertions.assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfUpdateDayNotFound() {
        Mockito.when(dayRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Boolean result = dayService.updateDay(1L, new Day());
        Assertions.assertFalse(result);
    }


    private static boolean assertDayEquals(Day o1, Day o2) {
        Assertions.assertEquals(o1.getTitle(), o2.getTitle(), "Title matches");
        Assertions.assertEquals(o1.getDescription(), o2.getDescription(), "Description matches");

        return true;
    }

    private static boolean assertDayGood(Day day) {
        if ((day.getTitle() != null && day.getTitle() instanceof String) && (day.getId() != null && day.getId() instanceof Long) && (day.getDescription() != null && day.getDescription() instanceof String)) {
            return true;
        } else {
            return false;
        }
    }
}
