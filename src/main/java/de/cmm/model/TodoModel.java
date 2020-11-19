package de.cmm.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class TodoModel extends PanacheEntityBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String title;

    @Column(columnDefinition="TEXT", nullable = false)
    public String subtitle;
    

    @Column(nullable = false)
    public boolean done;

    @Override
    public String toString() {
        return "TodoModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", done=" + done +
                '}';
    }

    public TodoModel() {
    }

    public TodoModel(String title, String subtitle, boolean done) {
        this.title = title;
        this.subtitle = subtitle;
        this.done = done;
    }
}
