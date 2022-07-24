package com.example.JustWork.serviceTests;

import com.example.JustWork.entity.Exercise;
import com.example.JustWork.entity.Set;
import com.example.JustWork.repository.SetRepository;
import com.example.JustWork.service.SetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SetServiceTest {

    @Mock
    private SetRepository setRepository;

    @InjectMocks
    private SetService setService;

    @Test
    public void shouldGetAllSets() {
        setService.getSets();
        Mockito.verify(setRepository).findAll();
    }

    @Test
    public void shouldGetSetById() {
        Set sampleSet = new Set(2, 1, 135, 5, 1, "warm-up");
        sampleSet.setId(1L);
        Mockito.when(setRepository.findById(1L)).thenReturn(Optional.of(sampleSet));
        Optional<Set> foundSet = setService.getSetById(1L);
        Mockito.verify(setRepository).findById(1L);
        Assertions.assertEquals(foundSet, Optional.of(sampleSet));
    }

    @Test
    public void shouldSaveSet() {
        Set sampleSet = new Set(2, 1, 135, 5, 1, "warm-up");
        setService.saveSet(sampleSet);
        Mockito.verify(setRepository).save(sampleSet);
    }

    @Test
    public void shouldDeleteSet() {
        setService.deleteSet(1L);
        Mockito.verify(setRepository).deleteById(1L);
    }

    @Test
    public void shouldUpdateSetIfFound() {
        Set foundSet = new Set(2, 1, 135, 5, 1, "warm-up");
        Set newSet = new Set();
        newSet.setExercise(new Exercise());
        newSet.setWeek(3);
        newSet.setNumber(2);
        newSet.setWeight(225);
        newSet.setReps(10);
        newSet.setDifficulty(3);
        newSet.setType("working");
        Mockito.when(setRepository.findById(any(Long.class))).thenReturn(Optional.of(foundSet));
        Boolean result = setService.updateSet(1L, newSet);

        Assertions.assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfUpdateSetNotFound() {
        Mockito.when(setRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Boolean result = setService.updateSet(1L, new Set());
        Assertions.assertFalse(result);
    }
}
