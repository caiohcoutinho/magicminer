package com.magicminer.model.entity;

import javax.persistence.*;

@Entity
public class Ball {
    private Long ballId;
    private Long gameId;
    private int ballNumber;

    private Ball(){

    }

    public Ball(Long gameId, int ballNumber) {
        this.gameId = gameId;
        this.ballNumber = ballNumber;
    }

    @Id
    public Long getBallId() {
        return ballId;
    }

    public void setBallId(Long ballId) {
        this.ballId = ballId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber(int ballNumber) {
        this.ballNumber = ballNumber;
    }
}
