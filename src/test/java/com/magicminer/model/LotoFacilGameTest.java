package com.magicminer.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

public class LotoFacilGameTest {

    @Test
    public void testPerfectScore(){
        Boolean[] balls = new Boolean[]{
                true, false, true, false, true,
                true, false, true, false, true,
                true, false, true, false, true,
                true, false, true, false, true,
                true, false, true, false, true
        };
        LotoFacilGame g1 = LotoFacilGame.createByBalls(balls);
        LotoFacilGame g2 = LotoFacilGame.createByBalls(balls);

        Assert.assertEquals(25, g1.score(g2));
    }

    @Test
    public void testZeroScore(){
        Boolean[] balls = new Boolean[]{
                true, false, true, false, true,
                true, false, true, false, true,
                true, false, true, false, true,
                true, false, true, false, true,
                true, false, true, false, true
        };
        Boolean[] reverse = new Boolean[balls.length];
        for(int i = 0 ; i < balls.length; i ++){
            reverse[i] = !balls[i];
        }
        LotoFacilGame g1 = LotoFacilGame.createByBalls(balls);
        LotoFacilGame g2 = LotoFacilGame.createByBalls(reverse);

        Assert.assertEquals(0, g1.score(g2));
    }

    @Test
    public void testClassicResultsAreValid(){
        IntStream.rangeClosed(1979, 2039).boxed().forEach(i -> {
            Assert.assertTrue(i+"-th classic result should be valid.", LotoFacilGame.getClassicResult(i).getValid());
        });
    }

    @Test
    public void generateTableInsertSqlTest(){
        String gameTableName = "Caixa";
        log("insert into GameTable(name) values('"+gameTableName+"');");

        LotoFacilGame.CLASSIC_RESULTS.values().stream().forEach(game -> {
            log("insert into gamenumber(gamedate,gamenumber) values ('" + game.getDate() + "', '" + game.getGameNumber() + "');");
        });

        LotoFacilGame.CLASSIC_RESULTS.values().stream().forEach(game -> {
            log("insert into game(gametableid, gamenumberid) values ((select gametableid from gametable where name = '"+gameTableName+"'), (select gamenumberid from gamenumber where gamenumber = '"+game.getGameNumber()+"'));");
        });

        LotoFacilGame.CLASSIC_RESULTS.values().stream().forEach(game -> {
            Boolean[] balls = game.getBalls();
            IntStream.rangeClosed(0, 24).boxed().forEach(i -> {
                if (balls[i]) {
                    log("insert into ball(gameid, ballnumber) values ((select gameid from game where gamenumberid = (select gamenumberid from gamenumber where gamenumber = " + game.getGameNumber() + ") and gametableid = (select gametableid from gametable where name = '" + gameTableName + "')) , " + i + ");");
                }
            });
        });


    }

    public void log(String text){
        System.out.println(text);
    }
}
