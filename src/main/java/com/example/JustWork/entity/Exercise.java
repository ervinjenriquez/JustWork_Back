package com.example.JustWork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "exercise")
public class Exercise {

    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String title;
    @OneToMany(
            mappedBy = "exercise",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private List<Set> sets = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    private Day day;

    //Constructors
    public Exercise() {}

    public Exercise(String title) {
        this.title = title;
    }

    public List<Set> getSets() {return this.sets;}
    public void setSets(List<Set> set) { this.sets = set; }
    public void addSet(Set newSet) {
        sets.add(newSet);
        newSet.setExercise(this);
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

    public Day getDay() {
        return day;
    }
    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return id.equals(exercise.id) && title.equals(exercise.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
