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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ball_id_generator")
    @SequenceGenerator(name="ball_id_generator", sequenceName = "ball_id_seq", allocationSize = 1)
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
