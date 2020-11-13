package com.magicminer.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Boolean.TRUE;

public class LotoFacilGame implements Game {

    public static final Map<Integer, LotoFacilGame> CLASSIC_RESULTS;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final int GAME_SIZE = 25;
    public static final int VALID_GAME_SIZE = 15;

    private Boolean[] balls = new Boolean[GAME_SIZE];
    private int gameNumber = 0;
    private LocalDate date;

    static {
        String classicGames = null;
        try {
            classicGames = new String(Files.readAllBytes(Paths.get(LotoFacilGame.class.getResource("/LotoFacilPreviousGames.csv").toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Unable to read LotoFacilPreviousGames.csv file");
        }
        List<String> games = Arrays.stream(classicGames.split("\n")).collect(Collectors.toList());

        CLASSIC_RESULTS = new HashMap<>();

        Collections.sort(games, Comparator.comparingInt(g -> Integer.parseInt(g.split(";")[0])));

        games.forEach(r -> {
            List<String> columns = Arrays.asList(r.split(";"));
            int gameNumber = Integer.parseInt(columns.get(0));
            LocalDate date = LocalDate.parse(columns.get(1), DATE_TIME_FORMATTER);

            Boolean[] balls = IntStream.rangeClosed(0, games.size()).boxed().map(t -> false)
                    .collect(Collectors.toList()).toArray(new Boolean[GAME_SIZE]);

            IntStream.rangeClosed(0, 14).boxed().forEach(i -> {
                balls[Integer.parseInt(columns.get(i+2))-1] = true;
            });

            LotoFacilGame game = LotoFacilGame.createByBalls(balls);
            game.setGameNumber(gameNumber);
            game.setDate(date);

            CLASSIC_RESULTS.put(gameNumber, game);
        });
    }

    private LotoFacilGame(){}

    private LotoFacilGame(Boolean[] balls) {
        this.balls = balls;
    }

    public static LotoFacilGame create(){
        return new LotoFacilGame();
    }

    public static LotoFacilGame createByBalls(Boolean[] balls){
        return new LotoFacilGame(balls);
    }

    public static LotoFacilGame getClassicResult(int index){
        return CLASSIC_RESULTS.get(index);
    }

    public boolean getValid(){
        return Arrays.stream(this.balls).filter(b -> b == TRUE).count() == VALID_GAME_SIZE;
    }

    public Boolean[] getBalls() {
        return balls;
    }

    public void setBalls(Boolean[] balls) {
        this.balls = balls;
    }

    public void setBall(int index, boolean value){
        this.balls[index] = value;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int score(Game res) {
        LotoFacilGame result = (LotoFacilGame) res;
        Boolean[] balls = this.getBalls();
        Boolean[] resultBalls = result.getBalls();
        int score = 0;
        for(int i = 0; i < GAME_SIZE; i++){
            if(balls[i] && resultBalls[i]){
                score++;
            }
        }
        return score;
    }
}
