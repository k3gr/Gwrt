package com.gwrteam.gwrteam.repository;

import com.gwrteam.gwrteam.model.UserLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserLeagueRepository extends JpaRepository<UserLeague, Long> {
    List<UserLeague> findAllByLeagueId(Long league_id);
    Optional <UserLeague> findByUserId(Long userId);
    Optional <UserLeague> findUserLeagueByUserId(Long id);
    Optional <UserLeague> findUserLeagueByUserIdAndAndLeagueId(Long id, Long leagueId);
}
