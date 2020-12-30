package com.magicminer.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class GameNumber {
    private Long gameNumberId;
    private LocalDate gameDate;
    private int gameNumber;

    private GameNumber(){

    }

    public GameNumber(LocalDate gameDate, int gameNumber) {
        this.gameDate = gameDate;
        this.gameNumber = gameNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamenumber_id_generator")
    @SequenceGenerator(name="gamenumber_id_generator", sequenceName = "gamenumber_id_seq", allocationSize = 1)
    public Long getGameNumberId() {
        return gameNumberId;
    }

    public void setGameNumberId(Long gameNumberId) {
        this.gameNumberId = gameNumberId;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }
}
