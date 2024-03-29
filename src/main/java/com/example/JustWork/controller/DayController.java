package com.example.JustWork.controller;

import com.example.JustWork.entity.Day;
import com.example.JustWork.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class DayController {

    @Autowired
    private DayService dayService;

    @GetMapping("/days")
    public List<Day> getDays() {return dayService.getDays();}

    @GetMapping("/days/{id}")
    public ResponseEntity<Day> getDayById(@PathVariable("id") Long id) {
        try {
            Optional<Day> foundDay = dayService.getDayById(id);
            if (foundDay.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Day updatedDay = foundDay.get();
            return new ResponseEntity<Day>(updatedDay, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/days")
    @Transactional
    public ResponseEntity<Day> addDay(@RequestBody Day day) {
        dayService.addDay(day);
        return new ResponseEntity<Day>(day, HttpStatus.CREATED);
    }

    @DeleteMapping("/days/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteDay(@PathVariable("id") Long id) {
        try {
            dayService.deleteDay(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_MODIFIED, "Unable to find the Day to delete.", e);
        }
    }

    @PutMapping("/days/{id}")
    public ResponseEntity<Day> editDay(@PathVariable("id") Long id, @RequestBody Day day) {
        Boolean successfulUpdate = dayService.updateDay(id, day);
        HttpStatus status = successfulUpdate ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Day>(day, status);
    }

}
