package com.magicminer.model.entity;

import com.magicminer.model.LotoFacilGame;

import java.util.List;

public interface GameCustomRepository {

    List<LotoFacilGame> getAllGames();

    List<LotoFacilGame> getGamesByGameTableId(Long gameTableId);
}
