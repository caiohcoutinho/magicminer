package com.magicminer.model.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameTableRepository extends CrudRepository<GameTable, Long> {
    List<GameTable> findByActiveTrue();
}
