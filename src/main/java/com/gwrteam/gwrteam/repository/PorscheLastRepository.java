package com.gwrteam.gwrteam.repository;

import com.gwrteam.gwrteam.model.PorscheTableLast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PorscheLastRepository extends JpaRepository<PorscheTableLast, Long> {
    @Override
    List<PorscheTableLast> findAll();
}