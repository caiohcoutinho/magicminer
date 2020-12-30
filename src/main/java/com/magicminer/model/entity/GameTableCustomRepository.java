package com.magicminer.model.entity;

import org.springframework.stereotype.Repository;

@Repository
public interface GameTableCustomRepository{
    GameTable cloneGameTable(Long gameTableId);
}
