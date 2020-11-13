package com.magicminer.ai;

import com.magicminer.model.LotoFacilGame;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

public class Specimen {
    private double[] genes;
    private double score;

    public Specimen() {
        this.genes = new double[LotoFacilGame.GAME_SIZE];
    }

    public Specimen(double[] genes) {
        this.genes = genes;
    }

    public Specimen(Random random) {
        this();
        for(int i = 0 ; i < LotoFacilGame.GAME_SIZE; i++){
            this.genes[i] = random.nextDouble();
        }
    }

    public double[] getGenes() {
        return genes;
    }

    public void setGenes(double[] genes) {
        this.genes = genes;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public LotoFacilGame run(Random random){
        int count = 0;
        Boolean[] balls = new Boolean[LotoFacilGame.GAME_SIZE];
        for(int i = 0; i < LotoFacilGame.GAME_SIZE; i++){
            boolean value = false;
            if(LotoFacilGame.VALID_GAME_SIZE - count >= LotoFacilGame.GAME_SIZE - i + 1 || random.nextFloat() * genes[i] > 0.5){
                value = true;
                count++;
            }
            balls[i] = value;
        }
        return LotoFacilGame.createByBalls(balls);
    }

    public double rankScore(Random random, List<LotoFacilGame> games){
        if(CollectionUtils.isEmpty(games)){
            return 0.5;
        }
        this.score = games.stream().mapToDouble(g -> this.run(random).score(g)).average().getAsDouble();
        return this.score;
    }
}
