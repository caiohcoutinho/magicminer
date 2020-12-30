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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configuration_id_generator")
    @SequenceGenerator(name="configuration_id_generator", sequenceName = "configuration_id_seq", allocationSize = 1)
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
