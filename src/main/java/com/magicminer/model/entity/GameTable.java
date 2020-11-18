package com.magicminer.model.entity;

import javax.persistence.*;

@Entity
public class GameTable {
    private Long gameTableId;
    private String name;

    private GameTable(){

    }

    public GameTable(String name) {
        this.name = name;
    }

    @Id
    public Long getGameTableId() {
        return gameTableId;
    }

    public void setGameTableId(Long gameTableId) {
        this.gameTableId = gameTableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
