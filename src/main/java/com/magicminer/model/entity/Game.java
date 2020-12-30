package com.magicminer.model.entity;

import javax.persistence.*;

@Entity
public class Game {
    private Long gameId;
    private Long gameTableId;
    private Long gameNumberId;

    private Game(){

    }

    public Game(Long gameTableId, Long gameNumberId) {
        this.gameTableId = gameTableId;
        this.gameNumberId = gameNumberId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_id_generator")
    @SequenceGenerator(name="game_id_generator", sequenceName = "game_id_seq", allocationSize = 1)
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGameTableId() {
        return gameTableId;
    }

    public void setGameTableId(Long gameTableId) {
        this.gameTableId = gameTableId;
    }

    public Long getGameNumberId() {
        return gameNumberId;
    }

    public void setGameNumberId(Long gameNumberId) {
        this.gameNumberId = gameNumberId;
    }
}
