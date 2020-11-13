package com.magicminer.ai.constant;

import com.magicminer.ai.random.RandomAlgorithm;
import com.magicminer.model.LotoFacilGame;
import org.junit.Assert;
import org.junit.Test;

public class ConstantAlgorithmTest {

    private static final long RANDOM_SEED = 123123123123123L;
    private static final int NUMBER_OF_EXPERIMENTS = 1000;

    @Test
    public void testClassic2076(){
        ConstantAlgorithm algorithm = ConstantAlgorithm.createBySeed(RANDOM_SEED);
        LotoFacilGame result = algorithm.run();
        Assert.assertTrue(result.getValid());
        Assert.assertEquals(10, result.score(LotoFacilGame.getClassicResult(2076)));
    }

    @Test
    public void testClassic2077(){
        ConstantAlgorithm algorithm = ConstantAlgorithm.createBySeed(RANDOM_SEED);
        LotoFacilGame result = algorithm.run();
        Assert.assertTrue(result.getValid());
        Assert.assertEquals(10, result.score(LotoFacilGame.getClassicResult(2077)));
    }

    @Test
    public void testClassic2078(){
        ConstantAlgorithm algorithm = ConstantAlgorithm.createBySeed(RANDOM_SEED);
        LotoFacilGame result = algorithm.run();
        Assert.assertTrue(result.getValid());
        Assert.assertEquals(9, result.score(LotoFacilGame.getClassicResult(2078)));
    }

    @Test
    public void testClassic2079(){
        ConstantAlgorithm algorithm = ConstantAlgorithm.createBySeed(RANDOM_SEED);
        LotoFacilGame result = algorithm.run();
        Assert.assertTrue(result.getValid());
        Assert.assertEquals(9, result.score(LotoFacilGame.getClassicResult(2079)));
    }

    @Test
    public void average(){

        double sum = 0.0;

        for(int i = 0; i < NUMBER_OF_EXPERIMENTS; i++){
            ConstantAlgorithm algorithm = ConstantAlgorithm.create();
            double thisExecutionScore = 0.0;
            for(int j = 1979; j <= 2039; j++){
                thisExecutionScore += algorithm.run().score(LotoFacilGame.getClassicResult(j));
            }
            sum += thisExecutionScore / (2039 - 1979 + 1);
        }

        System.out.println("Random Algorithm average score = "+sum/(1.0*NUMBER_OF_EXPERIMENTS));
    }
}
