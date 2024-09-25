/**
 * title: Alice in Wonderland
 * date: February 1 2024
 * @author Dominic Evans
 * student-id: 3612857
 * @version 1.0
 * @copyright 2024 Dominic Evans
 */

/**
 * DOCUMENTATION
 */

/**
 * GAME
 *
 * PURPOSE AND DESCRIPTION:
 *
 * The game class provides the core functionality of the Alice in Wonderland
 * game. It executes game initialization as well as executes the core game loop,
 * functioning as a rudimentary game engine. Game also holds all global
 * variables that describe the game state.
 *
 * Compile : "javac Game.java Control.java Character.java Direction.java
 *            Events.java Inventory.java Item.java Location.java Parse.java"
 * Run     : "java Game"
 * Requires: Java standard library
 *
 * GAME METHODS:
 *
 *   public static void main(String[] args)
 *     Calls all methods related to running the game, and contains the core loop
 *     of the game, which is an infinite loop. The loop is broken either by the
 *     player winning the game, or when the player chooses to quit.
 *
 * GAME VARIABLES:
 *
 *   public static String locationsDataPath: data path for locations text file;
 *                                           change this variable for game
 *                                           configuration.
 *
 *   public static String characterssDataPath: data path for characters text file;
 *                                             change this variable for game
 *                                             configuration.
 *
 *   public static String itemsDataPath: data path for items text file; change this
 *                                       variable for game configuration.
 *
 *   public static String objectsDataPath: data path for objects text file; change
 *                                         this variable for game configuration.
 *
 *   public static String commandsDatapath: data path for commands text file;
 *                                          change this variable for game
 *                                          configuration.
 */

/**
 * TEST PLAN
 *
 * Due to the large and complex nature of this game, the testing plan will deal
 * with a smaller, but generally equivalent version of the final game. In this
 * way, the core functionality of the game engine can be demonstrated without
 * needing to provide extensive testing of the finalized game project. This
 * approach was selected in order to minimize the amount of time required to
 * test the features of the core game engine, which would require repeatedly
 * playing the game in order to select every available option.
 *
 *   STEPS:
 *
 *     1. After compiling run the game as follows: 'java Game test' to enable
 *        testing mode
 *     2. Ensure that the game loads succesfully, this demonstrates that data
 *        handling is functioning properly - i.e. all data files are read and
 *        properly loaded into the game
 *     3. Enter the following sequence of commands, and ensure that output
 *        matches the given examples exactly. In this section, any text preceded
 *        by the '>> ' symbol is one that is entered as a command. All other text
 *        is output. This first run tests and demonstrates auxillary commands
 *        (i.e. those that do not change game state):
 *
 * Starting location DESCRIPTION
 * >> help
 *
 *                                 HELP
 * ----------------------------------------------------------------------------------
 *   The game is played by providing <COMMAND> <OBJECT> pairs which are interpreted
 *   by the game before it provides a response.
 *
 *   Help with a specific command may be accessed by typing "(h)elp <COMMAND>"
 *   >> help inventory
 *
 *                                 HELP
 * ----------------------------------------------------------------------------------
 *   COMMAND: INVENTORY
 *
 *         Syntax: "INVENTORY"
 *
 *                 This command has no object, it will print the
 *                 player's inventory and exit
 *
 *                 Synonyms include: I
 *
 * What would you like to do?
 * >> help go
 *
 *                                 HELP
 * ----------------------------------------------------------------------------------
 *   COMMAND: GO
 *
 *         Syntax: "GO <DIRECTION>"
 *
 *                 <DIRECTION> is one of the following: NORTH, SOUTH,
 *                 EAST, WEST, UP, DOWN
 *
 *                 Synonyms include: G, WALK, MOVE
 *
 * What would you like to do?
 * >> help take
 *
 *                                 HELP
 * ----------------------------------------------------------------------------------
 *   COMMAND: TAKE
 *
 *        Syntax: "TAKE <OBJECT>"
 *
 *                 <OBJECT> is any interactable object.
 *
 *                 Synonyms include: T, GRAB
 *
 * What would you like to do?
 * >> help drop
 *
 *                                 HELP
 * ----------------------------------------------------------------------------------
 *   COMMAND: DROP
 *
 *         Syntax: "DROP <OBJECT>"
 *
 *                  <OBJECT> is any interactable object.
 *
 *                  Synonyms include: D, DISCARD, LEAVE
 *
 * What would you like to do?
 * >> help look
 *
 *                                 HELP
 * ----------------------------------------------------------------------------------
 *   COMMAND: LOOK
 *
 *      Syntax: "LOOK <OBJECT/NONE>"
 *
 *               <OBJECT> may be any interactable game object
 *
 *               <NONE>: this command may be used with no object
 *
 *               Synonyms include: L, STARE, INSPECT
 *
 * What would you like to do?
 * >> help touch
 *
 *                                 HELP
 * ----------------------------------------------------------------------------------
 *   COMMAND: TOUCH
 *
 *         Syntax: "TOUCH <OBJECT>"
 *
 *                  <OBJECT> may be any interactable game object.
 *
 *                  Synonyms include: USE, ACTIVATE
 *
 * What would you like to do?
 * >> help talk
 *
 *                                 HELP
 * ----------------------------------------------------------------------------------
 *   COMMAND: TALK
 *
 *         Syntax: "TALK <OBJECT>"
 *
 *                  <OBJECT> may be any interactable game object
 *
 *                  TALK has no synonyms
 *
 * What would you like to do?
 * >> help attack
 *
 *                                 HELP
 * ----------------------------------------------------------------------------------
 *   COMMAND: ATTACK
 *
 *         Syntax: "ATTACK"
 *
 *                  This command will attempt to attack a viable
 *                  target near you. Requires CROQUET MALLET
 *
 *                  Synonyms include: A
 *
 * What would you like to do?
 * >> help quit
 *
 *                                 HELP
 * ----------------------------------------------------------------------------------
 *    COMMAND: QUIT
 *
 *          Syntax: "QUIT"
 *
 *                   Quits the game
 *
 *                   QUIT has no synonyms
 *
 *  What would you like to do?
 *  >> quit
 *
 *
 * Quitting. Thank you for playing
 *
 *     4. Launch game again and enter the following commands, as above, to test
 *        core actions. Ensure that output matches below exactly:
 *
 *
 *           Starting location DESCRIPTION
 *           >> go north
 *
 *           Rabbit hole DESCRIPTION. ROOM2 EAST. ROOM3 SOUTH. ITEM1 HERE
 *           >> south
 *
 *           Starting location RETURN DESCRIPTION
 *           >> n
 *
 *           Rabbit hole RETURN DESCRIPTION. ROOM3 SOUTH. ITEM1 HERE
 *           >> talk rabbit
 *
 *           "Hello I am TEST RABBIT."
 *           >> talk rabbit
 *
 *           "This is TEST RABBIT dialogue part two."
 *           >> take item1
 *
 *           You picked up ITEM1
 *           >> inventory
 *
 *           You are carrying:
 *           ITEM1
 *           >> i
 *
 *           You are carrying:
 *           ITEM1
 *           >> go east
 *
 *           Room2 DESCRIPTION. RABBITHOLE WEST
 *           >> look
 *
 *           Room2 RETURN DESCRIPTION. RABBITHOLE WEST
 *           >> take item2
 *
 *           There is nothing like that here.
 *           >> take key
 *
 *           You picked up KEY
 *           >> west
 *
 *           Rabbit hole RETURN DESCRIPTION. ROOM3 SOUTH. ITEM1 HERE
 *           >> go north
 *
 *           The door is locked.
 *           Rabbit hole RETURN DESCRIPTION. ROOM3 SOUTH. ITEM1 HERE
 *           >> use key
 *
 *
 *           You use the key to unlock the door.
 *           >> go north
 *
 *           Room3 DESCRIPTION. RABBITHOLE NORTH. ITEM2 HERE.
 *           >> take item2
 *
 *           You picked up ITEM2
 *           >> look
 *
 *           Room3 RETURN DESCRIPTION. RABBITHOLE NORTH
 *           >> i
 *
 *           You are carrying:
 *           ITEM1 KEY ITEM2
 *           >> go north
 *
 *           Rabbit hole RETURN DESCRIPTION. ROOM3 SOUTH. ITEM1 HERE
 *           >> go south
 *
 *           Starting location RETURN DESCRIPTION
 *           >> drop item1
 *
 *           You drop ITEM1
 *           >> i
 *
 *           You are carrying:
 *           KEY ITEM2
 *           >> drop item2
 *
 *           You drop ITEM2
 *
 *           Congratulations! You have escaped WONDERLAND. Thank you for playing
 *
 *     5. Now that core functionaity is established, refer to SAMPLE-GAME.txt,
 *        and perform a similar test on the full game itself. Within
 *        SAMPLE-GAME.txt one will find a full transcript of a completed run of
 *        the game. This is only one pathway through the full game, but
 *        demonstrates that all events are functioning as intended, as the above
 *        testing does not verify events extensively.
 */

