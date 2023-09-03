package com.gwrteam.gwrteam.repository;

import com.gwrteam.gwrteam.model.GT4TableLast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GT4LastRepository extends JpaRepository<GT4TableLast, Long> {
    @Override
    List<GT4TableLast> findAll();
}