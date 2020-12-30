package com.magicminer;

import com.magicminer.ai.constant.ConstantAlgorithm;
import com.magicminer.ai.genetic.GeneticAlgorithm;
import com.magicminer.ai.random.RandomAlgorithm;
import com.magicminer.model.LotoFacilGame;
import com.magicminer.model.entity.GameCustomRepository;
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
public class LotoFacilController {

    @Autowired
    private GameCustomRepository gameCustomRepository;

    @RequestMapping(value = "/lotofacil/{gameTableId}", method = RequestMethod.GET)
    public ResponseEntity<List<LotoFacilGame>> getGameList(@PathVariable("gameTableId") Long gameTableId) {
        return ResponseEntity.ok(gameCustomRepository.getGamesByGameTableId(gameTableId));
    }

    @RequestMapping(value = "/lotofacil/random", method = RequestMethod.GET)
    public ResponseEntity<List<LotoFacilGame>> runRandomAlgorithm(@RequestParam(name="seed", required = false) Long seed) {
        List<LotoFacilGame> games = new ArrayList<>(LotoFacilGame.CLASSIC_RESULTS.values());
        RandomAlgorithm algorithm = seed != null ? RandomAlgorithm.createBySeed(seed) : RandomAlgorithm.create();
        List<LotoFacilGame> result = games.stream()
                .filter(g -> g.getValid())
                .sorted(Comparator.comparingInt(LotoFacilGame::getGameNumber))
                .map(g -> {
                    LotoFacilGame execution = algorithm.run();
                    execution.score(g);
                    execution.setGameNumber(g.getGameNumber());
                    return execution;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/lotofacil/genetic", method = RequestMethod.GET)
    public ResponseEntity<List<LotoFacilGame>> runGeneticAlgorithm(@RequestParam(name="seed", required = false) Long seed) {
        List<LotoFacilGame> games = new ArrayList<>(LotoFacilGame.CLASSIC_RESULTS.values());
        GeneticAlgorithm algorithm = seed != null ? GeneticAlgorithm.createBySeed(seed) : GeneticAlgorithm.create();
        algorithm.setGames(games);
        algorithm.generateSuperSpecimen();
        List<LotoFacilGame> result = games.stream()
                .filter(g -> g.getValid())
                .sorted(Comparator.comparingInt(LotoFacilGame::getGameNumber))
                .map(g -> {
                    LotoFacilGame execution = algorithm.run();
                    execution.score(g);
                    execution.setGameNumber(g.getGameNumber());
                    return execution;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/lotofacil/constant", method = RequestMethod.GET)
    public ResponseEntity<List<LotoFacilGame>> runConstantAlgorithm(@RequestParam(name="seed", required = false) Long seed) {
        List<LotoFacilGame> games = new ArrayList<>(LotoFacilGame.CLASSIC_RESULTS.values());
        ConstantAlgorithm algorithm = seed != null ? ConstantAlgorithm.createBySeed(seed) : ConstantAlgorithm.create();
        List<LotoFacilGame> result = games.stream()
                .filter(g -> g.getValid())
                .sorted(Comparator.comparingInt(LotoFacilGame::getGameNumber))
                .map(g -> {
                    LotoFacilGame execution = algorithm.run();
                    execution.score(g);
                    execution.setGameNumber(g.getGameNumber());
                    return execution;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