import java.util.Scanner;

public class Game {

    // Game configuration
    public static String locationsDataPath = "LOCATIONS.txt";
    public static String charactersDataPath = "CHARACTERS.txt";
    public static String itemsDataPath = "ITEMS.txt";
    public static String objectsDataPath = "OBJECTS.txt";
    public static String commandsDataPath = "COMMANDS.txt";
    public static String prepositionsDataPath = "PREPOSITIONS.txt";

    public static void main(String[] args){

        System.out.print("\033\143");

        if(args.length > 0 && args[0].equals("test")){

            locationsDataPath = "TEST_LOCATIONS.txt";
            charactersDataPath = "TEST_CHARACTERS.txt";
            itemsDataPath = "TEST_ITEMS.txt";
            objectsDataPath = "TEST_OBJECTS.txt";
            Control.setTestWinCond();

        }

        Scanner stdin = new Scanner(System.in);
        String userInput;
        String[] userCommands;

        // Initialize Game Data, and print initial description of starting area
        Control.init();
        Control.look();
        // Parse.populateVerbSynonyms("COMMANDS.txt");

        // Main game loop
        while(true){

            // Prompt the player for action
            System.out.print(">> ");
            userInput = stdin.nextLine().toUpperCase();

            // Parse action and determine if legal, execute action if possible,
            // or prompt for a new action if not
            try {

                System.out.println();

                userCommands = Parse.userInput(userInput);

                Control.dispatcher(userCommands);

            } catch (IllegalArgumentException e){

                System.out.println("I don't understand that action. Enter"
                                    + " '(h)elp' for instructions.");
                continue;

            }

            Control.checkWinState();
        }

    } // end main()



} // end class Game
