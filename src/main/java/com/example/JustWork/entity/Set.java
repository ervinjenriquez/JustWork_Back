package com.example.JustWork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "set")
public class Set {
    @Id
    @GeneratedValue
    private Long id;

    private int week;
    private int number;
    private int weight;
    private int reps;
    private int difficulty;
    private String type;

    public Set() {
    }

    public Set(int week, int number, int weight, int reps, int difficulty, String type) {
        this.type = type;
        this.number = number;
        this.weight = weight;
        this.reps = reps;
        this.difficulty = difficulty;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Set set = (Set) o;
        return week == set.week && number == set.number && weight == set.weight && reps == set.reps && difficulty == set.difficulty && id.equals(set.id) && Objects.equals(type, set.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, week, number, weight, reps, difficulty, type);
    }
}
