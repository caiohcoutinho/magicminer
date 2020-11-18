package com.magicminer.model.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, Long> {
    Configuration findByName(String name);
}
