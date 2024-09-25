/**
 * title: Item
 * date: February 11 2024
 * @author Dominic Evans
 * student-id: 3612857
 * @version 1.0
 * @copyright 2024 Dominic Evans
 */

/**
 * DOCUMENTATION
 */

/**
 * ITEM
 *
 * Purpose and Description:
 *
 * The item class provides a description of an item game object. It provides
 * all the necessary properties that make an item unique from others.
 *
 * Getters are provided for all parameters, are they will be required by other
 * objects that use item, but a created item will not require any of it's
 * parameters to change, thus there are no setter methods.
 *
 * ITEM Methods:
 *
 *   public Item(String name):
 *     Constructor for generation of new item objects. String name specifies which
 *     object is to be generated, with all other data retrieved from the ITEMS
 *     text file via Parse.
 *
 *   public getName():
 *     Getter for the name variable.
 *
 *   public getDescription():
 *     Getter for the description variable.
 *
 *   public getEvents():
 *     Getter for the events variable.
 *
 * ITEM Variables:
 *
 *   public String name
 *     The name of the Item object.
 *
 *   private String description
 *     A description of the Item object.
 *
 *   private String[] events
 *     A list of the names of the events associated with the Item object
 *
 */

import java.util.ArrayList;

public class Item {

    private String name;
    private String description;
    private String[] events;

    public Item(String name) {

        ArrayList<String> data = new ArrayList<String>();

        data = Parse.readData(name, Game.itemsDataPath);

        this.name = name.toUpperCase();
        this.description = data.get(0);
        this.events = data.get(1).trim().split(" ");

    } // end Item constructor

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object o){
       if (this == o) return true;
       if (o == null) return false;
       if (this.getClass() != o.getClass()) return false;
       Item item = (Item) o;
       return this.name.equals(item.name);
    }

    @Override
    public String toString(){
        return this.name.toUpperCase();
    }

    // Getters
    public String getName(){
        return this.name.toUpperCase();
    } // end getName()

    public String getDescription(){
        return this.description;
    } // end getDescription()

    public String[] getEvents(){
        return this.events;
    } // end getEvents()

} // end Item class
