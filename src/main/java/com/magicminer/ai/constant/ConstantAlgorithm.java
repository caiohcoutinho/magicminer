package com.magicminer.ai.constant;

import com.magicminer.ai.Algorithm;
import com.magicminer.model.LotoFacilGame;

import java.util.Random;

public class ConstantAlgorithm implements Algorithm<LotoFacilGame> {

    private LotoFacilGame constantGame;

    private ConstantAlgorithm(){};

    private ConstantAlgorithm(Random random) {
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
        this.constantGame = LotoFacilGame.createByBalls(balls);
    }

    public static ConstantAlgorithm create(){
        return new ConstantAlgorithm(new Random());
    }

    public static ConstantAlgorithm createBySeed(long seed){
        Random random = new Random();
        random.setSeed(seed);
        return new ConstantAlgorithm(random);
    }

    @Override
    public LotoFacilGame run() {
        return LotoFacilGame.createByBalls(this.constantGame.getBalls());
    }
}
