package com.gwrteam.gwrteam.repository;

import com.gwrteam.gwrteam.model.PorscheTableMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PorscheMainRepository extends JpaRepository<PorscheTableMain, Long> {
    @Override
    List<PorscheTableMain> findAll();
}