package com.magicminer.model.entity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discriminator")
public class Configuration {
    private Long configurationId;
    private String name;
    private String discriminator;

    protected Configuration(){

    }

    public Configuration(String name) {
        this.name = name;
    }

    @Id
    public Long getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(Long configurationId) {
        this.configurationId = configurationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
