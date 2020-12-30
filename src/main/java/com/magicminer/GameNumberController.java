package com.magicminer;

import com.magicminer.model.entity.GameNumber;
import com.magicminer.model.entity.GameNumberRepository;
import com.magicminer.model.entity.GameTable;
import com.magicminer.model.entity.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameNumberController {

    @Autowired
    private GameNumberRepository gameNumberRepository;

    @RequestMapping(value = "/gameNumber", method = RequestMethod.GET)
    public ResponseEntity<List<GameNumber>> getAllGameNumbers() {
        List<GameNumber> list = new ArrayList<>();
        gameNumberRepository.findAll().forEach(gm -> list.add(gm));
        return ResponseEntity.ok(list);
    }
}
