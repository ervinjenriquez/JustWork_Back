package com.example.JustWork.controller;

import com.example.JustWork.entity.Exercise;
import com.example.JustWork.entity.Set;
import com.example.JustWork.repository.ExerciseRepository;
import com.example.JustWork.service.ExerciseService;
import com.example.JustWork.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SetController {

    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private SetService setService;

    @GetMapping("/sets")
    public List<Set> getSets() { return setService.getSets();}

    @GetMapping("/sets/{id}")
    public ResponseEntity<Set> getSetById(@PathVariable("id") Long id) {
        Optional<Set> foundSet = setService.getSetById(id);

        if(foundSet.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set updatedSet = foundSet.get();
        return new ResponseEntity<Set>(updatedSet, HttpStatus.OK);
    }

    @PostMapping("/sets/{id}")
    public ResponseEntity<Set> addSet(@PathVariable("id") Long id, @RequestBody Set set) {
        Optional<Exercise> foundList = exerciseService.getExerciseById(id);
        foundList.get().addSet(set);
        setService.saveSet(set);
        return new ResponseEntity<Set>(set, HttpStatus.CREATED);
    }

    @DeleteMapping("/sets/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteSet(@PathVariable("id") Long id) { setService.deleteSet(id);}

    @PutMapping("/sets/{id}")
    public ResponseEntity<Set> editSet(@PathVariable("id") Long id, @RequestBody Set set) {
        Boolean successfulUpdate = setService.updateSet(id, set);
        HttpStatus status = successfulUpdate ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Set>(set, status);
    }
}
