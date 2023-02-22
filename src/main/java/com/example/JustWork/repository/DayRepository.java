package com.example.JustWork.repository;

import com.example.JustWork.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

    List<Day> findAll();

}
