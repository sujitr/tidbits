package com.sujit;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="App")
public class App {
    
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private long id;
    
    @Column(length = 50, nullable = false, unique = true)
    private String name;
    
    @Column(length = 200, nullable = true)
    private String description;
    
    App() {
        // for hibernate.
    }
    
    public App(String name) {
        this.name = name;
    }
    
    public long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
