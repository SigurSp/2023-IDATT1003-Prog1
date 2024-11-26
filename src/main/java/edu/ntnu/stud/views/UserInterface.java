package edu.ntnu.stud.views;

import edu.ntnu.stud.models.TrainDeparture;
import edu.ntnu.stud.models.TrainDepartureRegister;
import edu.ntnu.stud.utils.Utils;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The TrainDepartureRegister class manages a collection of train departures,
 * allowing operations such as registration, retrieval, and removal based on various criteria.
 *
 * <p>This class provides methods for registering train departures, checking for duplicates,
 * searching for departures by train number or destination, updating departure times, and
 * obtaining a sorted list of departures based on their departure times.
 *
 * @author SigurSp
 * @version 3.0
 * @since 2.5
 */
public class UserInterface {
  // Constants representing the different menu choices
  private static final int LIST_ALL_TRAINS = 1;
  private static final int ADD_TRAIN_DEPARTURE = 2;
  private static final int ASSIGN_TRACK_TRAIN_DEPARTURE = 3;
  private static final int ADD_DELAY_TRAIN_DEPARTURE = 4;
  private static final int SEARCH_TRAIN_DEPARTURE_TRAIN_NUMBER = 5;
  private static final int SEARCH_TRAIN_DEPARTURE_DESTINATION = 6;
  private static final int DELETE_TRAIN_DEPARTURE_NUMBER = 7;
  private static final int UPDATE_TIME_OF_DAY = 8;
  private static final int EXIT = 0;
  private final Utils utils = new Utils();
  private TrainDepartureRegister trainDepartureRegister;

  /**
   * Initializes the application by creating a TrainDepartureRegister.
   */
  public void init() {
    //Her legger du inn all kode som er nødvendig for å initialisere applikasjonen ved
    //oppstart, som f.eks. å opprette instansen av register-klassen.
    this.trainDepartureRegister = new TrainDepartureRegister();

    try {
      trainDepartureRegister.registerTrainDeparture(
          new TrainDeparture(LocalTime.of(12, 0), "L1", "23", "Trondheim"));
      trainDepartureRegister.registerTrainDeparture(
          new TrainDeparture(LocalTime.of(12, 20), "R3", "26", "Oslo S"));
      trainDepartureRegister.registerTrainDeparture(
          new TrainDeparture(LocalTime.of(12, 40), "B56", "25", "Stjørdal"));
      trainDepartureRegister.registerTrainDeparture(
          new TrainDeparture(LocalTime.of(13, 0), "C5", "64", "Røros"));
    } catch (Exception e) {
      System.out.println("One of the following Train departures could not be added to the register "
          + "for the following reason: \n"
          + e.getMessage());
    }
  }

  /**
   * Starts the application. This is the main loop of the application,
   * presenting the menu, retrieving the selected menu choice from the user,
   * and executing the selected functionality.
   */
  public void start() {
    boolean finished = false;
    while (!finished) {
      int menuChoice = utils.showMenu();
      switch (menuChoice) {
        case LIST_ALL_TRAINS:
          printAllTrains();
          break;
        case ADD_TRAIN_DEPARTURE:
          addTrainDeparture();
          break;

        case ASSIGN_TRACK_TRAIN_DEPARTURE:
          assignTrackTrainDeparture();
          break;

        case ADD_DELAY_TRAIN_DEPARTURE:
          addDelayTrainDeparture();
          break;

        case SEARCH_TRAIN_DEPARTURE_TRAIN_NUMBER:
          searchTrainDepartureTrainNumber();
          break;

        case SEARCH_TRAIN_DEPARTURE_DESTINATION:
          searchTrainDepartureDestination();
          break;

        case DELETE_TRAIN_DEPARTURE_NUMBER:
          deleteTrainDepartureTrainNumber();
          break;

        case UPDATE_TIME_OF_DAY:
          updateTimeOfDay();
          break;

        default:
          System.out.println("Unrecognized menu selected..");
          break;

        case EXIT:
          System.out.println("Thank you for using the Train Dispatch System!\n");
          finished = true;
          break;
      }
    }
  }

  /**
   * Prints all the train departures in the register in ascending order. If no trains are in the
   * register it prints that no trains were found.
   */
  private void printAllTrains() {
    if (trainDepartureRegister.getSortedTrainDepartures() == null) {
      System.out.println("|-----------------No train departure found-------------------------|");
    } else {
      System.out.println("Time now: " + trainDepartureRegister.getTime());
      utils.printHeader();
      printListTrainDeparture(trainDepartureRegister.getSortedTrainDepartures());
    }
  }

