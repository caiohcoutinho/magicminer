package com.magicminer;

import com.magicminer.model.entity.Configuration;
import com.magicminer.model.entity.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ConfigurationController {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @RequestMapping(value = "/configuration/{configurationName}", method = RequestMethod.GET)
    public ResponseEntity<Configuration> getConfiguration(@PathVariable("configurationName") String configurationName) {
        return ResponseEntity.ok(configurationRepository.findByName(configurationName));
    }
}
