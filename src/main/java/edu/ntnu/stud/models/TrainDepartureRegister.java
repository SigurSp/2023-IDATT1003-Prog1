package edu.ntnu.stud.models;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * The TrainDepartureRegister class manages a collection of train departures,
 * allowing operations such as registration, retrieval, and removal based on various criteria.
 *
 * <p>This class provides methods for registering train departures, checking for duplicates,
 * searching for departures by train number or destination, updating departure times, deleting
 * trains and obtaining a sorted list of departures based on their departure times.
 *
 * @author SigurSp
 * @version 3.0
 * @since 0.2
 */
public class TrainDepartureRegister {
  private final ArrayList<TrainDeparture> trainDepartures = new ArrayList<>();
  private LocalTime time = LocalTime.of(0, 0);

  /**
   * Registers a train in the register.
   *
   * @param trainDeparture The train departure to be registered.
   * @throws IllegalArgumentException If a train with the same already exists or if the departure
   *                                  time is before the current time.
   */
  public void registerTrainDeparture(TrainDeparture trainDeparture)
      throws IllegalArgumentException {
    if (trainDepartures.contains(trainDeparture)) {
      throw new IllegalArgumentException("The train could not be registered."
          + " Since the register already contains a train with matching number");
    } else if (trainDeparture.getDepartureTime().isBefore(time.plusMinutes(1))) {
      throw new IllegalArgumentException("The train could not be added to the register."
          + " Since its departure time is before " + time.plusMinutes(1));
    } else {
      try {
        trainDepartures.add(trainDeparture);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
  }

  /**
   * Checks if a train with the given train number is already in the register.
   *
   * @param trainNumber The train number to be checked.
   * @return True if a train with the given number exists. Otherwise, false.
   */
  public boolean checkTrainNumber(String trainNumber) {
    return trainDepartures.stream().anyMatch(e -> e.getTrainNumber().equals(trainNumber));
  }

  /**
   * Checks if a train with the given destination is in the register.
   *
   * @param trainDestination The destination to check.
   * @return True if there is a train departure with the destination. Otherwise, false.
   */
  public boolean checkTrainDestination(String trainDestination) {
    return trainDepartures.stream().anyMatch(e -> e.getDestination().equals(trainDestination));
  }

  /**
   * Retrieves a train departure based on the unique train number.
   *
   * @param trainNumber The train number to be retrieved.
   * @return The train departure with the corresponding train number.
   */
  public TrainDeparture getTrainDepartureNumber(String trainNumber) {
    return trainDepartures.stream()
        .filter(departure -> departure.getTrainNumber().equals(trainNumber))
        .findFirst()
        .orElse(null);
  }

  /**
   * Retrieves a list of the train departures going to the given destination.
   *
   * @param destination the destination to be searched for.
   * @return A list of train departures with the specific destination.
   */
  public ArrayList<TrainDeparture> getTrainDeparturesDestination(String destination) {
    sortTrainDepartures();
    return trainDepartures.stream().filter(train ->
        train.getDestination().equals(destination)).collect(
        Collectors.toCollection(ArrayList::new));
  }

  /**
   * Retrieves a string containing the train numbers in the register.
   *
   * @return String containing train numbers.
   */
  public String getTrainNumbers() {
    return "The register contains the following train numbers: \n"
        + trainDepartures.stream().sorted(Comparator.comparing(TrainDeparture::getTrainNumber))
        .map(TrainDeparture::getTrainNumber).toList();
  }

  /**
   * Updates the current time for the register and removes departed trains.
   *
   * @param timeNow The current time.
   * @throws IllegalArgumentException If the provided time is before the current time.
   */
  public void updateTimeTrainDepartureRegister(LocalTime timeNow) throws IllegalArgumentException {
    if (timeNow.isBefore(time)) {
      throw new IllegalArgumentException("Time must be before " + time.toString());
    } else {
      time = timeNow;
      removeDepartedTrains();
    }
  }

  /**
   * Gets the current time.
   *
   * @return The current time.
   */
  public LocalTime getTime() {
    return time;
  }

  /**
   * Removes departed trains with departure time earlier than the current time in the register.
   */
  private void removeDepartedTrains() {
    trainDepartures.removeIf(
        departure -> departure.getDepartureTime()
            .plusHours(departure.getDelay().getHour())
            .plusMinutes(departure.getDelay().getMinute())
            .isBefore(time.plusMinutes(1)));
  }

  /**
   * Removes a train departure if the train number matches the unique train number in the register.
   *
   * @param trainNumber The train number to be removed.
   */
  public void removeTrainDepartureNumber(String trainNumber) {
    trainDepartures.removeIf(
        departure -> departure.getTrainNumber().equals(trainNumber)
    );
  }

  /**
   * Retrieves a sorted list of train departures ascending from departure time.
   *
   * @return List of sorted train departures.
   */
  public ArrayList<TrainDeparture> getSortedTrainDepartures() {
    if (trainDepartures.isEmpty()) {
      return null;
    }
    sortTrainDepartures();
    return trainDepartures;
  }

  /**
   * Sorts the train departures based on their departure time.
   */
  private void sortTrainDepartures() {
    if (trainDepartures.isEmpty()) {
      return;
    }
    trainDepartures.sort(Comparator.comparing(TrainDeparture::getDepartureTime));
  }

  /**
   * Returns a string representation of the train departures in the register.
   *
   * @return String representing the content of the register.
   */
  @Override
  public String toString() {
    sortTrainDepartures();
    return "The register contains the following train departures: \n"
        + String.join("\n", trainDepartures.stream().map(TrainDeparture::toString).toList());
  }
}
