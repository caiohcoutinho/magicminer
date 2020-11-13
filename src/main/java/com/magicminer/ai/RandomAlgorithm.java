package com.magicminer.ai;

import com.magicminer.model.LotoFacilGame;

import java.util.Random;

public class RandomAlgorithm implements Algorithm<LotoFacilGame> {

    private RandomAlgorithm(){};

    public RandomAlgorithm(Random random) {
        this.random = random;
    }
    private Random random;

    public static RandomAlgorithm create(){
        return new RandomAlgorithm(new Random());
    }

    public static RandomAlgorithm createBySeed(long seed){
        Random random = new Random();
        random.setSeed(seed);
        return new RandomAlgorithm(random);
    }

    @Override
    public LotoFacilGame run() {
        int count = 0;
        Boolean[] balls = new Boolean[LotoFacilGame.GAME_SIZE];
        for(int i = 0; i < LotoFacilGame.GAME_SIZE; i++){
            boolean value = false;
            if(count < LotoFacilGame.VALID_GAME_SIZE && (LotoFacilGame.VALID_GAME_SIZE - count >= LotoFacilGame.GAME_SIZE - i || random.nextBoolean())){
                value = true;
                count++;
            }
            balls[i] = value;
        }
        return LotoFacilGame.createByBalls(balls);
    }
}
