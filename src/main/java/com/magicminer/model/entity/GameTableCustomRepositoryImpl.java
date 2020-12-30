package com.magicminer.model.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class GameTableCustomRepositoryImpl implements GameTableCustomRepository{

    @Autowired
    private GameTableRepository gameTableRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private BallRepository ballRepository;

    @Override
    public GameTable cloneGameTable(Long gameTableId) {
        GameTable originalGameTable = gameTableRepository.findById(gameTableId).get();
        GameTable newGameTable = new GameTable(originalGameTable.getName()+" Clone");
        gameTableRepository.save(newGameTable);

        List<Game> originalGames = gameRepository.findByGameTableId(gameTableId);

        Map<Long, List<Ball>> ballMap = new HashMap<>();
        ballRepository.findByGameIdIn(originalGames.stream().map(Game::getGameId).collect(Collectors.toList())).forEach(b -> {
            Long key = b.getGameId();
            if(!ballMap.containsKey(key)){
                ballMap.put(key, new ArrayList<>());
            }
            ballMap.get(key).add(b);
        });;

        for (Game originalGame : originalGames) {
            Game newGame = new Game(newGameTable.getGameTableId(), originalGame.getGameNumberId());
            gameRepository.save(newGame);

            List<Ball> balls = ballMap.get(originalGame.getGameId());
            if(!CollectionUtils.isEmpty(balls)){
                for (Ball ball : balls) {
                    Ball newBall = new Ball(newGame.getGameId(), ball.getBallNumber());
                    ballRepository.save(newBall);
                }
            }
        }

        return newGameTable;
    }
}
