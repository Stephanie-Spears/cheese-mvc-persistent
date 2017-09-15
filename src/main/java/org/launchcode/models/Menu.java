package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Menu {

    @NotNull
    @Size(min = 3, max = 15)
    private String name;

    @Id
    @GeneratedValue
    private int id;


    //    /*This field will be used to hold all items in the menu, and Hibernate will populate it for us based on the relationships we set up in our controllers.
//    * Should have a getter but not a setter method
//    * @ManyToMany->This will set up 1/2 of our many-to-many relationship.*/
//    @ManyToMany
    @ManyToMany
    private List<Cheese> cheeses;

    public void addItem(Cheese item) {
        cheeses.add(item);
    }

    public void removeItem(Cheese item) { cheeses.remove(item);}

    public Menu() {
    }

    public Menu(String name) {
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

    public void setId(int id) {
        this.id = id;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }

    public boolean isDuplicate(Cheese cheese, Menu menu) {
        List<Cheese> cheeseMenu = menu.getCheeses();
        boolean isOnMenu = false;
        for (Cheese item : cheeseMenu) {
            if (item.equals(cheese)) {
                isOnMenu = true;
                break;
            }
        }
        return isOnMenu;
    }
}
