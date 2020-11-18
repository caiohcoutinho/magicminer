package com.magicminer.model.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTableRepository extends CrudRepository<GameTable, Long> {

}
