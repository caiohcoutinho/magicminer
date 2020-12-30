package com.magicminer.model.entity;

import com.magicminer.model.LotoFacilGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class GameCustomRepositoryImpl implements GameCustomRepository {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameNumberRepository gameNumberRepository;

    @Autowired
    private BallRepository ballRepository;

    @Override
    public List<LotoFacilGame> getAllGames() {
        List<LotoFacilGame> results = new ArrayList<>();

        Iterable<Game> games = gameRepository.findAll();

        Map<Long, GameNumber> gameNumberMap = new HashMap<>();
        gameNumberRepository.findAll().forEach(gm -> gameNumberMap.put(gm.getGameNumberId(), gm));

        Map<Long, List<Ball>> ballMap = new HashMap<>();
        ballRepository.findAll().forEach(b -> {
            Long key = b.getGameId();
            if(!ballMap.containsKey(key)){
                ballMap.put(key, new ArrayList<>());
            }
            ballMap.get(key).add(b);
        });

        games.forEach(g -> {

            List<Ball> ballsList = ballMap.get(g.getGameId());

            GameNumber gameNumber = gameNumberMap.get(g.getGameNumberId());

            Boolean[] balls = IntStream.rangeClosed(0, LotoFacilGame.GAME_SIZE).boxed().map(t -> false)
                    .collect(Collectors.toList()).toArray(new Boolean[LotoFacilGame.GAME_SIZE]);

            ballsList.stream().forEach(b -> {
                balls[b.getBallNumber()] = true;
            });

            LotoFacilGame game = LotoFacilGame.createByBalls(balls);
            game.setGameNumber(gameNumber.getGameNumber());
            game.setDate(gameNumber.getGameDate());

            results.add(game);
        });

        return results;
    }

    @Override
    public List<LotoFacilGame> getGamesByGameTableId(Long gameTableId) {
        List<LotoFacilGame> results = new ArrayList<>();

        List<Game> games = gameRepository.findByGameTableId(gameTableId);
        List<Long> gameIdList = games.stream().map(Game::getGameId).collect(Collectors.toList());

        Map<Long, GameNumber> gameNumberMap = new HashMap<>();
        gameNumberRepository.findAll().forEach(gm -> gameNumberMap.put(gm.getGameNumberId(), gm));

        Map<Long, List<Ball>> ballMap = new HashMap<>();
        ballRepository.findByGameIdIn(gameIdList).forEach(b -> {
            Long key = b.getGameId();
            if(!ballMap.containsKey(key)){
                ballMap.put(key, new ArrayList<>());
            }
            ballMap.get(key).add(b);
        });

        games.forEach(g -> {

            List<Ball> ballsList = ballMap.get(g.getGameId());

            GameNumber gameNumber = gameNumberMap.get(g.getGameNumberId());

            Boolean[] balls = IntStream.rangeClosed(0, LotoFacilGame.GAME_SIZE).boxed().map(t -> false)
                    .collect(Collectors.toList()).toArray(new Boolean[LotoFacilGame.GAME_SIZE]);

            if(ballsList != null) {
                ballsList.stream().forEach(b -> {
                    balls[b.getBallNumber()] = true;
                });
            }

            LotoFacilGame game = LotoFacilGame.createByBalls(balls);
            game.setGameNumber(gameNumber.getGameNumber());
            game.setDate(gameNumber.getGameDate());

            results.add(game);
        });

        return results;
    }
}
