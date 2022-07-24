package com.example.JustWork.serviceTests;

import com.example.JustWork.entity.Exercise;
import com.example.JustWork.entity.Set;
import com.example.JustWork.repository.ExerciseRepository;
import com.example.JustWork.service.ExerciseService;
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
public class ExerciseServiceTest {
    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    public void shouldGetAllExercises() {
        exerciseService.getExercises();
        Mockito.verify(exerciseRepository).findAll();
    }

    @Test
    public void shouldGetExerciseById() {
        Exercise sampleExercise = new Exercise("Bicep Curls");
        sampleExercise.setId(1L);
        Mockito.when(exerciseRepository.findById(1L)).thenReturn(Optional.of(sampleExercise));
        Optional<Exercise> foundExercise = exerciseService.getExerciseById(1L);
        Mockito.verify(exerciseRepository).findById(1L);
        Assertions.assertEquals(foundExercise, Optional.of(sampleExercise));
    }

    @Test
    public void shouldSaveExercise() {
        Exercise sampleExercise = new Exercise ("Bicep Curls");
        exerciseService.saveExercise(sampleExercise);
        Mockito.verify(exerciseRepository).save(sampleExercise);
    }

    @Test
    public void shouldDeleteExercise() {
        exerciseService.deleteExercise(1L);
        Mockito.verify(exerciseRepository).deleteById(1L);
    }

    @Test
    public void shouldUpdateExerciseIfFound() {
        Exercise foundExercise = new Exercise("Bicep Curls");
        Exercise newExercise = new Exercise();
        newExercise.addSet(new Set());
        newExercise.setTitle("Tricep Pushdown");
        Mockito.when(exerciseRepository.findById(any(Long.class))).thenReturn(Optional.of(foundExercise));
        Boolean result = exerciseService.updateExercise(1L, newExercise);

        Assertions.assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfUpdateExerciseNotFound() {
        Mockito.when(exerciseRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Boolean result = exerciseService.updateExercise(1L, new Exercise());
        Assertions.assertFalse(result);
    }
}
