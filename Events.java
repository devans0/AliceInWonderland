/**
 * title: Events
 * date: March 27 2024
 * @author Dominic Evans
 * studnet-id: 3612857
 * @version 1.0
 * @copyright 2024 Dominic Evans
 */

/**
 * DOCUMENTATION
 */

/**
 * EVENTS
 *
 * Purpose and Description:
 *
 * The Events class handles all special events associated with characters and
 * locations. These include any mini games or game-state-changing effects that
 * arise as a consequence of interacting with a character, item, or
 * environment.
 *
 * Each event method is responsible for checking for the state variables that
 * trigger it's execution. For example, many of the item-giving events are
 * triggered once the player has reached the final dialogue option for a
 * character. The event should check whether or not the dialogue options have
 * been exhausted before executing the rest of the code.
 *
 * Because each event method governs it's own execution parameters, events are
 * highly modulare and may be added and removed per game requirements. Events is
 * the intended home for all game extensions.
 *
 * EVENTS Methods:
 *
 *   public static void execute(String eventName)
 *     Associates the eventName string with a game event, allowing other classes
 *     to pass strings to the Events class and activate the events they
 *     indicate.
 *
 *   private static void big()
 *     Increases the player size, and prints relevant messages indicating that
 *     the EAT ME cake has been consumed. Will not increase the player size
 *     above 2.
 *
 *   private static void small()
 *     Reduces the player size, and prints the relevant messages indicating that
 *     the DRINK ME potion has been consumed. Will not reduce the player size
 *     below 0.
 *
 *   private static void keyholes()
 *     Prints the current status of the keyholes in the rabbithole location.
 *     Intended to provide updates for player progress.
 *
 *   private static void steal()
 *     Has the thief steal the jewel from the player, if he hasn't already.
 *     Prints out the relevant messages, and removes the jewel from the player's
 *     inventory if it is being stolen. If it has already been stolen, the thief
 *     does nothing. If the thief has the stolen jewel, he returns it.
 *
 *   private static void trade()
 *     Called whenever an item is dropped. Checks the current room for key items
 *     that particular characters are interested in, and gives them that item if
 *     they want it. In return, the character gives the player another item.
 *     Provides all inventory management for the exchange.
 *
 *   private static void forestMaze()
 *     Maze minigame, in which the player must navigate the dark forest to both
 *     escape it, and map it. Once the player has escaped the maze, this method
 *     changes the exit status for the dark forest location to OPEN.
 *
 *   prviate static void hatterGame()
 *     Riddles minigame, in which the player must answer the Mad Hatter's
 *     riddles. The player succeeds when they answer two of three riddles
 *     correctly, and are rewarded with a key and the Hatter's watch. If the
 *     player has already succeeded, print a congratulatory message and exit.
 *
 *   private static void stir()
 *     Allows the player to stir the pepper soup in the Duchess' cabin. Causes
 *     the big baby to sneeze and drop the diamond key.
 *
 *   private static void giveLetter()
 *     Causes the Duchess to give the player the letter of recommendation, once
 *     the player has spoken to her enough times.
 *
 *   private static void attack()
 *     Checks that the necesssary conditions are satisfied for an attack to take
 *     place. The player must have the croquet mallet, and the Queen must be
 *     present to make an attack.
 *
 *   private static void trial()
 *     The player is put on trial by the Queen of Hearts. She will kill the
 *     player, unless the player has the letter of recommendation to exonerate
 *     her.
 *
 *   private static void unlock()
 *     Allows the player to use the golden key to unlock the door in the safe
 *     zone, providing a more direct route from the safe zone to the Queen's
 *     garden.
 *
 *   public static void win()
 *     Ends the game in a win state.
 *
 *   public static void quit()
 *     Quits the game
 *
 *   public static void kill()
 *     Kills the player, with no reason given.
 *
 *   public static void kill(String cause)
 *     Kills the player, and prints a message giving the cause of death.
 *
 * EVENTS Variables:
 *
 *   private static Location currentLoc
 *     The current location object, as reported by Control
 *
 *   private static Inventory playerInventory
 *     The current player inventory, as reported by Control
 *
 *   private static Inventroy currentRoomInventory
 *     The current location's inventory, as reported by Control
 *
 *   private static ArrayList<Character> presentCharacters
 *     A list of the Characters present in the current location, as reported by
 *     Control
 *
 *   private static int playerSize
 *     Current player size, as reported by Control
 *
 *   private static boolean jewelStolen
 *     Denotes whether or not the jewel has been stolen from the player yet.
 *     Used to ensure that the player only has the jewel stolen once.
 *
 *   private static boolean hatterGameWon
 *     Denotes whether or not the player has successfully completed the Mad
 *     Hatter's riddles. Ensures that the Hatter doesn't badger the player
 *     infinitely for more answers to the same riddles.
 *
 *   private static String[] currentCommand
 *     The current command words, as reported by Control
 *
 *   private static boolean trialComplete
 *     Denotes whether or not the player has stood trial before the Queen of
 *     Hearts. Avoids infinite trials.
 *
 *   private static gaveLetter
 *     Denotes whether or not the Duchess has given the player the letter of
 *     recommendation yet. Avoid infiinite letters being generated.
 *
 */

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Events {

    private static Location currentLoc;
    private static Inventory playerInventory;
    private static Inventory currentRoomInventory;
    private static ArrayList<Character> presentCharacters;
    private static int playerSize;
    private static boolean jewelStolen = false;
    private static boolean hatterGameWon = false;
    private static String[] currentCommand = new String[3];
    private static boolean trialComplete = false;
    private static boolean gaveLetter = false;

    /**
     * execute(): executes specific events depending on the input string.
     * @param String eventName: name of an event that another class wants to
     *                          execute.
     * @return none
     * @throws IllegalArgumentException if the eventName does not correspond to
     *                                  a game event.
     */
    public static void execute(String eventName){

        // Import the current game state for event context
        currentLoc = Control.getCurrentRoom();
        playerInventory = Control.getPlayerInventory();
        currentRoomInventory = Control.getCurrentRoomInventory();
        presentCharacters = Control.getCharacters();
        currentCommand = Control.getCurrentCommand();

        switch(eventName.toUpperCase()){

            case "NONE":
                return;

            case "BIG":

                big();
                break;

            case "SMALL":

                small();
                break;

            case "KEYHOLES":

                keyholes();
                break;

            case "HATTERGAME":

                hatterGame();
                break;

            case "STIR":

                stir();
                break;

            case "FOREST-MAZE":

                forestMaze();
                break;

            case "STEAL":

                steal();
                break;

            case "TRADE":

                trade();
                break;

            case "GIVE-LETTER":

                giveLetter();
                break;

            case "TRIAL":

                trial();
                break;

            case "ATTACK":

                attack();
                break;

            case "UNLOCK":

                unlock();
                break;

            case "TIME":

                Parse.printAndFormat("Looking at the HATTER's strange watch " +
                                     "you see that it is " + ZonedDateTime.now());
                Parse.printAndFormat("A very strange watch indeed...");
                break;

            default:
                throw new IllegalArgumentException("EVENTS EXECUTE : Invalid event");

        }

    } // end execute()

    /**
     * big(): make the player larger, if possible.
     * @param none
     * @return none
     * @throws none
     */
    private static void big(){

        playerSize = Control.getPlayerSize();
        String outcome = "You eat a piece of the EAT ME cake ";

        switch(playerSize){

            case 0:
                playerSize++;
                outcome += "and return to normal size.";
                break;

            case 1:
                playerSize++;
                outcome += "and become HUGE.";
                break;

            case 2:
                outcome += "and realize that you are already big enough... ";
                outcome += "it's not likely the cake will make you any larger.";
                break;

        }

        Control.setPlayerSize(playerSize);
        Parse.printAndFormat(outcome);

    } // end big()

    /**
     * small(): make the player smaller, if possible.
     * @param none
     * @param none
     * @throws none
     */
    private static void small(){

        playerSize = Control.getPlayerSize();
        String outcome = "You drink a bit of the DRINK ME potion ";

        switch (playerSize){

            case 0:
                outcome += "and realize that you are already small enough... ";
                outcome += "it's not likely the potion will make you any smaller.";
                break;

            case 1:
                playerSize--;
                outcome += "and become tiny.";
                break;

            case 2:
                playerSize--;
                outcome += "and return to your normal size.";
                break;

        }

        Control.setPlayerSize(playerSize);
        Parse.printAndFormat(outcome);

    } // end small()

    /**
     * keyholes(): updates the player on how many of the keyholes in the safe
     *             zone are filled.
     * @param none
     * @return none
     * @throws none
     */
    private static void keyholes(){

        // Do not enter event if the player was not looking at the keyholes
        if(!currentCommand[0].equals("LOOK"))
            return;

        int keyCount = 0;
        int keysLeft;
        String outcome = "You look around at the keyholes. The ";

        if(currentRoomInventory.hasItem("SPADEKEY")){

            outcome += "SPADE ";
            keyCount++;

        }

        if(currentRoomInventory.hasItem("DIAMONDKEY")){

            outcome += "DIAMOND ";
            keyCount++;

        }

        if(currentRoomInventory.hasItem("CLUBKEY")){

            outcome += "CLUB ";
            keyCount++;

        }

        if(currentRoomInventory.hasItem("HEARTKEY")){

            outcome += "HEART ";
            keyCount++;

        }

        keysLeft = 4 - keyCount;

        if(keysLeft == 4)
            outcome = "You look around at the keyholes. All of them are still" +
                " emtpy. You have some work to do.";
        else
            outcome += "slots have keys in them. Only " + keysLeft + " to go.";

        Parse.printAndFormat(outcome);

    } // end keyholes()

    /**
     * steal(): Allows the thief to steal the jewel from the player.
     * @param none
     * @return none
     * @throws none
     */
    private static void steal(){

        if(jewelStolen && !playerInventory.hasItem("JEWEL")){

            System.out.println();
            Parse.printAndFormat("The thief is here! Now that you've caught him again, " +
                                 "he gives the jewel back with some complaining.");

            playerInventory.add(new Item("jewel"));
            Control.setPlayerInventory(playerInventory);

        } else if (jewelStolen && playerInventory.hasItem("JEWEL")) {

            System.out.println();
            Parse.printAndFormat("The thief is here! Since you've already caught him, " +
                                 "he'll leave you alone.");

        } else if (!jewelStolen && playerInventory.hasItem("JEWEL")){

            playerInventory.remove(new Item("jewel"));

            Control.setPlayerInventory(playerInventory);

            System.out.println();
            Parse.printAndFormat("You feel a hand reach into your pocket! Before you " +
                                 "can react, the THIEF has pulled the JEWEL out of your " +
                                 "pocket. As soon as he has it, he runs away.");
            jewelStolen = true;

        } else if (!jewelStolen && !playerInventory.hasItem("JEWEL")){

            System.out.println();
            Parse.printAndFormat("There is a shady man here. He is dressed in dark and " +
                                 "drab colours - in fact he looks like a THIEF. He seems " +
                                 "to be watching you. Very suspicious...");

        }

    } // end steal()

    /**
     * trade(): Give an item to a character, and receive one in return
     * @param none
     * @return none
     * @throws none
     */
    private static void trade(){

        currentRoomInventory = Control.getCurrentRoomInventory();

        if(currentRoomInventory.hasItem("FAN")){

            if(presentCharacters.contains(new Character("rabbit"))) {

                    Parse.printAndFormat("\"OH MY! You found my FAN! Thank you so much!\"" +
                                         "BR The WHITE RABBIT takes the FAN, and gives you " +
                                         "the GOLD KEY, and the SPADE KEY. BR " +
                                         "\"I really can't thank you enough!\"");

                    currentRoomInventory.remove(new Item("fan"));
                    playerInventory.add(new Item("spadekey"));
                    playerInventory.add(new Item("goldkey"));
                    Control.setPlayerInventory(playerInventory);

        }

        }

    } // end trade()

    /**
     * forestMaze(): Minigame for navigating the dark forest. Once the game is
     *               completed successfully, the forest becomes navigable
     *               normally.
     * @param none
     * @return none
     * @throws none
     */
    private static void forestMaze(){

        boolean lost = true;
        String input;
        int patternCount = 0;
        Scanner userInput = new Scanner(System.in);
        String[] exitPattern = new String[] { "RIGHT", "LEFT", "LEFT", "RIGHT", "STRAIGHT" };

        Parse.printAndFormat("You walk down the forest path, but quickly become hopelessly " +
                             "lost. Every tree looks the same, and you can't remember which " +
                             "path you took to get here. You are at a crossroads, with paths " +
                             "leading STRAIGHT, LEFT, or RIGHT.");

        while(lost){

            System.out.println("Which way do you go?");
            System.out.print(">> ");

            input = userInput.nextLine().toUpperCase();

            switch(input){

                case "LEFT":

                    if(exitPattern[patternCount].equals(input)){

                        patternCount++;
                        System.out.println();
                        Parse.printAndFormat("You go " + input + ". It feels like you're " +
                                             "closer to the exit.");

                    } else {

                        patternCount = 0;
                        Parse.printAndFormat("You go " + input + ". After some " +
                                             "wandering you find yourself back where " +
                                             "you started.");

                    }

                    break;

                case "RIGHT":

                    if(exitPattern[patternCount].equals(input)){

                        patternCount++;
                        System.out.println();
                        Parse.printAndFormat("You go " + input + ". It feels like you're " +
                                             "closer to the exit.");

                    } else {

                        patternCount = 0;
                        System.out.println();
                        Parse.printAndFormat("You go " + input + ". After some " +
                                             "wandering you find yourself back where " +
                                             "you started.");

                    }

                    break;

                case "STRAIGHT":

                    if(exitPattern[patternCount].equals(input)){

                        patternCount++;
                        System.out.println();
                        Parse.printAndFormat("You go " + input + ". It feels like you're " +
                                             "closer to the exit.");

                    } else {

                        patternCount = 0;
                        System.out.println();
                        Parse.printAndFormat("You go " + input + ". After some " +
                                             "wandering you find yourself back where " +
                                             "you started.");

                    }

                    break;

                default:
                    System.out.println();
                    Parse.printAndFormat("You try to wander " + input + " but that is not " +
                                         "a direction in the forest. You end up back" +
                                         "where you started. Try \"LEFT,\" \"RIGHT,\"" +
                                         "  or \"STRAIGHT.\"");
                    patternCount = 0;
                    break;

            }

            if(patternCount == exitPattern.length)
                lost = false;

        }


        System.out.println();
        Parse.printAndFormat("You have found your way out of the darkest parts of the " +
                             "DARK FOREST. You now feel that you can find your way " +
                             "through with little difficulty.");

        // Change the exit status of the dark forest to OPEN for the remainder
        // of the game
        for(int i = 0; i < 6; i++)
            currentLoc.setExitStatus(i, "OPEN");

    } // end forestMaze()

    /**
     * hatterGame(): The Mad Hatter's game of riddles
     * @param none
     * @return none
     * @throws none
     */
    private static void hatterGame(){

        if(hatterGameWon){

            Parse.printAndFormat("\"Good show! Good show! I hope that we can " +
                                 "come down with another bout of riddles soon!\"");
            return;

        }

        String[] riddles = new String[]{ "\"What goes up but never comes down?\"",
                                         "\"I can fly, but have no wings. I can cry but have no eyes. Wherever I go, darkness follows me. What am I?\"",
                                         "\"What has to be broken before you can use it?\"" };
        String[] answers = new String[]{ "AGE", "CLOUD", "EGG" };
        Scanner user = new Scanner(System.in);
        String[] userInput;
        String userAnswer;
        int userScore = 0;

        
        Parse.printAndFormat("\"Now if you don't mind, I have a few riddles for you. If " +
                             "you answer well, there'll even be prizes!\" says the " +
                             "HATTER. \"Shall we begin?\" (Y/N)");
        System.out.print(">> ");

        userAnswer = user.nextLine().toUpperCase();

        if(userAnswer.equals("YES") || userAnswer.equals("Y")) {

            System.out.println();
            Parse.printAndFormat("\"Excellent! Now then, let me think of a good one...\" " +
                                 "and the hatter strokes his chin, contemplating.");

        } else {

            System.out.println();
            Parse.printAndFormat("\"Oh, that's too bad, perhaps another time then.\"");
            return;

        }

        for(int i = 0; i < riddles.length; i++){

            Parse.printAndFormat(riddles[i]);

            System.out.print(">> ");

            userInput = user.nextLine().toUpperCase().split(" ");

            for(String word : userInput){

                if(Parse.isPreposition(word))
                    continue;
                else
                    userAnswer = word;

            }

            if(userAnswer.equals(answers[i])) {

                System.out.println();
                Parse.printAndFormat("\"Yes! That's exactly right!\" the hatter " +
                                     "jumps up and down in his seat excitedly.\"");
                userScore++;

            } else {

                System.out.println();
                Parse.printAndFormat("The hatter frowns, considering your answer " +
                                     "\"No, sorry that's not quite right... \"");

            }

        }

        if (userScore >= 2){

            hatterGameWon = true;

            System.out.println();
            Parse.printAndFormat("\"Splendid showing! You are quite the riddler - " +
                                 "positively RIDDLED in fact! Here are your prizes " +
                                 "as promised.\" BR The HATTER looks around the table " +
                                 "absent-mindedly before something seems to catch his " +
                                 "eye. He pulls an ornate key with a head shaped like " +
                                 "a playing-card's CLUB out of a nearby pot of jam " +
                                 "\"Here you are! Oh and you look like someone who " +
                                 "could use the time, as well.\" He pulls his watch " +
                                 "from his coat pocket and hands it over with the " +
                                 "sticky key.");

            playerInventory.add(new Item("clubkey"));
            playerInventory.add(new Item("watch"));
            Control.setPlayerInventory(playerInventory);

        } else {

            System.out.println();
            Parse.printAndFormat("\"Well, unfortunately that was not a performance due " +
                                 "a reward. Perhaps you'd like to try again? Just ask " +
                                 "anytime for a rematch.\"");

        }

    } // end hattersGame

    /**
     * stir(): Stir the soup in the duchess' cabin, causing the baby to sneeze
     *         and give the diamond key to the player.
     * @param none
     * @return none
     * @throws none
     */
    private static void stir(){

        Parse.printAndFormat("You grasp the massive ladle, and stir the large pot of soup " +
                             "as soon as the liquid begins to move, the air fills with " +
                             "the scent of pepper, as a cloud of the spice is raised " +
                             "from the surface of the mixture. The BABY begins to sneeze " +
                             "uncontrollably, and the DUCHESS begins to complain and " +
                             "beat the poor child. During the entire commotion, the DIAMOND " +
                             "KEY the child was playing with somehow flies through the " +
                             "air and lands in your pocket. What luck!");

        playerInventory.add(new Item("diamondkey"));
        Control.setPlayerInventory(playerInventory);

    } // end stir()

    /**
     * giveLetter(): Player receives a letter of recommendation from the
     *               Duchess
     * @param none
     * @return none
     * @throws none
     */
    private static void giveLetter(){

        gaveLetter = true;

        if(playerInventory.hasItem("LETTER"))
            return;

        Parse.printAndFormat("\"You seem like such a nice girl. I have a very good nose " +
                             "for these things, you know. I am always right about it. Yes, " +
                             "you are clearly one of the good ones! Why,  I'm so sure of it " +
                             "I'll give you a LETTER of recommendation.\" The DUCHESS " +
                             "rummages in her apron pocket for a moment before producing a " +
                             "beautifully sealed letter. \"I'd say that could get you out " +
                             "of a SPOT OF TROUBLE, yes indeed. Well, thank you for visiting\"");

        playerInventory.add(new Item("letter"));

    } // end giveLetter()

    /**
     * attack(): Use the croquet mallet to attack the QUEEN
     * @param none
     * @return none
     * @throws none
     */
    private static void attack(){

        if(playerInventory.hasItem("CROQUETMALLET")) {

            if(presentCharacters.contains(new Character("queen"))){

                Parse.printAndFormat("You attack the queen with the CROQUET MALLET. " +
                                     "She becomes extremely frightened and runs " +
                                     "away, with her entire retinue in toe. In her " +
                                     "haste, she drops the HEART KEY.");

                currentRoomInventory.add(new Item("heartkey"));
                Control.setCurrentRoomInventory(currentRoomInventory);
                Control.removeCharacter(new Character("queen"));
                Control.removeCharacter(new Character("cards"));

            } else {

                Parse.printAndFormat("It's not very nice to attack people with " +
                                     "CROQUET MALLETS. It's really best reserved " +
                                     "for TYRANTS.");

            }
        } else {

            Parse.printAndFormat("You do not have anything to attack with...");

        }

    } // end attack()

    /**
     * trial(): Alice is put on trial by the Queen of Hearts
     * @param none
     * @return none
     * @throws none
     */
    private static void trial(){

        if(trialComplete)
            return;

        Parse.printAndFormat("The QUEEN looks at you with disgust. \"You have lingered " +
                             "far too long, young lady! This is most improper! You " +
                             "must know that impropriety is punishable by DEATH! OFF WITH " +
                             "HER HEAD\" the QUEEN's voice rises higher and higher as she " +
                             "speaks, reaching screeching heights. She gestures to some " +
                             "nearby guards, and they begin to close in on you. BR As it " +
                             "looks as though you are about to be grabbed, a small and " +
                             "portly fellow squeezes out from the crowd of onlookers. He " +
                             "is wearing a crown adorned with hearts. This must be the KING " +
                             "\"Now, my dear, we agreed that we shan't be beheading without " +
                             "a trial from now on...\" he says quietly. The QUEEN turns a " +
                             "few shades redder before the colour drains from her face a " +
                             "a little and she visibly collects herself before presenting " +
                             "you with a saccharine smile. \"Of course, my dear, you are " +
                             "quite right. Then we shall have a TRIAL! How exciting! BR" +
                             "The entire retinue looks quite excited, as they follow the " +
                             "KING and QUEEN down a garden path. You feel hands on your " +
                             "arms, as some guards grab you and usher you down the same " +
                             "path behind the royal retinue. You soon find yourself in a " +
                             "spacious paved squre in the garden's center, with the royal " +
                             "retinue arrayed about the fringes of the square, eagerly " +
                             "awaiting the beginning of the trial. The KING and QUEEN " +
                             "sit at the far end of the square in large thrones. BR" +
                             "A scribe comes forth and proclaims to the assembled crowd: " +
                             "\"WE HAVE BEFORE US THE ACCUSED, ALICE, WHO SHALL STAND TRIAL " +
                             "FOR THE CRIME OF SPEAKING ENTIRELY TOO LONG WITH HER ROYAL " +
                             "MAJESTY, THE QUEEN OF HEARTS. THIS IS A MOST EGREGIOUS CRIME " +
                             "DOES ANYONE HAVE ANYTHING NICE TO SAY FOR THIS CRIMINAL?\" BR");

        if(gaveLetter){

            Parse.printAndFormat("There is a terrible silence, and you swear you could hear " +
                                 "a pin drop. Just as it seems hopeless, you see a familiar, " +
                                 "albeit very tiny figure step forth from the assembled crowd. " +
                                 "It takes you a moment to recognize them, but you soon realize " +
                                 "that this is the DUCHESS coming to your aid. \"Why yes,\" says " +
                                 "the DUCHESS, \"I can attest that this is a very nice young " +
                                 "lady. She came to my cabin recently and we had a very nice " +
                                 "chat. As a matter of fact, I gave her a letter of " +
                                 "recommendation.\" The QUEEN looks very startled to hear this " +
                                 "and asks: \"Is this true, child? Check her pockets!\" BR");

            if(playerInventory.hasItem("LETTER")){

                Parse.printAndFormat("The guards come up to you and rifle through your pockets, " +
                                     "quickly finding the LETTER OF RECOMMENDATION from the DUCHESS. " +
                                     "Upon seeing it, the QUEEN looks very flustered. \"Well then, " +
                                     "I suppose you must be proper enough then, for the DUCHESS " +
                                     "to recommend you personally. Very well then, this court finds " +
                                     "the defendant NOT GUILTY! As recompense for this false " +
                                     "accusation, please accept this small token.\" The QUEEN " +
                                     "gestures and a servant runs forward with a beautiful pillow " +
                                     "upon which rests a HEART KEY. BR \"Now begone!\" screams the " +
                                     "QUEEN, and you are quickly brought back to the garden proper " +
                                     "along with the rest of the royal retinue.");

                playerInventory.add(new Item("heartkey"));
                Control.setPlayerInventory(playerInventory);

            } else {

                Parse.printAndFormat("The guards come up to you and rifle through your pockets, but " +
                                     "find nothing. \"LIAR,\" shrieks the QUEEN. \"NOW WE SHALL HAVE " +
                                     "BOTH OF YOUR HEADS!\" BR You see guards rapidly approach you and " +
                                     "the DUCHESS, grabbing a hold of you before you can act. They drag " +
                                     "you off to the side of the square where a red-stained stump " +
                                     "resides. There is a large man with an even larger axe standing " +
                                     "beside it. The guards roughly shove your neck against the stump, and " +
                                     "the last thing you see is the massive axe descending towards you...");

                kill("BEHEADED");

            }

        } else {

            Parse.printAndFormat("You wait with baited breath, but no one comes forth. " +
                                 "After a very short time waiting, the QUEEN hastily proclaims: " +
                                 "\"Well, if there is no one to speak to your goodness, you must " +
                                 "by definition be imporoper. Therefore we find you GUILTY! OFF " +
                                 "WITH HER HEAD!\" BR You see guards rapidly approach you and grab " +
                                 "a hold of you before you can act. They drag you off to the side " +
                                 "of the square where a red-stained stump resides. There is a large " +
                                 "man with an even larger axe standing beside it. The guards roughly " +
                                 "shove your neck against the stump, and the last thing you see is " +
                                 "the massive axe descending towards you...");

            kill("BEHEADED");
        }

    } // end trial()

    /**
     * unlock(): uses a key to unlock a location's door
     * @param none
     * @return none
     * @throws none
     */
    private static void unlock(){

        if(currentLoc.equals(new Location("rabbithole")) ||
           currentLoc.equals(new Location("darkforest"))){

            String[] exitStatus = currentLoc.getExitStatus();

            for(int i = 0; i < exitStatus.length; i++){

                if(exitStatus[i].equals("LOCKED"))
                    currentLoc.setExitStatus(i, "OPEN");

            }

            System.out.println();
            Parse.printAndFormat("You use the key to unlock the door.");

        } else {

            Parse.printAndFormat("There is nothing to unlock here");

        }

    } // end unlock()

    /**
     * win(): ends the game by winning
     * @param none
     * @return none
     * @throws none
     */
    public static void win(){

        System.out.println();
        System.out.println("Congratulations! You have escaped WONDERLAND. " +
                           "Thank you for playing.");
        System.exit(0);

    } // end win()

    /**
     * quit(): Quits out of the game
     * @param none
     * @return none
     * @throws none
     */
    public static void quit(){

        System.out.println();
        System.out.println("Quitting. Thank you for playing.");
        System.exit(0);

    } // end quit()

    /**
     * kill(): kills the player, ending the game, giving no reason.
     * @param none
     * @return none
     * @throws none
     */
    public static void kill(){

        System.out.println();
        System.out.println("You were killed.");
        System.out.println("GAME OVER");
        System.exit(0);

    } // end kill()

    /**
     * kill(): kills the player, ending the game for a stated reason.
     * @param String cause: The cause of the player death
     * @return none
     * @throws none
     */
    public static void kill(String cause){

        System.out.println();
        System.out.println("You were " + cause);
        System.out.println("GAME OVER. THANK YOU FOR PLAYING.");
        System.exit(0);

    }

} // end Event class
