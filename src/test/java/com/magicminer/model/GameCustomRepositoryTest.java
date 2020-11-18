package com.magicminer.model;

import com.magicminer.model.entity.GameCustomRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class GameCustomRepositoryTest {

    @Autowired
    private GameCustomRepository gameCustomRepository;

    @Test
    public void testRepositories(){
        Map<Integer, LotoFacilGame> databaseGames = gameCustomRepository.getAllGames().stream().collect(
                Collectors.toMap(LotoFacilGame::getGameNumber, Function.identity()));
        Map<Integer, LotoFacilGame> resourceGames = LotoFacilGame.CLASSIC_RESULTS;

        databaseGames.entrySet().stream().forEach(entry ->
            Assert.assertEquals(entry.getValue(), resourceGames.get(entry.getKey()))
        );
        resourceGames.entrySet().stream().forEach(entry -> {
            if(entry.getValue().getValid()){
                Assert.assertEquals(entry.getValue(), databaseGames.get(entry.getKey()));
            };
        });
    }
}
