/**
 * title: Character
 * date: February 29 2024
 * @author Dominic Evans
 * student-id: 3612857
 * @version 1.0
 * @copyright 2024 Dominic Evans
 */

/**
 * DOCUMENTATION
 */

/**
 * CHARACTER
 *
 * The character class defines all the properites and methods related to a game
 * character. This includes the character's name, inventory, dialogue options,
 * and events related to the character.
 *
 * Getters and setters are provided in order allow for other classes to change
 * aspects of the character as the game progresses.
 *
 * CHARACTER Methods:
 *
 *   public Character(String name)
 *     Character consttructor. Uses the name of the character to look up data in
 *     the CHARACTERS.txt data file and create a new Character object according
 *     to the properties defined there.
 *
 *   public int hashCode()
 *     Generates a hashCode associated with the Character object, derived from
 *     the Character's name property. This overrides default hashCode methods in
 *     order to ensure that two Character objects will be considered equal when
 *     they share the same name property.
 *
 *   public boolean equals(Object o)
 *     Compares the Character object to another object. If the object, and the
 *     Character object share the same class, and have equivalent name
 *     properties, they are considered equivalent objects. This method overrides
 *     the default implementation of equals.
 *
 *   public String[] getDialogue()
 *     dialogue getter. Returns an array of strings representing the various
 *     dialogue options for a Character object.
 *
 *   public String getName()
 *     name getter. Returns the name property of a Character object in upper
 *     case.
 *
 *   public Inventory getInventory()
 *     inventory getter. Returns the characters inventory as an Inventory object
 *
 *   public void addToInventory(Item item)
 *     Adds an Item object to the characters inventory
 *
 *   public String getDescription()
 *     Returns the initial description for a character. This string is intended
 *     to print only on the first viewing of a character.
 *
 *   public String getReturnDescription()
 *     returnDescription getter. This description is intended to be used on
 *     subsequent viewings of a character by the player.
 *
 *   public String[] getEvent()
 *     event getter. The event associated with a given character.
 *
 *   public int getTalkCount()
 *     talkCount getter. Represents the number of times a character has been
 *     spoken to by the player.
 *
 *   public void increaseTalkCount()
 *     talkCount incrementer. This method is called every time a character is
 *     spoken to by the player - keeping track of the number of times that
 *     character has been spoken to overall
 *
 *   public boolean checkEventTriggered()
 *     Returns true if the character's event has been triggered; otherwise,
 *     returns false.
 *
 *   public void setEventTriggered()
 *     Sets eventTriggered to true, upon execution of a character's event
 *
 * CHARACTER Variables:
 *
 *   private String name
 *     The name of the character
 *
 *   priavte Inventory inventory
 *     The Inventory object used to keep track of the items that a character has
 *     in their possession
 *
 *   priavte String description
 *     Holds the description string for a character. Used upon initially looking
 *     at a character.
 *
 *   priavte String returnDescription
 *     Holds the description that is used whenever a character is looked at
 *     outside of the first time.
 *
 *   private String[] dialogue
 *     Holds all of the strings that may be printed when the player speaks to a
 *     character.
 *
 *   private String event
 *     Holds the name of the event associated with the character.
 *
 *   private int talkCount
 *     Keeps track of how many times a character has been spoken to.
 *
 *   private boolean eventTriggered
 *     Boolean value used as a toggle to indicite whether or not the character's
 *     event has been triggered or not.
 */

import java.util.ArrayList;

public class Character {

    private String name;
    private Inventory inventory = new Inventory();
    private String description;
    private String returnDescription;
    private String[] dialogue;
    private String event;
    private int talkCount = 0;
    private boolean eventTriggered = false;

    public Character(String name){

       ArrayList<String> data = new ArrayList<String>();

       data = Parse.readData(name, Game.charactersDataPath);

       this.name = name;
       this.description = data.get(0);
       this.returnDescription = data.get(1);
       this.dialogue = data.get(2).split("/");

       for(String item : data.get(3).split(" ")){

           if(item.equals("NONE"))
               break;

           this.inventory.add(new Item(item));

       }

       this.event = data.get(4).trim().toUpperCase();

    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return this.name.equals(character.name);
    }

    // Getters and Setters
    public String[] getDialogue(){
        return this.dialogue;
    } // end getDialogue()

    public String getName(){
        return this.name.toUpperCase();
    } // end getName()

    public Inventory getInventory(){
        return this.inventory;
    } // end getInventory()

    public void addToInventory(Item item){
        this.inventory.add(item);
    } // end addToInventory()

    public String getDescription(){
        return this.description;
    } // end getDescription

    public String getDeturnDescription(){
        return this.returnDescription;
    } // end returnDescription

    public String getEvent(){
        return this.event;
    } // end getEvent()

    public int getTalkCount(){
        return this.talkCount;
    } // end getTalkCount

    public void increaseTalkCount(){
        this.talkCount++;
    } // end increaseTalkCount

    public boolean checkEventTriggered(){
        return this.eventTriggered;
    } // end checkEventTriggered()

    public void setEventTriggered(){
        this.eventTriggered = true;
    } // end setEventTriggered()

} // end Character class
