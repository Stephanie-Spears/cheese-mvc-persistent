package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

////ensures the class will be persistent (@Entity ensures the class is mapped to a relational database table)-->(ie. added to the database)
@Entity
public class Category {


    //    /*
//    * private property cheeses of type List<Cheese>, initialized to an empty ArrayList
//    * represents the list of all items in a given category
//    * @OneToMany->Each one category will have many cheeses, but each cheese can have only one category.
//    * @JoinCOlumn(name="category_id") -> tells Hibernate to use the category_id column of the cheese table to determine which cheese belong to a given category
//    * */
    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Cheese> cheeses = new ArrayList<>();


    //specifies the primary key of an entity
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }
}

