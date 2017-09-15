package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Entity
public class Cheese {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
//    //regex allows spaces between string, disallows spaces only
    @Pattern(regexp="(.|\\s)*\\S(.|\\s)*", message="Name must not be empty")
    private String name;

    @NotNull
    @Pattern(regexp="(.|\\s)*\\S(.|\\s)*", message="Description must not be empty")
    private String description;

//    /*@ManyToOne->specifies there can be many cheeses for any one category*/
//    /*Hibernate will create a column named category_id (based on the field name) and when a Cheese object is stored, this column will contain the id of its category object. The data for the category object itself will go in the table for the Category class
//    * This complimentary pair of annotations -- @ManyToOne and @OneToMany, along with @JoinColumn clarifying how the latter should behave -- set up this relationship to be managed properly on both the application / object-oriented side and the database / relational side.*/
    @ManyToOne
    private Category category;


    //    /*represents the list of Menu objects that a given cheese is contained in. In order to tell Hibernate how to store and populate objects from the list, we specify that the field should be mappedBy the cheeses field of the Menu class.
//    * In other words, the items in this list should correspond to the Menu objects that contain a given Cheese object in their cheeses list. And the inverse relationship is true as well: The items in Menu.cheeses should correspond to the Cheese objects that have a given Menu object in their menus list. Hibernate will notice that our list contains Menu objects, and will look in that class for a property with the same name as that specified by the mappedBy attribute.
//    * We won't be accessing menus outside this class, so there's no need currently to make it anything other than private.*/
    @ManyToMany(mappedBy = "cheeses")
    private List<Menu> menus;

    public Cheese(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Cheese() { }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Menu> getMenus() {
        return menus;
    }
}

