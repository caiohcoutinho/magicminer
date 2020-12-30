package com.magicminer.model.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> findByGameTableId(Long gameTableId);
    Game findByGameTableIdAndGameNumberId(Long gameTableId, Long gameNumberId);
}
