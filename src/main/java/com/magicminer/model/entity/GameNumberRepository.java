package com.magicminer.model.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameNumberRepository extends CrudRepository<GameNumber, Long> {

}
