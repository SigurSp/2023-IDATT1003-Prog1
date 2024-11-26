package edu.ntnu.stud;

import edu.ntnu.stud.views.UserInterface;


/**
 * Wrapper class for the static main method to run the train dispatch application.
 */
public class TrainDispatchApp {
  /**
   * Main method that runs the application.
   *
   * @param args are the arguments for the main method.
   */
  public static void main(String[] args) {
    UserInterface userInterface = new UserInterface();
    userInterface.init();
    userInterface.start();
  }
}
