package com.example.JustWork.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedEntityGraph(name = "day-exercise-graph",
        attributeNodes = @NamedAttributeNode("exercises")
)
@Table(name = "day")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String title;
    private String description;

    @OneToMany(
            mappedBy = "day",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Exercise> exercises = new ArrayList<>();

    public Day() {}

    public Day(String title, String description) {
        this.title = title;
        this.description = description;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Exercise> getExercises() {
        return this.exercises;
    }
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
    public void addExercise(Exercise newExercise) {
        exercises.add(newExercise);
        newExercise.setDay(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return title.equals(day.title) && Objects.equals(description, day.description) && Objects.equals(exercises, day.exercises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, exercises);
    }
}
