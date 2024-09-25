/**
 * title: Inventory
 * date: February 20 2024
 * @author Dominic Evans
 * student-id: 3612857
 * @version 1.0
 * @copyright 2024 Dominic Evans
 */

/**
 * DOCUMENTATION
 */

/**
 * INVENTORY
 *
 * Purpose and Description:
 *
 * Provides inventory management for the player, game characters, and locations.
 * Holds Item objects in an ArrayList that may be accessed using particular
 * methods, allowing for items to be added to the list, removed from it, or for
 * the list to be queried for the existence of an item.
 *
 * INVENTORY Methods:
 *
 *   public Inventory()
 *     Inventory constructor. Generates a new empty Inventory object, consisting
 *     of an empty ArrayList that holds items.
 *
 *   public Inventory(String name, String filePath)
 *     Inventory constructor. Generates a new inventory object by selecting a
 *     data field using the name variable, from a specific file, specified using
 *     the filePath variable. Creates items based on the item names it finds
 *     there, and stores them in an ArrayList of items which constitutes the
 *     inventory.
 *
 *   public Inventory(ArrayList<Item> items)
 *     Inventory constructor. Generates a new inventory object that is filled
 *     with all of the same items as the items ArrayList it accepts as it's
 *     parameter.
 *
 *   public ArrayList<Item> getInventory()
 *     inventory getter.
 *
 *   public boolean isEmpty()
 *     Returns true if ArrayList<Item> inventory is empty. Provides the same
 *     functionality as the ArrayList.isEmpty method, but for Inventory objects.
 *
 *   public Item getItem(String itemName)
 *     Returns an Item object from the inventory based on the name of the
 *     desired object.
 *
 *   public void add(Item item)
 *     Adds the passed Item to the inventory.
 *
 *   public void remove(Item item)
 *     Removes the passed Item from the inventory.
 *
 *   public boolean hasItem(Item item)
 *     Returns true if the specified item exists in the inventory.
 *
 *   public boolean hasItem(String itemName)
 *     Returns true if an item exists in the inventory that has the same name as
 *     itemName.
 *
 *   public String printInventory(boolean isPlayer)
 *     Returns a string that describes all of the contents of the inventory in a
 *     context sensitive manner. If the inventory belongs to the player
 *     (isPlayer = true), then the message will be in first person perspective; if
 *     the inventory doesn't belong to a player (isPlayer = false), then the message
 *     is in the third person perspective.
 *
 * INVENTORY Variables:
 *
 *   private ArrayList<Item> inventory
 *     ArrayList that holds all of the Item objects comprising the inventory.
 */

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> inventory;

    public Inventory (){

        this.inventory = new ArrayList<Item>();

    }

    public Inventory (String name, String filePath){

        ArrayList<String> data = Parse.readData(name, filePath);

        // Generate all items in the given inventory.
        // All inventory entries must be the fourth entry in a data file
        for (String item : data.get(3).split(" "))
            this.inventory.add(new Item(item));

    } // end Inventory constructor

    // Inventory constructor for retrieving an inventory from a list of items
    // held in a room object
    public Inventory (ArrayList<Item> items){

        this.inventory = items;

    } // end constructor

    /**
     * getInventory(): inventory getter
     * @param none
     * @return ArrayList<Item> inventory
     * @throws none
     */
    public ArrayList<Item> getInventory(){
        return this.inventory;
    } // end getInventory()

    /**
     * isEmpty(): returns true if the inventory is empty, false if it contains
     *            at least one item
     * @param none
     * @return boolean true if iventory contains no items; false if it contains
     *         at least one
     * @throws none
     */
    public boolean isEmpty(){

        return this.inventory.isEmpty();

    } // end isEmpty()

    /**
     * getItem(): returns an item object from the inventory when supplied with
     *            it's name
     * @param String itemName: the name of the desired item
     * @returns Item object found in the inventory; null otherwise
     * @throws none
     */
    public Item getItem(String itemName){

        for(Item item : this.inventory){

            if(item.getName().equals(itemName))
                return item;

        }

        return null;

    } // end getItem()

    /**
     * add(): Adds an item to the inventory
     * @param Item item: the item to be added to inventory
     * @return none
     * @throws none
     */
    public void add(Item item){

        this.inventory.add(item);

    } // end add()

    /**
     * remove(): Removes an item from the inventory
     * @param Item item: Item object to be removed
     * @return none
     * @throws none
     */
    public void remove(Item item){

        int index = inventory.indexOf(item);

        inventory.remove(index);

    } // end remove()

    /**
     * hasItem(): checks an inventory for the existence of an item
     * @param Item item: the desired item
     * @return true if item is present; false otherwise
     * @throws none
     */
    public boolean hasItem(Item item){

        if(this.inventory.contains(item))
            return true;

        return false;

    } // end hasItem()

    public boolean hasItem(String itemName){

        for(Item item : this.inventory){

            if(item.getName().equals(itemName))
                return true;

        }

        return false;

    } // end hasItem()

    /**
     * printInventory(): prints a message containing the contents of the
     *                   inventory; if the inventory is empty, it prints a
     *                   message explaining the inventory is empty.
     * @param boolean isPlayer: denotes whether or not the inventory is the
     *                          player inventory; changing the format of the printout
     * @return String list of everything the inventory holds
     * @throws none
     */
    public String printInventory(boolean isPlayer){

        String result = "";

        if(isPlayer){

            result = "You are carrying: \n";

            if(this.inventory.isEmpty())
                return "You are not carrying anything.";

            for(Item item : this.inventory){

                result += item.getName() + " ";
            }

        } else {

            if(this.inventory.isEmpty())
                return "There are not any items here";

            if(this.inventory.size() == 1)
                return "You see " + this.inventory.get(0) + " here";

            result = "You see several items here:";
            for(Item item : this.inventory){

                result += " " + item.getName();

            }

        }

        return result;
    } // end printInventory()

} // end Inventory class
