package com.magicminer;

import com.magicminer.model.entity.Configuration;
import com.magicminer.model.entity.ConfigurationRepository;
import com.magicminer.model.entity.GameTable;
import com.magicminer.model.entity.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameTableController {

    @Autowired
    private GameTableRepository gameTableRepository;

    @RequestMapping(value = "/gameTable", method = RequestMethod.GET)
    public ResponseEntity<List<GameTable>> getGameTables() {
        List<GameTable> list = new ArrayList<>();
        gameTableRepository.findAll().forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/gameTable/{gameTableId}", method = RequestMethod.GET)
    public ResponseEntity<GameTable> getGameTableById(@PathVariable("gameTableId") Long gameTableId) {
        return ResponseEntity.ok(gameTableRepository.findById(gameTableId).get());
    }
}
