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
        Day sampleDay = new Day("Push A", "Chest-Tri-Shoulders");
        sampleDay.setId(1L);
        Mockito.when(dayRepository.findById(1L)).thenReturn(Optional.of(sampleDay));
        Optional<Day> foundDay = dayService.getDayById(1L);
        Assertions.assertEquals(foundDay, Optional.of(sampleDay));
    }

    @Test
    public void shouldNotGetDayByIdWhenDayDoesNotExist() {
        Day sampleDay = new Day("Push A", "Chest-Tri-Shoulders");
        sampleDay.setId(1L);

        Mockito.when(dayRepository.findById(2L)).thenReturn(null);
        Optional<Day> foundDay = dayService.getDayById(2L);

        Assertions.assertEquals(null, foundDay);
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
}