  /**
   * Asks the user for different parameters for a new train departure and adds it to the register.
   * If there is something wrong with the train departure the user gets printed the reason why it
   * could not be added to the register.
   */
  private void addTrainDeparture() {
    System.out.println(trainDepartureRegister.getTrainNumbers()
        + ", what is the new train number: ");
    String trainNumber = utils.inputString();
    while (trainDepartureRegister.checkTrainNumber(trainNumber)) {
      System.out.println("Invalid " + trainNumber + " already exists!");
      trainNumber = utils.inputString();
    }
    System.out.println("What time does the train depart?");
    LocalTime departureTime = utils.inputLocalTime();
    while (departureTime.isBefore(trainDepartureRegister.getTime().plusMinutes(1))) {
      System.out.println("Can't set the time before "
          + trainDepartureRegister.getTime().plusMinutes(1));
      departureTime = utils.inputLocalTime();
    }
    System.out.println("Destination: ");
    String destination = utils.inputString();
    System.out.println("What line is the train");
    String line = utils.inputString();
    try {
      trainDepartureRegister.registerTrainDeparture(
          new TrainDeparture(departureTime, line, trainNumber, destination));
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Method to search up a train departure and assign it a track. Track has to be equal or more than
   * 0. If the track can not be assigned the user is prompted the reason why.
   */
  private void assignTrackTrainDeparture() {
    if (checkTrainDepartureRegisterEmpty()) {
      return;
    }
    System.out.println(trainDepartureRegister.getTrainNumbers()
        + ", which train do you want to assign a track: ");
    String trainNumber = checkTrainDepartureNumber();
    System.out.println("What track do you want to assign to " + trainNumber + ": ");
    int trainTrack = utils.inputInt();
    try {
      trainDepartureRegister.getTrainDepartureNumber(trainNumber).setTrack(trainTrack);
      System.out.println(trainNumber + " has now been assigned track " + trainTrack);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Method to search up a train departure and assign it a delay.
   */
  private void addDelayTrainDeparture() {
    if (checkTrainDepartureRegisterEmpty()) {
      return;
    }
    System.out.println(trainDepartureRegister.getTrainNumbers()
        + ", what train do you want to add a delay to?");
    String trainNumber = checkTrainDepartureNumber();
    System.out.println("What delay do you want to assign to " + trainNumber + ": ");
    LocalTime trainDelay = utils.inputLocalTime();
    trainDepartureRegister.getTrainDepartureNumber(trainNumber).setDelay(trainDelay);
    System.out.println(trainNumber + " has now been delayed with " + trainDelay);
  }

  /**
   * Method to search up train departure with a unique train number. If the train is found it is
   * printed to the console.
   */
  private void searchTrainDepartureTrainNumber() {
    if (checkTrainDepartureRegisterEmpty()) {
      return;
    }
    System.out.println(trainDepartureRegister.getTrainNumbers()
        + ", what train number do you want to check: ");
    String trainNumber = checkTrainDepartureNumber();
    utils.printHeader();
    printTrainDeparture(trainDepartureRegister.getTrainDepartureNumber(trainNumber));
  }

  /**
   * Method to search up train departure going to a destination. Trains going to this destination is
   * printed to the console. If no train is found it gets printed to the console.
   */
  private void searchTrainDepartureDestination() {
    if (checkTrainDepartureRegisterEmpty()) {
      return;
    }
    System.out.println("What destination do you want to check: ");
    String trainDestination = utils.inputString();
    if (!trainDepartureRegister.checkTrainDestination(trainDestination)) {
      System.out.println("|--------------No trains going to this destination-----------------|");
    } else {
      utils.printHeader();
      printListTrainDeparture(
          trainDepartureRegister.getTrainDeparturesDestination(trainDestination));
    }
  }

  /**
   * Method to deleted train departure by giving a train number.
   */
  private void deleteTrainDepartureTrainNumber() {
    if (checkTrainDepartureRegisterEmpty()) {
      return;
    }
    System.out.println(trainDepartureRegister.getTrainNumbers()
        + ", What train do you want to delete?");
    String trainNumber = checkTrainDepartureNumber();
    trainDepartureRegister.removeTrainDepartureNumber(trainNumber);
  }

  /**
   * Method to update time in the train departure register. Time cant be set to earlier this day.
   */
  private void updateTimeOfDay() {
    System.out.println("What time is it now: ");
    try {
      LocalTime timeNow = utils.inputLocalTime();
      trainDepartureRegister.updateTimeTrainDepartureRegister(timeNow);
    } catch (IllegalArgumentException e) {
      System.out.println("Time can't be before " + trainDepartureRegister.getTime());
    }
  }

  /**
   * Method to select train departure by inputting a train number. If no train is found the user is
   * prompted to enter a new number.
   *
   * @return String of trainNumber in the register.
   */
  private String checkTrainDepartureNumber() {
    String trainNumber = utils.inputString();
    while (!trainDepartureRegister.checkTrainNumber(trainNumber)) {
      System.out.println("Train not found, try again: ");
      trainNumber = utils.inputString();
    }
    return trainNumber;
  }

  /**
   * checks if there is any trains in the register. If the register is empty a message is printed
   * to the user.
   *
   * @return boolean true if TrainDepartureRegister is empty, else false.
   */
  private boolean checkTrainDepartureRegisterEmpty() {
    if (trainDepartureRegister.getSortedTrainDepartures() == null) {
      System.out.println("|-----------------No train departure found-------------------------|");
      return true;
    } else {
      return false;
    }
  }

  /**
   * Prints out a single train departure with all its attributes.
   *
   * @param train Train departure to be printed.
   */
  public void printTrainDeparture(TrainDeparture train) {
    System.out.println(train.printTrainDeparture()
    );
  }

  /**
   * Prints out a list of train departures by using the method.
   * {@link #printTrainDeparture(TrainDeparture)}
   *
   * @param trainDeparture Arraylist of train departure to be printed.
   */
  public void printListTrainDeparture(ArrayList<TrainDeparture> trainDeparture) {
    trainDeparture.forEach(this::printTrainDeparture);
  }

}