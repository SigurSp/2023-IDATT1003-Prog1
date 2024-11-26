package edu.ntnu.stud.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Utility class with methods that utilize the java.util.Scanner to receive user input from
 * the terminal. The class also contains often printed string for the user menu.
 *
 * @author SigurSp
 * @version 3.0
 * @since 2.5
 */
public class Utils {

  /**
   * Scans for user int input. If the input does not contain a valid int the user is asked to
   * enter a new number.
   *
   * @return Int which the user has inputted.
   */
  public int inputInt() {
    Scanner in = new Scanner(System.in);
    while (!in.hasNextInt()) {
      System.out.println("Invalid number, try again");
      in.next();
    }
    return in.nextInt();
  }

  /**
   * Scans for user String input. If the input does not contain a string the user is asked to enter
   * a new String.
   *
   * @return String that the user has inputted
   */
  public String inputString() {
    Scanner in = new Scanner(System.in);
    while (!in.hasNextLine()) {
      System.out.println("Invalid input");
      in.nextLine();
    }
    return in.nextLine();
  }

  /**
   * Scans for user LocalTime input. If the input has the wrong format or out of range the user is
   * asked to enter a new time.
   *
   * @return LocalTime which the user has inputted.
   */
  public LocalTime inputLocalTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    System.out.println("Enter time HH:mm");
    Scanner scanner = new Scanner(System.in);
    String userInput = scanner.next();
    LocalTime localTime = null;
    while (localTime == null) {
      try {
        localTime = LocalTime.parse(userInput, formatter);
      } catch (Exception e) {
        System.out.println("Invalid input format. Please enter time in HH:mm format.");
        userInput = scanner.next();
      }
    }
    return localTime;
  }

  /**
   * Shows the user menu and asks for user to input a number to choose a menu option. If the
   * inputted number is out of range or a String the user is asked to enter a new number.
   *
   * @return Int representing user menu choice.
   */
  public int showMenu() {
    int menuChoice = -1;
    printMenu();
    Scanner sc = new Scanner(System.in);
    if (sc.hasNextInt()) {
      menuChoice = sc.nextInt();
    } else {
      System.out.println("You must enter a number, not text");
    }
    return menuChoice;
  }

  /**
   * Prints the header for the different train departure categories.
   */
  public void printHeader() {
    System.out.println("""
        |--------------------Train departure found-------------------------|
        |-Departure Time-|-Line-|-Number-|---Destination---|-Track | Delay-|""");
  }

  /**
   * Prints the menu with user options.
   */
  private void printMenu() {
    System.out.println("""

        ***** Train Dispatch System Application v3.0 *****
        1. List all Train Departures
        2. Add Train Departure
        3. Assign track to train departure
        4. Add delay to train departure
        5. Search train departure by train number
        6. Search train departure by destination
        7. Delete train departure
        8. Update time of day
        0. Quit
        
         Please enter a number between 0 and 8.""");
  }
}
