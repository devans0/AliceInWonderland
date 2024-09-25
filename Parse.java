/**
 * title: Parse
 * date: February 24 2024
 * @author: Dominic Evans
 * student-id: 3612857
 * @version: 1.0
 * @copyright: 2024 Dominic Evans
 */
/**
 * DOCUMENTATION
 */
/**
 * PARSE
 *
 * Purpose and Description:
 *
 * Provides parsing for both user input as well as data files for use in playing
 * and loading game data. Additionally houses methods that are used to classify
 * words into grammatical contexts, as well as loading data about legal
 * commands (game verbs), and objects.
 *
 * PARSE Methods:
 *
 *   public static ArrayList<String> readData(String field, String fileName)
 *     Reads a data file and returns a list of it's relevant contents. Relevancy
 *     of the content of the data file is determined through use of the 'field'
 *     parameter. The method will only return items associated with this field.
 *
 *   public static void populateVerbSynonyms(String filePath)
 *     Parses the text file found at filePath and stores all of the synonynms
 *     for the game verbs into a HashMap that maps the base verb to all of it's
 *     synonyms which are stored in the form of an array of strings. This will
 *     be used to compare against user input to determine whether an input word
 *     matches a game verb.
 *
 *   public static void populateObjects(String filePath)
 *     Parses the text file fou nd at filePath and stores all of the names of
 *     the game objects it finds there. These will be used to compare against
 *     user input to determine whether an input word matches a game object.
 *
 *   public static void populatePrepositions(String filePath)
 *     Adds all the words stored at filePath to the list of prepositions
 *
 *   public static String[] userInput(String input)
 *     Searches through the raw text provided from the user as a command and
 *     saves all of the relevant words for later processing in other parts of
 *     the game. A word is considered relevant if it is a verb, a verb synonym,
 *     or a valid game object. If a word doesn't fit this description it is
 *     saved as the thrid element of the returning String array, in order to
 *     preserve it for context-sensitive error messages.
 *
 *   public static boolean isPreposition(String word)
 *     Returns true if word is present in the list of prepositions; false
 *     otherwise.
 *
 *   private static String findBaseVerb(String word)
 *     Searches the verb-synonym map for the existence of word, as either an
 *     element of one of the arrays of strings, or as one of the keys of the
 *     hash map. If the word is found, the base word is returned.
 *
 *   private static boolean isVerb(String word)
 *     Searches the verb-synonym map for the existence of a word, as either an
 *     element of one fo the arrays of strings, or as one of the keys of the
 *     hash map. If the word is found, return true; false otherwise.
 *
 *   private static String findBaseObject(String word)
 *     Searches the verb-synonym map for the existence of word, as either an
 *     element of one of the arrays of strings, or as one of the keys of the
 *     hash map. If the word is found, the base word is returned.
 *
 *   private static boolean isObject(String word)
 *     Searches the list of game objects and returns true if the word is found
 *     in the list. False otherwise.
 *
 *   public static boolean isDirection(String word)
 *     If the word is one of the direction words (NORTH, SOUTH, EAST, WEST, UP,
 *     or DOWN), return true; false otherwise.
 *
 *   public static void printAndFormat(String output)
 *     Receives as input a string that the game wishes to output to the user.
 *     Print the string to the console token by token, keeping track of the
 *     total number of characters printed on a given line. If the number of
 *     characters exceed the desired width, insert a linebreak character and
 *     continue pritning. If the next token is 'BR', then a break in the text
 *     has been requested. Print two line breaks and continue printing. This
 *     method ensures proper formatting for output text.
 *
 * PARSE Variables:
 *
 *   private static final String[] verbs
 *     Exhaustive list of all of the possible action words present in the game.
 *     Each of these words is assigned synonyms, so that only the simple base
 *     words may be passed to further methods for processing.
 *
 *   private static HashMap<String, String[]> verbSynonyms
 *     Mapping of synonyms of a verb onto that verb as a base word. Used when
 *     searching for whether or not a word is considered a game verb or not as
 *     well as reducing that word down to it's 'base' verb.
 *
 *   private static ArrayList<String> objects
 *     Exhaustive list of all game objects, populated by the populateObjects
 *     method. This list defines what a valid game object is.
 *
 *   private static ArrayList<String> prepositions
 *     List of all of the prepositions recognized by the game. Prepositions are
 *     stored in a text file, and loaded upon game initialization.
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class Parse {

  private static final String[] verbs = new String[]{ "GO", "TAKE", "DROP", "LOOK", "TOUCH", "TALK",
                                                      "GIVE", "QUIT", "INVENTORY", "ATTACK","HELP" };
  private static HashMap<String, String[]> verbSynonyms = new HashMap<String, String[]>();
  private static HashMap<String, String[]> objects = new HashMap<String, String[]>();
  private static ArrayList<String> prepositions = new ArrayList<String>();

/**
 * readData(): reads specially formatted data from a text file, selecting only
 *             the desired data and returning it as an ArrayList.
 * @param String field: specifies which field to return from the text file.
 * @param String fileName: specifies from which file to read data from.
 * @throws FileNotFound if the specified fileName does not exist.
 */
  public static ArrayList<String> readData(String field, String fileName) {

    ArrayList<String> result = new ArrayList<String>();

    try {

      // Open data file and extract it's contents
      File file = new File(fileName);
      Scanner read = new Scanner(file);
      String fileContents = "";

      while(read.hasNext()) {
        fileContents += read.next() + " ";
      }

      // Compile pattern matching text file format for data headings
      Pattern p = Pattern.compile(field + "\\s+\\{\\s+([^\\}]*)\\s+\\}");
      Matcher m = p.matcher(fileContents);

      // Un-comment below for text file debugging when faced with
      // IllegalStateException: No match found
      // System.out.println("READ DATA: " + p.toString());

      // Search the contents of the file using above pattern
      m.find();

      String data = m.group(1).trim();

      result = new ArrayList<String>(Arrays.asList(data.split(";")));

      read.close();

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }

    return result;
  } // end readData()

  /**
   * populateVerbSynonyms() reads data from the text file and stores the
   * relevant synonyms into the verbSynonym HashMap
   * @param String filePath: directs the method to the correct text file for
   *                         reading data
   */
  public static void populateVerbSynonyms(String filePath){

    // Find synonyms in text file under each verb heading
    for(String verb : verbs){

      // Generate ArrayList of synonyms found under heading
      ArrayList<String> synonyms = readData(verb, filePath);

      // Convert from array list to array, for HashMap storage
      String[] synonymsArr = new String[synonyms.size()];
      synonymsArr = synonyms.toArray(synonymsArr);

      // Store in the HashMap
      verbSynonyms.put(verb, synonymsArr);

    }

  } // end populateVerbSynonyms()

  /**
   * populateObjects(): populates the list of objects used when parsing user
   *                    input and maps the object to all of it's synonyms.
   * @param String filePath: path to the file containing the list of all game
   *                         objects
   * @return none
   * @throws FileNotFoundException: when one or more of the provided filepaths
   *                                are not legitimate
   */
  public static void populateObjects(String filePath){

    try {

      File objectFile = new File(filePath);
      Scanner read = new Scanner(objectFile);

      String fileContents = "";
      String synonymData;
      String[] synonyms;
      String objWord;

      while(read.hasNext()){

        fileContents += read.next().toUpperCase() + " ";

      }

      read.close();

      Pattern p = Pattern.compile("(\\w+)\\s+\\{\\s+([^\\}]*)\\s+\\}");
      Matcher m = p.matcher(fileContents);

      while(m.find()){

        objWord = m.group(1);
        synonymData = m.group(2).trim();
        synonyms = synonymData.split(";");

        objects.put(objWord, synonyms);

      }

    } catch (FileNotFoundException e) {

      System.out.println("Object File not found");
      e.printStackTrace();

    }

  } // end populateObjects()

  /**
   * populatePrepositions(): Populates the list of prepositions, used for
   *                         parsing user input.
   * @param String filePath: Path to text file containing list of
   *                         preppositions.
   * @return none
   * @throws FileNotFoundException if filePath is invalid
   */
  public static void populatePrepositions(String filePath){

    try {

      File file = new File(filePath);
      Scanner read = new Scanner(file);
      String fileContents = "";


      while(read.hasNext()) {

        fileContents += read.next() + " ";

      }

      for(String word : fileContents.split(" ")) {
        prepositions.add(word);
      }

      read.close();

    } catch (FileNotFoundException e) {

      e.printStackTrace();

    }

  } // end populatePrepositions()

  /**
   * userInput(): takes raw text from user input and translates them into game
   *              commands for the rest of the program to use.
   * @param String input: raw input string as entered by the user
   * @returns String array with three elements; the first element is always the
   *          verb; the second element is the name of a legal game object, or
   *          null if there was no matching object; the third element is null if
   *          the second element is populated, and is the user input if the
   *          second element is null - this serves as a word memory allowing
   *          calling methods to access user input even if it is not related to
   *          a legal game object
   * @throws IllegalCommandException if string does not translate to a legal
   *         game command
   */
  public static String[] userInput(String input){

    String[] result = new String[3];
    String[] words = input.split("\\s+");


    // Loop parses out verbs and objects, ignorning all other words
    for(String word : words){

      if(isPreposition(word))
        continue;

      if(isDirection(word))
        result[0] = "GO";

      if(isVerb(word) && result[0] == null){

        result[0] = findBaseVerb(word).toUpperCase();
        continue;

      } else if (isVerb(word) && result[0] != null) {

        result[2] = findBaseVerb(word).toUpperCase();

      }

      if(isObject(word)){

        if(result[1] == null)
          result[1] = findBaseObject(word).toUpperCase();
        else if (result[1] != null && result[2] == null)
          result[2] = findBaseObject(word).toUpperCase();

      } else {

        if(result[2] == null)
          result[2] = word.toUpperCase();

      }

    }

    if(result[0] == null)
      throw new IllegalArgumentException("Unknown command");

    return result;

  } // end  userInput()

  /**
   * isPreposition(): tests whether or not a given word is a preposition
   * @return true if word is preposition; false if not
   * @param String word: word to be tested
   * @throws none
   */
  public static boolean isPreposition(String word){

    if(prepositions.contains(word))
      return true;

    return false;

  } // end isPreposition()

  /**
   * findBaseVerb(): returns the key to which a word is associated in the
   *                 synonyms hashmap.
   * @return String corresponding to a verb's base word
   * @param String word: the word to be associated with it's 'base' word (i.e.
   *                     the word it is a synonym of)
   * @throws IllegalArgumentException if the word has no base verb
   */
  private static String findBaseVerb(String word){

    if(Arrays.asList(verbs).contains(word))
      return word;

    for (String key : verbSynonyms.keySet()){

      for(String synonym : verbSynonyms.get(key)){

        if(synonym.trim().equals(word))
          return key;
      }

    }

    throw new IllegalArgumentException ("Verb not found");

  } // end findBaseVerb()

  /**
   * isVerb() returns true if a word passed to it apepars either in the list of
   * game verbs or is one of those verb's synonyms
   * @return boolean indicating a string's verb status
   * @param String word: word to be tested
   * @throws none
   */
  private static boolean isVerb(String word){

    if(Arrays.asList(verbs).contains(word))
      return true;

    for(String key : verbSynonyms.keySet()){

      for(String synonym : verbSynonyms.get(key)){

        if(synonym.trim().equals(word))
          return true;

      }
    }

    return false;

  } // end isVerb()

  /**
   * findBaseObject(): returns the key to which an object word is associated to
   *                 in the objectSynonyms hashmap.
   * @return String corresponding to the base object word for the given synonym.
   * @param String word: the object word that we wish to reduce to it's base
   * word
   * @throws IllegalArgumentException if the word is not a synonym of any game object
   */
  private static String findBaseObject(String word){

    for(String key : objects.keySet()){

      if(key.equals(word))
        return key;

      for(String synonym : objects.get(key)){

        if(synonym.trim().equals(word))
          return key;

      }

    }

    throw new IllegalArgumentException("Game object not found");

  } // end findBaseObject()

  /**
   * isObject() returns true if a word passed to it appears either in the list
   * of game objects or is one of those object's synonyms
   * @return boolean indicating a string's object status
   * @param String word: word to be tested
   * @throws none
   */
  private static boolean isObject(String word){

    for(String key : objects.keySet()) {

      if(key.equals(word))
        return true;

      for(String synonym : objects.get(key)) {

        if(synonym.trim().equals(word))
          return true;

      }

    }

    return false;

  } // end isObject()

  /**
   * isDirection: returns true if the word passed is NORTH, SOUTH, EAST, WEST,
   *              UP, or DOWN
   * @return boolean indicating whether input is a direction or not
   * @param Sting word: the word being tested
   * @throws none
   */
  public static boolean isDirection(String word){

    if(word.equals("NORTH") || word.equals("SOUTH") || word.equals("EAST") ||
       word.equals("WEST") || word.equals("UP") || word.equals("DOWN"))
      return true;

    if(word.equals("N") || word.equals("S") || word.equals("E") ||
       word.equals("W") || word.equals("U") || word.equals("D"))
      return true;

    return false;

  } // end isDirection

  /**
   * printAndFormat(): prints a string to the console, and formats it to not
   *                   exceed a specific width, inserting line breaks as
   *                   necssary.
   * @param String output: the string to be formatted
   * @return none
   * @throws none
   */
  public static void printAndFormat(String output){

    String[] words = output.split(" ");
    int charCounter = 0;
    int width = 120; // configure print width here
    String newLine = System.lineSeparator();

    for(String word : words){

      if(charCounter > width){

        System.out.print(newLine);
        charCounter = 0;

      }

      if(word.equals("BR")){
        System.out.println(newLine);
        charCounter = 0;
        continue;
      }

      charCounter += word.length() + 1; // add extra for space

      System.out.print(word.trim() + " ");

    }

    System.out.print(newLine);

  } // end printAndFormat

} // end class Parse
