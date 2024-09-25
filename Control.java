/**
 * title: Control
 * date: February 5 2024
 * @author Dominic Evans
 * student-id: 3612857
 * @version 1.0
 * @copyright 2024 Dominic Evans
 */

/**
 * DOCUMENTAITON
 */

/**
 * CONTROL
 *
 * Purpose and Description:
 *
 * The Control class handles player input, and implements any methods that are
 * required for performing various game actions. Control functions as a
 * 'traffic controller' coordinating the other classes into a logical game
 * framework.
 *
 * CONTROL Methods:
 *
 *   public static void init()
 *     Provides game initialization by giving values to key game variables that
 *     are required for play to begin. Sets the starting location, and generates
 *     the game map that the player will traverse.
 *
 *   public static void dispatcher(String[] verbObj)
 *     Functions as a traffic controller for the game. Recieves as input an
 *     array of strings of length 3 which contains as elements a verb, a game
 *     object, and optionally a third element which is used as a memory so the
 *     game can respond more eloquently to non-game-object commands. When this
 *     method receives a command, it directs it to the appropriate game logic by
 *     filtering it according to the verb entered
 *
 *   public static void update()
 *     Updates game-state variables when called. This method ensures that all
 *     game-state variables are pointing to the correct current values. It
 *     updates the current available exits, the items in the current room, and
 *     the characters present.
 *
 *   public static void look(Item item? Character character?)
 *     This method prints out a description, according to context. It has
 *     several overloaded versions which accept Items, and Character objects
 *     respectively. When called with no parameters, this method prints out the
 *     general description of the current surroundings. When given an Item or
 *     Character object, it prints the description of that Item or Character.
 *
 *   public static void go(Direction direction)
 *     Moves the player from the current location in the direction indicated.
 *     Direction is an enum that indicates legal directions of travel. Possible
 *     directions are NORTH, SOUTH, EAST, WEST, UP, and DOWN. If there is no
 *     exit in the direction indicated, go prints the appropriate message
 *     indicating this.
 *
 *   public static boolean checkExitStatus(Direciton direction)
 *     Compares the exit status of the exit that lies in the given direction,
 *     and ensures that travel in that direction is permitted. If the player is
 *     too large, or too small for the exit, or if the exit is locked, it
 *     returns false. Otherwise, if the exit is open, and the exit's size
 *     matches the player's then it returns true. Used exclusively by go() to
 *     determine if movement is legal. A special status, LOST, corresponds to
 *     the forrest maze, and activates the forrest maze Event if encountered.
 *
 *   public static void take(Item item)
 *     Removes an item from the current room's inventory and places it into the
 *     player's inventory. Allows for game items to be picked up by the player.
 *
 *   public static void drop(Item item)
 *     The inverse of take. Removes an item from the players inventory and adds
 *     it to the current room's inventory.
 *
 *   public static void touch(String touched)
 *     Functions as a catch-all interaction method. By aliasing different user
 *     inputs to touch, touch allows a wide variety of verbs to cause item,
 *     character, or environment interaction. Touch accepts a String argument
 *     indicating the name of the thing touched, it then filters this input to
 *     the appropriate present touchable thing and calls the relevant event(s).
 *     If touched is not a valid item, it prints an appropriate error message.
 *
 *   public static void talk(Character character)
 *     Method of calling dialogue from Character objects. The character
 *     parameter indicates to whom the player is talking, and calls the
 *     appropriate line of dialogue. Each character has one or more lines of
 *     dialogue which are called sequentially. When the Character is out of
 *     dialogue lines, it repeats the last line.
 *
 *   private static void inventory()
 *     Calls the printInventory method on the player inventory. Allows for the
 *     player to check what items they are currently in possession of.
 *
 *   private static String classifyObject(String objName)
 *     Takes a string parameter of an object name and uses it to classify what
 *     type of object it is. By searching through all available game objects in
 *     the current game context, it returns a string which serves as a
 *     classification of the object. E.g. when passed "RABBIT" it would return
 *     "CHARACTER" indicating that the string refers to a character. In this
 *     way, strings can be mapped to game objects for further processing.
 *
 *   public static void help(String topic)
 *     Prints the help dialogue. If no topic is provided, it prints the general
 *     help dialogue giving basic instructions for playing the game. Optionally,
 *     a topic may be passed to the method, prompting it to provide
 *     topic-specific help dialogues.
 *
 *   public static void setTestWinCond()
 *     Used for testing. Sets testWinCond to true, in order to alter the win
 *     conditions for the test data set.
 *
 *   public static void checWinState()
 *     Checks whether or not the game has been won, based on the winning
 *     conditions defined therein. For this game, the win conditions is that all
 *     of the suit keys (key of clubs, hearts, diamonds, and spades) are all in
 *     the rabbithole room inventory. When this condition is met, the game is
 *     won, and the method calls the Events.win(), which ends the game in the
 *     win state. Win state may be altered through toggling the testWinCond to
 *     true, for use in testing base game engine.
 *
 * CONTROL Variables:
 *
 *    private static Location currentRoom
 *      Tracks the room the player is currently in by refercing the relvant
 *      Location object.
 *
 *    private static Inventory playerInventory
 *      Holds the player inventory. This provides player inventory management
 *      for the duration of the game. Any item the player holds is held in this
 *      Inventory object.
 *
 *    private static Inventory currentRoomInventory
 *      Derived from the currentRoom Location object, this Inventory represents
 *      the items within the current game room.
 *
 *    private static HashMap<Direction, Location> exits
 *      HashMap containing a mapping of Direction enums to Locations. This
 *      serves as a way of refercing what exits are available for the player
 *      from the current room.
 *
 *    private static String[] exitStatus
 *      Array of strings that denote the exit status of the various exits of a
 *      given Location. The index of this array corresponds with the indicies of
 *      the ArrayList describing the exits. Thus, exitStatus[0] indicates the
 *      exit status of the first exit in exits (i.e. NORTH).
 *
 *    private static ArrayList<Characters> characters
 *      ArrayList containing all the Character objects that represent the game
 *      characters present in the current room. Derived from the currentRoom
 *      Location object.
 *
 *    private static String[] currentCommand
 *      Stores all of the current command words as reported by the parser. Used
 *      for passing command words to the Events class for context-specific event
 *      responses.
 *
 *    private static int playerSize
 *      Denotes the current size of the player, with 0 = SMALL, 1 = NORMAL, 2 =
 *      HUGE. Player begins play at NORMAL size.
 *
 *    private static int thiefChance
 *      Assigned a random integer between 0 and 9, every time the go() method is
 *      called. If the value of thiefChance is 0, then the thief appears.
 *
 *    privte static Random rand
 *      Random number generator.
 *
 *    private static boolean testWinCond
 *      Boolean value that is toggled to 'true' when the game is using test
 *      data, in order to alter the win condition for the new data set.
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Control {

    private static Location currentRoom;
    private static Inventory playerInventory = new Inventory();
    private static Inventory currentRoomInventory;
    private static HashMap<Direction, Location> exits;
    private static String[] exitStatus;
    private static ArrayList<Character> characters;
    private static String[] currentCommand = new String[3];
    private static int playerSize = 1;
    private static int thiefChance;
    private static Random rand = new Random();
    private static boolean testWinCond = false;

    /**
     * getCurrentRoom(): currentRoom getter
     * @param none
     * @return Location currentRoom
     * @throws none
     */
    public static Location getCurrentRoom(){
        return currentRoom;
    } // end getCurrentRoom()

    /**
     * getPlayerInventory(): playerInventory getter
     * @param none
     * @return Inventory playerInventory
     * @throws none
     */
    public static Inventory getPlayerInventory(){
        return playerInventory;
    } // end getPlayerInventory()

    /**
     * setPlayerInventory(): playerInventory setter
     * @param Inventory newInventory: set the playerInventory to the new one
     * @return none
     * @throws none
     */
    public static void setPlayerInventory(Inventory newInventory){
        playerInventory = newInventory;
    } // end setPlayerInventory

    /**
     * getCurrentRoomInventory(): currentRoomInventory getter
     * @param none
     * @return Inventory currentRoomInventory
     * @throws none
     */
    public static Inventory getCurrentRoomInventory(){
        return currentRoomInventory;
    } // end getCurrentRoomInventory()

    /**
     * setCurrentRoomInventory(): currentRoomInventory setter
     * @param Inventory invent: the new inventory for the room
     * @returns none
     * @throws none
     */
    public static void setCurrentRoomInventory(Inventory invent){
        currentRoomInventory = invent;
    }

    /**
     * getCharacters(): characters getter
     * @param none
     * @return ArrayList<Character> characters
     * @throws none
     */
    public static ArrayList<Character> getCharacters(){
       return characters;
    } // end getCharacters

    /**
     * removeCharacter(): removes a character from the current room
     * @param Character character: character to be removed
     * @return none
     * @throws none
     */
    public static void removeCharacter(Character character){
        characters.remove(character);
    }

    /**
     * getCurrentCommand(): currentCommand getter
     * @param none
     * @return String[3] currentCommand
     * @throws none
     */
    public static String[] getCurrentCommand(){
       return currentCommand;
    } // end getCurrentCommand()

    /**
     * getPlayerSize(): playerSize getter
     * @param none
     * @return int playerSize
     * @throws none
     */
    public static int getPlayerSize(){
       return playerSize;
    } // end getPlayerSize

    /**
     * setPlayerSize(): playerSize setter
     * @param int size: the new size for the player; value between 0 and 2
     *                  inclusive.
     * @return none
     * @throws none */
    public static void setPlayerSize(int size){
        playerSize = size;
    } // end setPlayerSize()

    /**
     * init(): Initializes the game state when the game is first started.
     *         Accomplishes any bootstrapping necessary, and sets all global
     *         variables to their initial values.
     * @param none
     * @return none
     * @throws none
     */
    public static void init(){

        Parse.populateVerbSynonyms(Game.commandsDataPath);
        Parse.populateObjects(Game.objectsDataPath);
        Parse.populatePrepositions(Game.prepositionsDataPath);

        currentRoom = new Location("START");

        Location.generateMap(currentRoom);

        Location.mapExits(Location.getMappedLocations());

        update();

    } // end init()

    /**
     * dispatcher(): recieves as input a verb-object pair, and calls the
     *               appropriate methods to execute the action intended by
     *               the player.
     * @param String[] verbObj: a three-element array consisting of a verb at index
     *                          0 , and an object to be acted upon at index 1,
     *                          if the object of the command is not known to the
     *                          game, it is stored in element 2; this allows the
     *                          game to reference the entered object, even if it
     *                          does not have a meaning in game
     * @return none
     * @throws IllegalArgumentException: if verbObj does not conform to the
     *                                   constraints listed above
     */
    public static void dispatcher(String[] verbObj){

        String verb;
        String object;
        String objectMemory = verbObj[2];

        if(verbObj.length != 3)
           throw new IllegalArgumentException("Invalid number of arguments");

        // Handle potential null values for verbs or objects
        if(verbObj[0] == null)
            throw new IllegalArgumentException("CONTROL DISPATCH: Invalid action");
        else
            verb = verbObj[0];

        // null objects okay, indicates actions that may have no object such as LOOK
        // Must handle the null case
        if(verbObj[1] == null)
            object = "NONE";
        else
            object = verbObj[1];

        currentCommand = verbObj;

        switch(verb){

            case "QUIT":

                Events.quit();
                break;

            case "GO":

                try {

                    go(Direction.valueOf(object));

                } catch (IllegalArgumentException e) {

                    System.out.println("There is nowhere to go that way");
                }

                break;

            case "TAKE":

                if(classifyObject(object).equals("ITEM") && currentRoomInventory.hasItem(object)) {

                    Item theItem = currentRoomInventory.getItem(object);
                    take(theItem);

                } else {

                    System.out.println("There is nothing like that here.");

                }

                break;

            case "DROP":

                if(classifyObject(object).equals("ITEM") && playerInventory.getItem(object) != null) {

                    Item theItem = playerInventory.getItem(object);
                    drop(theItem);

                }
                break;

            case "LOOK":

                if(playerInventory.getItem(object) != null){

                    look(playerInventory.getItem(object));
                    break;

                }

                if(currentRoomInventory.getItem(object) != null){

                    look(currentRoomInventory.getItem(object));
                    break;

                }

                if(classifyObject(object).equals("CHARACTER")){

                    look(new Character(object));
                    break;

                }

                // Handle the case of user entering a valid game item that is
                // not present in the current room
                if(playerInventory.getItem(object) == null
                   && currentRoomInventory.getItem(object) == null
                   && !object.equals("NONE")) {

                    System.out.println("There's nothing like that to look at here.");
                    break;

                }

                // Base case, when the player is looking at their general
                // surroundings
                look();
                break;

            case "TOUCH":

                if(object.equals("NONE") && objectMemory == null){

                    System.out.println("There's nothing like that to touch here");
                    break;

                } else if(object.equals("NONE") && objectMemory != null){

                    System.out.println("There is no " + objectMemory + " here to touch");
                    break;

                } else {

                    touch(object);
                    break;
                }

            case "TALK":

                boolean nothingToTalkTo = true;

                for(Character character : characters){

                    if(character.getName().equals(object)) {

                        talk(character);
                        nothingToTalkTo = false;
                        break;

                    } else if (currentRoomInventory.getItem(object) != null || playerInventory.getItem(object) != null){

                        System.out.println("You talk to " + object + ". It doesn't seem to be listening...");
                        nothingToTalkTo = false;
                        break;

                    }

                }

                if(nothingToTalkTo)
                    System.out.println("You talk to yourself...");

                break;

            case "ATTACK":

                Events.execute("ATTACK");
                break;

            case "INVENTORY":

                inventory();
                break;

            case "HELP":

                help(objectMemory);
                break;


            default:
                System.out.println("EXECUTE DEFAULT");
                throw new IllegalArgumentException("Invalid action.");
        }


    } // end dispatcher()

    /**
     * update(): updates all game-state variables.
     * @param none
     * @return none
     * @throws none
     */
    public static void update(){

        exits = currentRoom.getExits();
        exitStatus = currentRoom.getExitStatus();
        currentRoomInventory = currentRoom.getItems();
        characters = currentRoom.getCharacters();

    } // end update()

    /**
     * look(): returns the room description, as appropriate for the current
     *         room's status as either visited or not-visited.
     * @param none
     * @return none
     * @throws none
     */
    public static void look(){

        if(currentRoom.isVisited()){

            Parse.printAndFormat(currentRoom.getReturnDescription().trim());
            currentRoomInventory.printInventory(false);

        } else {

            Parse.printAndFormat(currentRoom.getDescription().trim());
            currentRoom.setVisited();

        }

    } // end look()

    /**
     * look(): overloaded version of look for use when looking at a game item
     * @param Item item: the item being investigated
     * @return none
     * @throws none
     */
    public static void look(Item item){

        if(currentRoomInventory.hasItem(item) || playerInventory.hasItem(item))
            System.out.println(item.getDescription());
        else
            System.out.println("You look around and can't find " + item.getName() + "anywhere...");

    } // end look()

    /**
     * look(): overloaded version of look for use when looking at a character
     * @param Character character: the character being investigated
     * @return none
     * @throws none
     */
    public static void look(Character character){

        if(characters.contains(character))
            System.out.println(character.getDescription());
        else
            System.out.println(character.getName() + " is not here");

    } // end look()

    /**
     * go(): change the current room to a new room and invoke the look method.
     * @param Direction direction: the direction that the player wishes to go,
     *                             given as a direction enum.
     * @return none
     * @throws IllegalArgumentException: when a direction is not mapped to a
     *                                   location
     */
    public static void go(Direction direction){


       if(exits.get(direction) != null && checkExitStatus(direction))
           currentRoom = exits.get(direction);

       if(exits.get(direction) == null)
           throw new IllegalArgumentException("There is nowhere to go that way.");

       update();

       look();

       thiefChance = rand.nextInt(10);

       if(thiefChance == 0) {

           characters.add(new Character("thief"));
           Events.execute("STEAL");

       }

       if(!currentRoom.isVisited())
           currentRoom.setVisited();

    } // end go()

   /**
    * checkExitStatus(): checks whether or not an exit is passable. An exit may
    *                    have a status of OPEN, LOCKED, BIG, or SMALL.
    * @param Direction direction: direction of travel
    * @return true if passable; false if impassable
    * @throws IllegalStateException if status restrictions are violated.
    */
    private static boolean checkExitStatus(Direction direction){

        switch(direction){

            case NORTH:

                if(exitStatus[0].equals("LOCKED")) {
                    Parse.printAndFormat("The door is locked.");
                    return false;
                }

                if(exitStatus[0].equals("BIG") && playerSize != 2) {
                    Parse.printAndFormat("This door is HUGE. You are too small " +
                                         "to reach the handle.");
                    return false;
                }

                if(exitStatus[0].equals("SMALL") && playerSize != 0){
                    Parse.printAndFormat("This door is TINY. You are too big " +
                                         "to fit through.");
                    return false;
                }

                if(exitStatus[0].equals("LOST")) {
                    Events.execute("FOREST-MAZE");
                }

                break;

            case EAST:

                if(exitStatus[1].equals("LOCKED")) {
                    Parse.printAndFormat("The door is locked.");
                    return false;
                }

                if(exitStatus[1].equals("BIG") && playerSize != 2) {
                    Parse.printAndFormat("This door is HUGE. You are too small " +
                                         "to reach the handle.");
                    return false;
                }

                if(exitStatus[1].equals("SMALL") && playerSize != 0){
                    Parse.printAndFormat("This door is TINY. You are too big " +
                                         "to fit through.");
                    return false;
                }

                if(exitStatus[1].equals("LOST")) {
                    Events.execute("FOREST-MAZE");
                }

                break;

            case SOUTH:

                if(exitStatus[2].equals("LOCKED")) {
                    Parse.printAndFormat("The door is locked.");
                    return false;
                }

                if(exitStatus[2].equals("BIG") && playerSize != 2) {
                    Parse.printAndFormat("This door is HUGE. You are too small " +
                                         "to reach the handle.");
                    return false;
                }

                if(exitStatus[2].equals("SMALL") && playerSize != 0){
                    Parse.printAndFormat("This door is TINY. You are too big " +
                                         "to fit through.");
                    return false;
                }

                if(exitStatus[2].equals("LOST")) {
                    Events.execute("FOREST-MAZE");
                }

                break;

            case WEST:

                if(exitStatus[3].equals("LOCKED")) {
                    Parse.printAndFormat("The door is locked.");
                    return false;
                }

                if(exitStatus[3].equals("BIG") && playerSize != 2) {
                    Parse.printAndFormat("This door is HUGE. You are too small " +
                                         "to reach the handle.");
                    return false;
                }

                if(exitStatus[3].equals("SMALL") && playerSize != 0){
                    Parse.printAndFormat("This door is TINY. You are too big " +
                                         "to fit through.");
                    return false;
                }

                if(exitStatus[3].equals("LOST")) {
                    Events.execute("FOREST-MAZE");
                }

                break;

            case UP:

                if(exitStatus[4].equals("LOCKED")) {
                    Parse.printAndFormat("The door is locked.");
                    return false;
                }

                if(exitStatus[4].equals("BIG") && playerSize != 2) {
                    Parse.printAndFormat("This door is HUGE. You are too small " +
                                         "to reach the handle.");
                    return false;
                }

                if(exitStatus[4].equals("SMALL") && playerSize != 0){
                    Parse.printAndFormat("This door is TINY. You are too big " +
                                         "to fit through.");
                    return false;
                }

                if(exitStatus[4].equals("LOST")) {
                    Events.execute("FOREST-MAZE");
                }

                break;

            case DOWN:

                if(exitStatus[5].equals("LOCKED")) {
                    Parse.printAndFormat("The door is locked.");
                    return false;
                }

                if(exitStatus[5].equals("BIG") && playerSize != 2) {
                    Parse.printAndFormat("This door is HUGE. You are too small " +
                                         "to reach the handle.");
                    return false;
                }

                if(exitStatus[5].equals("SMALL") && playerSize != 0){
                    Parse.printAndFormat("This door is TINY. You are too big " +
                                         "to fit through.");
                    return false;
                }


                if(exitStatus[5].equals("LOST")) {
                    Events.execute("FOREST-MAZE");
                }

                break;

        }

        return true;

    } // end checkExitStatus()

    /**
     * take(): take an item from the current room's inventory and add it to the
     *         player's inventory
     * @param Item item: the item to be taken from the room
     * @throws IllegalArgumentException: if the item is not present in the room inventory
     */
    public static void take(Item item){

        if(currentRoomInventory.hasItem(item)){

            playerInventory.add(item);

            if(playerInventory.hasItem(item))
                System.out.println("You picked up " + item.getName());
            else
                System.out.println("You have failed to pick up " + item.getName());

            currentRoomInventory.remove(item);

            if(currentRoomInventory.hasItem(item))
                System.out.println(item.getName() + " sits on the ground, mocking you.");

        } else {

            throw new IllegalArgumentException("Item not found");

        }

    } // end take()

    /**
     * drop(): take an item from the player's inventory and add it to the
     *         current room's inventory
     * @param Item item: the item to be taken from the player inventory
     * @throws none
     */
    public static void drop(Item item){

        if(playerInventory.hasItem(item)){

            playerInventory.remove(item);
            currentRoomInventory.add(item);
            System.out.println("You drop " + item.getName());
            Events.execute("TRADE");

        } else {

            Parse.printAndFormat("You do not have that item to drop.");

        }

    } // end drop()

    /**
     * touch(): calls interaction method from Events on a specific item
     *          or character.
     * @param String touched: the name of the item being interacted with
     * @throws none
     * @return none
     */
    public static void touch(String touched){

        if(playerInventory.hasItem("GOLDKEY"))
            Events.execute("UNLOCK");

        if(currentRoomInventory.hasItem(touched)){

            Item touchedItem = currentRoomInventory.getItem(touched);

            String[] itemEvents = touchedItem.getEvents();

            for(String event : itemEvents){

                if(event.equals("NONE"))
                    continue;

                Events.execute(event);

            }

        } else if (playerInventory.hasItem(touched)){

            Item touchedItem = playerInventory.getItem(touched);

            String[] itemEvents = touchedItem.getEvents();

            for(String event : itemEvents){

                if(event.equals("NONE"))
                    continue;

                Events.execute(event);

            }

        } else if (characters.contains(new Character(touched.toLowerCase()))){

            for(Character character : characters){

                if(character.getName().equals(touched)){

                    String event = character.getEvent();
                    Events.execute(event);

                }
            }

        } else {

            Parse.printAndFormat("Nothing happens.");

        }

    } // end touch()

    /**
     * talk(): facilitates talking to a game character
     * @param Character character: the character spoken to
     * @return none
     * @throws IllegalArgumentException: if the character is not present in the
     *                                   current location
     */
    public static void talk(Character character){

        String event = character.getEvent();

        if(characters.contains(character)) {

            String[] characterDialogue = character.getDialogue();
            int talkCount = character.getTalkCount();

            if(talkCount >= characterDialogue.length - 1){

                if(!character.checkEventTriggered() && !event.equals("NONE"))
                    Events.execute(event);
                else
                    Parse.printAndFormat(characterDialogue[characterDialogue.length - 1]);

            } else {

                Parse.printAndFormat(characterDialogue[talkCount].trim());
                character.increaseTalkCount();

            }

        } else {

            throw new IllegalArgumentException(character.getName() + " is not here");

        }

    } // end talk()


    /**
     * inventory(): prints the player inventory
     * @param none
     * @return none
     * @throws none
     */
    private static void inventory(){

        System.out.println(playerInventory.printInventory(true));

    } // end inventory()

    /**
     * classifyObject(): receieves the name of a game object, and classifies it
     *                   according to the current context as either an Item,
     *                   Character, or Location.
     * @param String objName: name of the object requiring classification
     * @return String classifying the object as ITEM, CHARACTER, or DIRECTION;
     *         NOCLASS otherwise
     */
    private static String classifyObject(String objName){

       if(currentRoomInventory.getItem(objName) != null || playerInventory.getItem(objName) != null)
           return "ITEM";

       try {
           Character character = new Character(objName);

           if(characters.contains(character))
               return "CHARACTER";
       } catch (Exception e) {
           // Do nothing. This exception will simply be ignored as it indicates
           // that there is no game character matching objName
       }

       try {
           Direction.valueOf(objName);
           return "DIRECTION";
       } catch (Exception e) {
           // Do nothing. This exception is ignored, as it only indicates that
           // objName does not correspond to a direction
       }

       return "NOCLASS";

    } // end classifyOjbect()

    /**
     * help(): prints help dialogue about a desired topic
     * @param String topic: the name of a command the user wishes to learn more
     *                      about
     * @return none
     * @throws none
     */
    public static void help(String topic){

        System.out.println("\t\t\t\tHELP");
        System.out.println("----------------------------------------------------------------------------------");

        if(topic == null){

            System.out.println("  The game is played by providing <COMMAND> <OBJECT> pairs"
                               + " which are interpreted\n  by the game before it provides a "
                               + "response.");
            System.out.println();
            System.out.println("  Help with a specific command may be accessed by typing \"(h)elp"
                               + " <COMMAND>\"");

            return;

        }

        switch (topic){

            case "COMMANDS":
                System.out.println("  VALID COMMANDS:");
                System.out.println();
                System.out.println("\t \"GO\" \"TAKE\" \"DROP\" \"LOOK\" \"TOUCH\""
                                   + " \"TALK\" \"INVENTORY\" \"HELP\" \"QUIT\"");
                System.out.println();
                System.out.println("\t NOTE: commands have several synonyms, including"
                                   + " using the first\n\t letter of their name");
                System.out.println("\t\te.g. \"G\" or \"g\" for GO");
                break;

            case "GO":
                System.out.println("  COMMAND: GO");
                System.out.println();
                System.out.println("\tSyntax: \"GO <DIRECTION>\""
                                   + "\n\n\t\t <DIRECTION> is one of the following:"
                                   + " NORTH, SOUTH,\n\t\t EAST, WEST, UP, DOWN");
                System.out.println();
                System.out.println("\t\t Synonyms include: G, WALK, MOVE");
                break;

            case "TAKE":
                System.out.println("  COMMAND: TAKE");
                System.out.println();
                System.out.println("\tSyntax: \"TAKE <OBJECT>\""
                                   + "\n\n\t\t <OBJECT> is any interactable object.");
                System.out.println();
                System.out.println("\t\t Synonyms include: T, GRAB");
                break;

            case "DROP":
                System.out.println("  COMMAND: DROP");
                System.out.println();
                System.out.println("\tSyntax: \"DROP <OBJECT>\""
                                   + "\n\n\t\t <OBJECT> is any interactable object.");
                System.out.println();
                System.out.println("\t\t Synonyms include: D, DISCARD, LEAVE");
                break;

            case "LOOK":
                System.out.println("  COMMAND: LOOK");
                System.out.println();
                System.out.println("\tSyntax: \"LOOK <OBJECT/NONE>\""
                                   + "\n\n\t\t <OBJECT> may be any interactable game object"
                                   + "\n\n\t\t <NONE>: this command may be used with no object");
                System.out.println();
                System.out.println("\t\t Synonyms include: L, STARE, INSPECT");
                break;

            case "TOUCH":
                System.out.println("  COMMAND: TOUCH");
                System.out.println();
                System.out.println("\tSyntax: \"TOUCH <OBJECT>\""
                                   + "\n\n\t\t <OBJECT> may be any interactable game object.");
                System.out.println();
                System.out.println("\t\t Synonyms include: USE, ACTIVATE");
                break;

            case "TALK":
                System.out.println("  COMMAND: TALK");
                System.out.println();
                System.out.println("\tSyntax: \"TALK <OBJECT>\""
                                   + "\n\n\t\t <OBJECT> may be any interactable game object");
                System.out.println();
                System.out.println("\t\t TALK has no synonyms");
                break;

            case "INVENTORY":
                System.out.println("  COMMAND: INVENTORY");
                System.out.println();
                System.out.println("\tSyntax: \"INVENTORY\""
                                   + "\n\n\t\t This command has no object, it will print the"
                                   + "\n\t\t player's inventory and exit");
                System.out.println();
                System.out.println("\t\t Synonyms include: I");
                break;

            case "ATTACK":
                System.out.println("  COMMAND: ATTACK");
                System.out.println();
                System.out.println("\tSyntax: \"ATTACK\""
                                   + "\n\n\t\t This command will attempt to attack a viable"
                                   + "\n\t\t target near you. Requires CROQUET MALLET");
                System.out.println();
                System.out.println("\t\t Synonyms include: A");
                break;

            case "QUIT":
                System.out.println("  COMMAND: QUIT");
                System.out.println();
                System.out.println("\tSyntax: \"QUIT\""
                                   + "\n\n\t\t Quits the game");
                System.out.println();
                System.out.println("\t\t QUIT has no synonyms");
                break;

           default:
               System.out.println("  The game is played by providing <COMMAND> <OBJECT> pairs"
                                  + " which are interpreted\n  by the game before it provides a "
                                  + "response.");
               System.out.println();
               System.out.println("  Help with a specific command may be accessed by typing \"(h)elp"
                                  + " <COMMAND>\"");
               break;

        }

        System.out.println();
        System.out.println("What would you like to do?");

    } // end help()

    /**
     * setTestWinCond(): Toggles the win condition to allow for game testing
     *                   win condition to be used.
     * @param none
     * @return none
     * @throws none
     */
    public static void setTestWinCond(){

        testWinCond = true;

    } // end setTestWinCond()

    /**
     * checkWinState(): checks if the game has been won.
     * @param boolean testWinCond: true if the game is in testing mode; toggles
     *                             the win conditions to the simplified
     *                             conditions used during testing.
     * @return boolean value indicating whether or not the win state has been reached
     * @throws none
     */
    public static void checkWinState(){

        String currentRoomName = currentRoom.getName();

        if(testWinCond){

            if(currentRoomName.equals("START") &&
               currentRoomInventory.hasItem("ITEM1") &&
               currentRoomInventory.hasItem("ITEM2"))
                Events.win();

        }

        if(currentRoomName.equals("rabbithole") &&
           currentRoomInventory.hasItem("SPADEKEY") &&
           currentRoomInventory.hasItem("HEARTKEY") &&
           currentRoomInventory.hasItem("DIAMONDKEY") &&
           currentRoomInventory.hasItem("CLUBKEY"))
            Events.win();

    }

} // end Control class
