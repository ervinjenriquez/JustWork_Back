package com.example.JustWork.service;

import com.example.JustWork.entity.Exercise;
import com.example.JustWork.entity.Set;
import com.example.JustWork.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetService {

    @Autowired
    private SetRepository setRepository;

    public List<Set> getSets() { return setRepository.findAll();}

    public Optional<Set> getSetById(Long id) { return setRepository.findById(id);}

    public void saveSet(Set set) {
        setRepository.save(set);
    }

    public void deleteSet(Long id) { setRepository.deleteById(id);}

    public Boolean updateSet(Long id, Set set) {
        Optional<Set> foundSet = getSetById(id);

        if(foundSet.isEmpty()) {
            return Boolean.FALSE;
        }

        Set retrievedSet = foundSet.get();

        if (set.getExercise() != null) {
            retrievedSet.setExercise(set.getExercise());
        }
        if (set.getDifficulty() >= 1 && set.getDifficulty() <= 10) {
            retrievedSet.setDifficulty(set.getDifficulty());
        }
        if (set.getNumber() > 0) {
            retrievedSet.setNumber(set.getNumber());
        }
        if (set.getType() != null) {
            retrievedSet.setType(set.getType());
        }

        saveSet(retrievedSet);
        return Boolean.TRUE;
    }
}
