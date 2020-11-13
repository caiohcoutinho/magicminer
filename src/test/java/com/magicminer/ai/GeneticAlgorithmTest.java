package com.magicminer.ai;

import com.magicminer.model.LotoFacilGame;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GeneticAlgorithmTest {

    private static final long RANDOM_SEED = 123123123123123L;
    private static final int NUMBER_OF_EXPERIMENTS = 1;

    @Test
    public void testClassic2076(){
        GeneticAlgorithm algorithm = GeneticAlgorithm.createBySeed(RANDOM_SEED);
        algorithm.generateSuperSpecimen();
        LotoFacilGame run = algorithm.run();
        Assert.assertTrue(run.getValid());
        Assert.assertEquals(8, run.score(LotoFacilGame.getClassicResult(2076)));
    }

    @Test
    public void testClassic2077(){
        GeneticAlgorithm algorithm = GeneticAlgorithm.createBySeed(RANDOM_SEED);
        algorithm.setGames(Lists.newArrayList(LotoFacilGame.getClassicResult(2076)));
        algorithm.generateSuperSpecimen();
        LotoFacilGame run = algorithm.run();
        Assert.assertTrue(run.getValid());
        Assert.assertEquals(7, run.score(LotoFacilGame.getClassicResult(2077)));
    }

    @Test
    public void testClassic2078(){
        GeneticAlgorithm algorithm = GeneticAlgorithm.createBySeed(RANDOM_SEED);
        algorithm.setGames(Lists.newArrayList(LotoFacilGame.getClassicResult(2076), LotoFacilGame.getClassicResult(2078)));
        algorithm.generateSuperSpecimen();
        LotoFacilGame run = algorithm.run();
        Assert.assertTrue(run.getValid());
        Assert.assertEquals(10, run.score(LotoFacilGame.getClassicResult(2078)));
    }

    @Test
    public void testClassic2079(){
        GeneticAlgorithm algorithm = GeneticAlgorithm.createBySeed(RANDOM_SEED);
        algorithm.setGames(Lists.newArrayList(LotoFacilGame.getClassicResult(2076), LotoFacilGame.getClassicResult(2078),
                LotoFacilGame.getClassicResult(2079)));
        algorithm.generateSuperSpecimen();
        LotoFacilGame run = algorithm.run();
        Assert.assertTrue(run.getValid());
        Assert.assertEquals(11, run.score(LotoFacilGame.getClassicResult(2079)));
    }

    @Test
    public void average(){

        double sum = 0.0;

        List<LotoFacilGame> games = Lists.newArrayList();
        for(int j = 1979; j <= 2039; j++){
            games.add(LotoFacilGame.getClassicResult(j));
        }

        for(int i = 0; i < NUMBER_OF_EXPERIMENTS; i++){
            GeneticAlgorithm algorithm = GeneticAlgorithm.create();
            algorithm.setGames(games);
            algorithm.generateSuperSpecimen();

            double thisExecutionScore = 0.0;
            for(int j = 1979; j <= 2039; j++){
                thisExecutionScore += algorithm.run().score(LotoFacilGame.getClassicResult(j));
            }
            sum += thisExecutionScore / (2039 - 1979 + 1);
        }

        System.out.println("Genetic Algorithm average score = "+sum/(1.0*NUMBER_OF_EXPERIMENTS));
    }
}
