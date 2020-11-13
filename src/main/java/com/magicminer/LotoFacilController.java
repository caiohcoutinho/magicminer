package com.magicminer;

import com.magicminer.model.LotoFacilGame;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LotoFacilController {

    @RequestMapping(value = "/lotofacil", method = RequestMethod.GET)
    public ResponseEntity<List<LotoFacilGame>> getGameList() {
        List<LotoFacilGame> games = new ArrayList<>(LotoFacilGame.CLASSIC_RESULTS.values());
        return ResponseEntity.ok(games);
    }
}
