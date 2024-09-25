/**
 * title: Location
 * date: February 10 2024
 * @author Dominic Evans
 * student-id: 3612857
 * @version 1.0
 * @copyright 2024 Dominic Evans
 */

/**
 * DOCUMENTATION
 */

/**
 * LOCATION
 *
 * Purpose and Description:
 *
 * Locations class defines all the properties of a game 'room' that contains all
 * of the game objects, descriptions and interactions that make up the game. It
 * facilitates creating new Location objects that contain their specific items,
 * characters, and exits as well as implements methods for accessing these
 * properties.
 *
 * LOCATION Methods:
 *
 *   public Location(String name)
 *     Location constructor. Accepts as input the name of the location being
 *     created. Using this name, the location object is generated according to
 *     data held in the LOCATIONS.txt file.
 *
 *   public int hashCode()
 *     Generates a hash code associated with the location object based on the
 *     name of the location. This is necessary for overriding the equals method.
 *
 *   public boolean equals(Object o)
 *     Compares the location object to another object o. If the names and
 *     classes are equivalent, this method returns true. In this way, two
 *     Location objects with the same name are considered the same object for
 *     all game logic purposes.
 *
 *   public String getName()
 *     name getter
 *
 *   public String getDescription()
 *     description getter
 *
 *   public String getReturnDescription()
 *     returnDescription getter
 *
 *   public Inventory getItems()
 *     roomItems getter
 *
 *   public ArrayList<Character> getCharacters()
 *     characters getter
 *
 *   public HashMap<Direction, Location> getExits()
 *     exits getter
 *
 *   public String[] getEvents()
 *     events getter
 *
 *   public boolean isVisited()
 *     Returns true when the location object has been previously visited by the
 *     player.
 *
 *   public void setVisited()
 *     Changes the visited status from false to true; used the first time the
 *     player visits a location.
 *
 *   public String[] getExitNames()
 *     exitNames getter
 *
 *   public String[] getExitStatus()
 *     exitStatus getter
 *
 *   public void setExitStatus(int exitIndex, String newStatus)
 *     exitStatus setter. Changes the exit status found at exitIndex, to the new
 *     status, as specified by newStatus
 *
 *   public static ArrayList<Location> getMappedLocations()
 *     mappedLocations getter
 *
 *   public static void generateMap(Location location)
 *     Generates a game map by crawling the game data. Beginning with the START
 *     location, the method recursively creates new Location objects for each
 *     location listed as a connection in a given location. If a connected
 *     location has already been mapped, the method returns, halting the
 *     recursion. Once the method has completed it's crawl, the entire game map
 *     will be loaded into memory, with references to Location objects held in
 *     the mappedLocations ArrayList
 *
 *   public static void mapExits(ArrayList<Location> locations)
 *     Maps all the cardinal directions of a given location to their
 *     corresponding Location objects. This facilitates moving between rooms
 *     during gameplay. A cardinal direction is defined as any of the six
 *     degrees of movement available in the game (NORTH, SOUTH, EAST, WEST, UP, DOWN)
 *
 *   private void mapDirection(int ordinalDirection, Location location)
 *     Maps a specific ordinal direction to a given Location object. This is
 *     accomplished by adding the Location to the exits HashMap. The integer
 *     value cardinalDirection indicates to which cardinal direction the
 *     Location should be mapped. 0 = NORTH; 1 = EAST; 2 = SOUTH; 3 = WEST; 4 =
 *     UP; 5 = DOWN
 *
 *   private mapDirection(int ordinalDirection, String nonExit)
 *     Overloaded version of mapDirection called specifically for the case when
 *     there is no Location associated with the given ordinal direction. nonExit
 *     must equal NONE for a legal method call; ordinalDirection functions
 *     exactly as in the base version of this method.
 *
 * LOCATION Variables:
 *
 *   private static ArrayList<Location> mappedLocations
 *     Holds references to all of the game locations that have been loaded.
 *     Functions as a global game map from which location objects can be pulled
 *     for use in other areas of the game.
 *
 *   private String name
 *     The name of the location. Serves as the object identifier; when two
 *     Location objects have the same name, they are equivalent objects.
 *
 *   private String description
 *     The description of the Location given when the player initially visits
 *     the location.
 *
 *   private String returnDescription
 *     The description of the Location given when the player returns to the
 *     location.
 *
 *   private ArrayList<Character> characters
 *     List of all the characters present in the location
 *
 *   private Inventory roomItems
 *     Inventory object holding all of the Items present in the location
 *
 *   private String[] events
 *     Array of all the names of available events in the location. These strings
 *     are passed to the Events class to trigger specific events.
 *
 *   private String[] exitNames
 *     Array of the names of the locations that are connected to the exits of
 *     the location. These strings are passed to methods to permit moving from
 *     location to location
 *
 *   private HashMap<Direction, Location> exits
 *     Map of Directions to Locations, representing the exits available in the
 *     location. This map provides the 'real' exits that methods use to move the
 *     player from one location to another.
 *
 *   private String[] exitStatus
 *     String array indicating the exit status for a given exit. The exit is
 *     specified by it's location in the array, with it's elemnt corresponding
 *     to a cardinal direction - 0 = NORTH; 1 = EAST; 2 = SOUTH; 3 = WEST; 4 =
 *     UP; 5 = DOWN
 *
 *   private boolean visited
 *     Indicates whether or not the player has been here before. False until the
 *     description has been printed, and then true after that. Prompts certain
 *     methods to behave differently to provide different behaviour when
 *     returning to the location
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Location {

    private static ArrayList<Location> mappedLocations = new ArrayList<Location>();

    private String name;
    private String description;
    private String returnDescription;
    private ArrayList<Character> characters;
    private Inventory roomItems;
    private String[] events;
    private String[] exitNames;
    private HashMap<Direction, Location> exits = new HashMap<Direction, Location>();
    private String[] exitStatus;
    private boolean visited;

    public Location(String name){

       ArrayList<String> data = new ArrayList<String>();

       data = Parse.readData(name, Game.locationsDataPath);

       this.name = name;
       this.description = data.get(0);
       this.returnDescription = data.get(1);

       // Initialize and create character objects
       this.characters = new ArrayList<Character>();

       for(String character : data.get(2).trim().split(" ")){

           // Remove any unintentional whitespace
           character.trim();

           if(character.equals("NONE"))
               break;

           this.characters.add(new Character(character));

       }

       // Initialize and create room inventory
       this.roomItems = new Inventory();

       for(String item : data.get(3).trim().split(" ")) {

           if(item.equals("NONE"))
               break;

           this.roomItems.add(new Item(item));

       }

       this.events = data.get(4).trim().split(" ");

       // Keep a record of exits to be used when exits are actually mapped to
       // directions after location mapping is complete
       this.exitNames = data.get(5).trim().split(" ");

       this.exitStatus = data.get(6).trim().split(" ");

    } // end Location()

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Location loc = (Location) o;
        return this.name.equals(loc.name);
    }

    /**
     * getName(): Location name getter.
     * @param none
     * @return String name: name of the Location
     * @throws none
     */
    public String getName(){
        return this.name;
    } // end getName()

    /**
     * getDescription(): Location description getter. Used for first entry into
     *                   location.
     * @param none
     * @return String description: initial description of the location.
     * @throws none
     */
    public String getDescription(){
        return this.description;
    } // end getDescription()

    /**
     * getReturnDescription(): Location description getter. Used for each entry
     *                         into a location after the first one.
     * @param none
     * @return String returnDescription: description used when returning to a
     *                                   location.
     * @throws none
     */
    public String getReturnDescription(){
        return this.returnDescription;
    } // end getReturnDescription()

    /**
     * getItems(): Location inventory getter.
     * @param none
     * @return Inventory roomItems: Inventory object containing the location's
     *                              inventory
     * @throws none */
    public Inventory getItems(){
        return this.roomItems;
    } // end getItems()

    /**
     * getCharacters(): provides the list of characters present in a room
     * @param none
     * @returns ArrayList<Character> characters: array list of characters
     *                                           present in a location
     * @throws none
     */
    public ArrayList<Character> getCharacters(){
        return this.characters;
    } // end

    /**
     * getExits(): returns map of locations available from the current location
     *             mapped to the cardinal directions.
     * @param none
     * @returns HashMap<String, Location> exits: Map of locations to cardinal
     *                                           directions.
     * @throws none
     */
    public HashMap<Direction, Location> getExits(){
        return this.exits;
    } // end getExits()

    /**
     * getEvents(): Location event getter.
     * @param none
     * @returns String[] events: list of events contained in the location
     * @throws none
     */
    public String[] getEvents(){
        return this.events;
    } // end getEvents()

    /**
     * isVisited() reports the current status of the Location as either visited
     * or  unvisited.
     * @param none
     * @return true if previously visited, false otherwise
     */
    public boolean isVisited(){

        if(visited)
            return true;

        return false;

    } // end isVisited()

    /**
     * setVisited(): changes the room's status from unvisited to visited.
     * @param none
     */
    public void setVisited(){

        this.visited = true;

    } // end setVisited()

    /**
     * getExitNames(): exitNames getter
     * @param none
     * @return String array containing location's exit names
     * @throws none
     */
    public String[] getExitNames(){
        return this.exitNames;
    } // end getExitNames()

    /**
     * getExitStatus(): exitStatus getter
     * @param none
     * @return String[] exitStatus
     * @throws none */
    public String[] getExitStatus(){
       return this.exitStatus;
    } // end getExitStatus()

    /**
     * setExitStatus(): changes exit status for the location
     * @param int exitIndex: index of the exit to be changed; 0 = NORTH,
     *                       1 = EAST, 2 = SOUTH, 3 = WEST, 4 = UP,
     *                       5 = DOWN
     * @param String newStatus: The status the indicated exit is to be changed
     *                          to
     * @return none
     * @throws IllegalStateException if the index being changed is ever larger
     *         than 5. Exit Status arrays are always length 6.
     */
    public void setExitStatus(int exitIndex, String newStatus){

        if(exitIndex > 5)
            throw new IllegalStateException("Exit Status Index Out of Bounds.");

        this.exitStatus[exitIndex] = newStatus;

    } // end setExitStatus()

    /**
     * getMappedLocations(): returns the ArrayList of all mapped (instantiated)
     *                       Location objects.
     * @param none
     * @return ArrayList<Location>: ArrayList containing all instantiated game
     *                              Locations.
     * @throws none
     */
    public static ArrayList<Location> getMappedLocations(){

        return mappedLocations;

    } // end getMappedLocations

    /**
     * generateMap(): generates the game map, storing locations in the
     *                mappedLocations static variable
     * @param Location location: a location to be mapped; initially used as a
     *                           seed location which then recursively maps all
     *                           other connected locations
     * @return none
     * @throws none
     */
    public static void generateMap(Location location){

        ArrayList<String> locationData = Parse.readData(location.getName(), Game.locationsDataPath);
        String[] connectionsData = locationData.get(5).trim().split(" ");

        if(mappedLocations.contains(location)){

            return;

        } else {

            mappedLocations.add(location);

            for (String locName : connectionsData){

                if(locName.equals("NONE")){

                   continue;

                }

               Location connectingLoc = new Location(locName);

               if(mappedLocations.contains(connectingLoc)){

                   continue;

               }

               generateMap(connectingLoc);

            }

            return;

        }


    } // end generateMap

    /**
     * mapExits(): maps all cardinal directions to corresponding locations, per
     *             location data
     * @param ArrayList<Location> locations: ArrayList of all locations needing
     *                                       their exits mapped; to be fed the
     *                                       game map ArrayList, containing all
     *                                       loaded game locations.
     * @return none
     * @throws IllegalStateException: If the data file specifies a location that
     *                                was not loaded into the game map as an exit.
     */
    public static void mapExits(ArrayList<Location> locations){

        for(Location loc : locations){

            String[] exits = loc.getExitNames();

            for(int i = 0; i < exits.length; i++){

                if(exits[i].equals("NONE")) {

                    loc.mapDirection(i, "NONE");
                    continue;

                }

                Location  placeholderLoc = new Location(exits[i]);

                if(locations.contains(placeholderLoc)){

                    int locIndex = locations.indexOf(placeholderLoc);
                    loc.mapDirection(i, locations.get(locIndex));

                } else {

                    throw new IllegalStateException("MAPEXITS: No such location");

                }

            }

        }

    } // end mapExits()

    /**
     * mapDirection(): maps a location to a cardinal direction of the current
     *                 location; defining legal directions of travel for the
     *                 location.
     * @param int ordinalDirection: integer corresponding to the ordinal
     *                              directions of the location; 0 - NORTH,
     *                              1 - EAST, 2 - SOUTH, 3 - WEST, 4 - UP,
     *                              5 - DOWN
     * @param Location location: the location the ordinalDirection will be
     *                           mapped to
     * @return none
     * @throws IllegalArgumentException: when ordinalDirection does not
     *                                   correspond to one of it's legal values
     */
    private void mapDirection(int ordinalDirection, Location location){

        // Handle the null case, when there is nothing in a given ordinal
        // direction.

         switch(ordinalDirection){
            case 0:
               this.exits.put(Direction.NORTH, location);
               return;
            case 1:
                this.exits.put(Direction.EAST, location);
                return;
            case 2:
                this.exits.put(Direction.SOUTH, location);
                return;
            case 3:
                this.exits.put(Direction.WEST, location);
                return;
            case 4:
                this.exits.put(Direction.UP, location);
                return;
            case 5:
                this.exits.put(Direction.DOWN, location);
                return;
            default:
                throw new IllegalArgumentException("Not a direction.");
         }


    } // end mapDirection()

    /**
     * mapDirection(): Overloaded version of default mapDirection to account
     *                 for directions that have no location associated with them.
     * @param int ordinalDirection: indicates the ordinal direction to be
     *                              mapped
     * @param String nonExit: Must equal "NONE" as this functions as a flag to
     *                        map a direction to null, rather than a location.
     * @throws IllegalArgumentException: if nonExit does not equal "NONE" or a
     *                                   direction other than a legal cardinal
     *                                   is passed to the function
     */
    private void mapDirection(int ordinalDirection, String nonExit){

        if(!nonExit.equals("NONE"))
            throw new IllegalArgumentException("Invalid non-direction");

        switch(ordinalDirection){

            case 0:
                this.exits.put(Direction.NORTH, null);
                return;
            case 1:
                this.exits.put(Direction.EAST, null);
                return;
            case 2:
                this.exits.put(Direction.SOUTH, null);
                return;
            case 3:
                this.exits.put(Direction.WEST, null);
                return;
            case 4:
                this.exits.put(Direction.UP, null);
                return;
            case 5:
                this.exits.put(Direction.DOWN, null);
                return;
            default:
                throw new IllegalArgumentException("Non cardinal non-direction");
        }
    }


} // end Locations class
