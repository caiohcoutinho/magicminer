package com.magicminer.model.entity;

import javax.persistence.*;

@Entity
public class GameTable {
    private Long gameTableId;
    private String name;
    private boolean active = true;

    private GameTable(){

    }

    public GameTable(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gametable_id_generator")
    @SequenceGenerator(name="gametable_id_generator", sequenceName = "gametable_id_seq", allocationSize = 1)
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
