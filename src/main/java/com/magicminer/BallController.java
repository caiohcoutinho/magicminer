package com.magicminer;

import com.magicminer.ai.constant.ConstantAlgorithm;
import com.magicminer.ai.genetic.GeneticAlgorithm;
import com.magicminer.ai.random.RandomAlgorithm;
import com.magicminer.model.LotoFacilGame;
import com.magicminer.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BallController {

    @Autowired
    private BallRepository ballRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameNumberRepository gameNumberRepository;

    @RequestMapping(value = "/ball/{gameTableId}/{gameNumber}/{ballNumber}", method = RequestMethod.POST)
    public ResponseEntity<Ball> post(@PathVariable("gameTableId") Long gameTableId,
                     @PathVariable("gameNumber") int gameNumber, @PathVariable("ballNumber") int ballNumber) {
        GameNumber gameNumberEntity = gameNumberRepository.findByGameNumber(gameNumber);
        Game game = findOrCreateGame(gameTableId, gameNumberEntity.getGameNumberId());
        Ball newBall = ballRepository.save(new Ball(game.getGameId(), ballNumber));
        return ResponseEntity.ok(newBall);
    }

    @RequestMapping(value = "/ball/{gameTableId}/{gameNumber}/{ballNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<Ball> delete(@PathVariable("gameTableId") Long gameTableId,
            @PathVariable("gameNumber") int gameNumber, @PathVariable("ballNumber") int ballNumber) {
        GameNumber gameNumberEntity = gameNumberRepository.findByGameNumber(gameNumber);
        Game game = findOrCreateGame(gameTableId, gameNumberEntity.getGameNumberId());
        Ball ball = ballRepository.findByGameIdAndBallNumber(game.getGameId(), ballNumber);
        ballRepository.delete(ball);
        return ResponseEntity.ok(ball);
    }

    private Game findOrCreateGame(@PathVariable("gameTableId") Long gameTableId, Long gameNumberId) {
        Game game = gameRepository.findByGameTableIdAndGameNumberId(gameTableId, gameNumberId);
        if(game != null){
            return game;
        }
        Game newGame = new Game(gameTableId, gameNumberId);
        newGame = gameRepository.save(newGame);
        return newGame;
    }


}
