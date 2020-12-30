package com.magicminer;

import com.magicminer.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class GameTableController {

    @Autowired
    private GameTableRepository gameTableRepository;

    @Autowired
    private GameTableCustomRepository gameTableCustomRepository;

    @RequestMapping(value = "/gameTable", method = RequestMethod.GET)
    public ResponseEntity<List<GameTable>> getGameTables() {
        List<GameTable> list = new ArrayList<>();
        gameTableRepository.findByActiveTrue().forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/gameTable/{gameTableId}", method = RequestMethod.GET)
    public ResponseEntity<GameTable> getGameTableById(@PathVariable("gameTableId") Long gameTableId) {
        return ResponseEntity.ok(gameTableRepository.findById(gameTableId).get());
    }

    @RequestMapping(value = "/gameTable", method = RequestMethod.PUT)
    public ResponseEntity<GameTable> put(@RequestBody(required = true) GameTable gameTable) {
        GameTable newGameTable = gameTableRepository.save(gameTable);
        return ResponseEntity.ok(newGameTable);
    }

    @RequestMapping(value = "/gameTable", method = RequestMethod.POST)
    public ResponseEntity<GameTable> post(@RequestBody(required = true) GameTable gameTable) {
        GameTable newGameTable = gameTableRepository.save(gameTable);
        return ResponseEntity.ok(newGameTable);
    }

    @RequestMapping(value = "/gameTable/{gameTableId}", method = RequestMethod.POST)
    public ResponseEntity<GameTable> post(@PathVariable("gameTableId") Long gameTableId) {
        return ResponseEntity.ok(gameTableCustomRepository.cloneGameTable(gameTableId));
    }

    @RequestMapping(value = "/gameTable/{gameTableId}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> delete(@PathVariable("gameTableId") Long gameTableId) {
        Optional<GameTable> optionalGameTable = gameTableRepository.findById(gameTableId);
        if(optionalGameTable.isPresent()){
            GameTable gameTable = optionalGameTable.get();
            gameTable.setActive(false);
            gameTableRepository.save(gameTable);
        }
        return ResponseEntity.ok(gameTableId);
    }

}
