package com.example.JustWork.repository;

import com.example.JustWork.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetRepository extends JpaRepository<Set, Long>, JpaSpecificationExecutor<Set> {

    @Override
    List<Set> findAll();
    List<Set> findByType(String type);

}
