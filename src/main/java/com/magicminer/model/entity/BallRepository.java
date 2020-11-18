package com.magicminer.model.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BallRepository extends CrudRepository<Ball, Long> {
    List<Ball> findByGameId(Long gameId);
    List<Ball> findByGameIdIn(List<Long> gameId);
}
