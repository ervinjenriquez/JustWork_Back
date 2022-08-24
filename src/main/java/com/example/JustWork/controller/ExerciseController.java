package com.example.JustWork.controller;

import com.example.JustWork.entity.Day;
import com.example.JustWork.entity.Exercise;
import com.example.JustWork.service.DayService;
import com.example.JustWork.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class ExerciseController {

    @Autowired
    private DayService dayService;
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/exercises")
    public List<Exercise> getExercises() {return exerciseService.getExercises();}

    @GetMapping("/exercises/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable("id") Long id) {
        Optional<Exercise> foundExercise = exerciseService.getExerciseById(id);
        if(foundExercise.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Exercise updatedExercise = foundExercise.get();
        return new ResponseEntity<Exercise>(updatedExercise, HttpStatus.OK);
    }

    @PostMapping("/exercises/{id}")
    @Transactional
    public ResponseEntity<Exercise> addExercise(@PathVariable("id") Long id, @RequestBody Exercise exercise) {
        Optional<Day> foundList = dayService.getDayById(id);
        foundList.get().addExercise(exercise);
        exerciseService.saveExercise(exercise);
        return new ResponseEntity<Exercise>(exercise, HttpStatus.CREATED);
    }

    @DeleteMapping("/exercises/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExercise(@PathVariable("id") Long id) {exerciseService.deleteExercise(id);}

    @PutMapping("/exercises/{id}")
    public ResponseEntity<Exercise> editExercise(@PathVariable("id") Long id, @RequestBody Exercise updateRequest) {
        Optional<Exercise> foundExercise = exerciseService.getExerciseById(id);

        if (foundExercise.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Exercise updatedExercise = foundExercise.get();
        updatedExercise.setTitle(updateRequest.getTitle());
        exerciseService.saveExercise(updatedExercise);

        return new ResponseEntity<Exercise>(updatedExercise, HttpStatus.OK);
    }

}
