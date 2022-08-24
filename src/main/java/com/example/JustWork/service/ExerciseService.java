package com.example.JustWork.service;

import com.example.JustWork.entity.Exercise;
import com.example.JustWork.entity.Set;
import com.example.JustWork.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<Exercise> getExercises() { return exerciseRepository.findAll();}

    public Optional<Exercise> getExerciseById(Long id) { return exerciseRepository.findById(id);}

    public void saveExercise(Exercise exercise) { exerciseRepository.save(exercise); }

    public void deleteExercise(Long id) { exerciseRepository.deleteById(id); }

    public Boolean updateExercise(Long id, Exercise exercise) {
        Optional<Exercise> foundExercise = getExerciseById(id);

        if (foundExercise.isEmpty()) {
            return Boolean.FALSE;
        }

        Exercise retrievedExercise = foundExercise.get();;

        if (exercise.getTitle() != null) {
            retrievedExercise.setTitle(exercise.getTitle());
        }
        if (exercise.getSets() != null) {
            retrievedExercise.setSets(exercise.getSets());
        }

        saveExercise(retrievedExercise);
        return Boolean.TRUE;
    }

}
