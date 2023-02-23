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
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

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

        Mockito.when(dayRepository.findById(1L)).thenReturn(Optional.of(sampleDay2));
        Optional<Day> foundDay = dayService.getDayById(1L);

        try {
            if (isDayGood(foundDay.get())) {
                Assertions.assertEquals(foundDay, Optional.of(sampleDay2), "foundDay equals sampleDay2 ");
            } else {
                throw new Exception("Bad Day");
            }
        } catch (Exception e) {
            System.out.println("Bad day was found");
        }
    }

    @Test
    public void shouldNotGetDayByIdWhenDayDoesNotExist() {
        Day sampleDay = new Day("Push A", "Chest-Tri-Shoulders");
        sampleDay.setId(1L);

        Mockito.when(dayRepository.findById(2L)).thenReturn(null);
        Optional<Day> foundDay = dayService.getDayById(2L);

        Assertions.assertNull(foundDay, "Assert that foundDay does equal Null");
    }

    @Test
    public void shouldNotGetDayWhenNegativeNumberIsPassed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayById(-1L);
        });
    }


    @Test
    public void shouldNotGetDayByIdWhenPassedNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayById(null);
        });
    }

    @Test
    public void shouldSaveDay() {
        Day sampleDay = new Day("Push A", "Chest-Tri-Shoulders");
        dayService.saveDay(sampleDay);
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


    //private assertDayEquals
    //build my own

    //Start with discussion about arch tiers and why they are important
    //Service tier, middle tier

    //Build this out later
    private boolean assertDayEquals(Day o1, Day o2) {

        Assertions.assertEquals(o1.getTitle(), o2.getTitle(), "Title matches");
        Assertions.assertEquals(o1.getDescription(), o2.getDescription(), "Description matches");

        return true;
    }

    //Method assertGoodDay build under
    private static boolean isDayGood(Day day) {
        if ((day.getTitle() != null && day.getTitle() instanceof String) && (day.getId() != null && day.getId() instanceof Long) && (day.getDescription() != null && day.getDescription() instanceof String)) {
            return true;
        } else {
            return false;
        }
    }
}
