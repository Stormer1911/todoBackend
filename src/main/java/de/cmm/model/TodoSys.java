package de.cmm.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TodoSys extends PanacheEntityBase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String value;
    public Boolean initialized;

    public TodoSys() {
    }

    public TodoSys(String value, Boolean initialized) {
        this.value = value;
        this.initialized = initialized;
    }

    public static TodoSys findByName(String value) {
        return find("value", value).firstResult();
    }
}
