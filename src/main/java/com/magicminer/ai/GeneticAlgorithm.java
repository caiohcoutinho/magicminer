package com.magicminer.ai;

import com.magicminer.model.LotoFacilGame;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneticAlgorithm implements Algorithm<LotoFacilGame> {

    private static final int FARM_SIZE = 100;
    private static final int ITERATIONS = 50;
    private static final boolean DEBUG = true;
    private static final double MUTATION_CHANCE = 0.1;

    private List<LotoFacilGame> games;
    private Specimen superSpecimen;

    private GeneticAlgorithm(){}

    public GeneticAlgorithm(Random random) {
        this.random = random;
    }

    private Random random;

    public static GeneticAlgorithm create(){
        return new GeneticAlgorithm(new Random());
    }

    public static GeneticAlgorithm createBySeed(long seed){
        Random random = new Random();
        random.setSeed(seed);
        return new GeneticAlgorithm(random);
    }

    public List<LotoFacilGame> getGames() {
        return games;
    }

    public void setGames(List<LotoFacilGame> games) {
        this.games = games;
    }

    public void log(String text){
        if(DEBUG){
            System.out.println(text);
        }
    }

    public void generateSuperSpecimen() {
        this.log("Starting Genetic Algorithm.");
        List<Specimen> farm = generateInitialFarm();
        farm.stream().forEach(s -> s.rankScore(random, games));

        for(int i = 0; i < ITERATIONS; i++) {
            farm.sort(Comparator.comparingDouble(Specimen::getScore));
            System.out.println("Fit = "+farm.get(FARM_SIZE-1).getScore());

            // parents
            Specimen p1 = farm.get(FARM_SIZE - 2);
            Specimen p2 = farm.get(FARM_SIZE - 1);

            // kill the worst
            farm.remove(0);

            Specimen offspring = generateOffspring(p1, p2);
            offspring.rankScore(random, games);

            farm.add(offspring);
        }

        farm.sort(Comparator.comparingDouble(Specimen::getScore));
        this.superSpecimen = farm.get(FARM_SIZE-1);
    }

    @Override
    public LotoFacilGame run() {
        return this.superSpecimen.run(this.random);
    }

    private Specimen generateOffspring(Specimen p1, Specimen p2) {
        double[] genes = new double[LotoFacilGame.GAME_SIZE];
        for(int i = 0 ; i < LotoFacilGame.GAME_SIZE; i++){
            if(this.random.nextDouble() < MUTATION_CHANCE){
                genes[i] = this.random.nextDouble();
            } else {
                genes[i] = (p1.getGenes()[i] + p2.getGenes()[i]) / 2.0;
            }
        }
        return new Specimen(genes);
    }

    private List<Specimen> generateInitialFarm() {
        return IntStream.range(0, FARM_SIZE).boxed().map(i -> new Specimen(random)).collect(Collectors.toList());
    }
}
