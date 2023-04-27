package com.example.JustWork.service;

import com.example.JustWork.entity.Day;
import com.example.JustWork.entity.Exercise;
import com.example.JustWork.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DayService {

    @Autowired
    private DayRepository dayRepository;

    public List<Day> getDays() { return dayRepository.findAll();}

    // All possible inputs: null, negative/positive #, 0
    // Invalid inputs => throw IllegalArgumentException
    // Valid inputs => found/notFound
    public Optional<Day> getDayById(Long id) {
        if (id == null || id <= 0) { //If valid input
            throw new IllegalArgumentException();
        } else { //If not valid input
            return dayRepository.findById(id);
        }
    }

    // All possible inputs: [Day object, null]
    // Invalid inputs => throw IllegalArgumentException
    // Valid inputs => add Day
    public void addDay(Day day) { 
        if (day == null) {
            throw new IllegalArgumentException("Oops, can't save a null day.", null);
        } else {
            dayRepository.save(day);
        }
    }

    public void deleteDay(Long id) { dayRepository.deleteById(id);}

    public Boolean updateDay(Long id, Day day) {
        Optional<Day> foundDay = getDayById(id);

        if(foundDay.isEmpty()) {
            return Boolean.FALSE;
        }

        Day retrievedDay = foundDay.get();

        if(day.getTitle() != null) {
            retrievedDay.setTitle(day.getTitle());
        }
        if(day.getDescription() != null) {
            retrievedDay.setDescription(day.getDescription());
        }
        if(day.getExercises() != null) {
            retrievedDay.setExercises(day.getExercises());
        }

        addDay(retrievedDay);
        return Boolean.TRUE;
    }
}
