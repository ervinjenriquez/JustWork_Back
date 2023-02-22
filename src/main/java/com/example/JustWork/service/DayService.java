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

    //if id(1) return correct day
    //if id(2) return 404 (id 2 doesn't exist)
    //if id(null) return 400
    public Optional<Day> getDayById(Long id) {
        if (id != null) { //If valid input
            return dayRepository.findById(id);
        } else { //If not valid input
            throw new IllegalArgumentException();
        }
    }

    public void saveDay(Day day) { dayRepository.save(day);}

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

        saveDay(retrievedDay);
        return Boolean.TRUE;
    }
}
