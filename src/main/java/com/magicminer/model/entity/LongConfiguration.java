package com.magicminer.model.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("LongConfiguration")
public class LongConfiguration extends Configuration{

    private Long value;

    private LongConfiguration(){

    }

    public LongConfiguration(String name, Long value){
        super(name);
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
