package edu.ntnu.stud.models;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Class describing a train departure som a station with information such as departure time,
 * train number, line, destination, track, and delay.
 *
 * <p>Contains a constructor to create a train departure. Getter for each parameter and setter for
 * line, track and delay.
 *
 * @author SigurSp
 * @version 3.0
 * @since 0.1
 */
public class TrainDeparture {
  private final String trainNumber;
  private final LocalTime departureTime;
  private final String line;
  private final String destination;
  private int track;
  private LocalTime delay;

  /**
   * Creates an instance of TrainDeparture with track = -1.
   * Object is only created once it is verified that all inputted Strings are legal by performing
   * the method
   * {@link #verifyStringParameter(String, String)}
   * {@link #verifyLocalTime(LocalTime)}
   *
   * @param departureTime is a LocalTime representing the departure time of the train.
   * @param trainNumber   is a String representing the unique train departure number.
   * @param destination   is a String representing the end destination of the train departure.
   * @throws IllegalArgumentException if any of the String inputs are blank.
   */
  public TrainDeparture(LocalTime departureTime, String line, String trainNumber,
                        String destination) {
    verifyLocalTime(departureTime);
    verifyStringParameter(line, "Line");
    verifyStringParameter(trainNumber, "Train number");
    verifyStringParameter(destination, "Destination");
    this.departureTime = departureTime;
    this.line = line;
    this.trainNumber = trainNumber;
    this.destination = destination;
    this.track = -1;
    this.delay = LocalTime.of(0, 0);
  }

  /**
   * Verifies that a String contains information. The check fails is the String parameter is blank.
   *
   * @param parameter     String to be verified
   * @param parameterName is the name of the String being checked.
   * @throws IllegalArgumentException if the String parameter is either "" or only whitespace,
   this will be considered illegal.
   */
  private void verifyStringParameter(String parameter, String parameterName)
      throws IllegalArgumentException {
    if (parameter.isBlank()) {
      throw new IllegalArgumentException("The string for the parameter "
          + parameterName + " was a blank string, try to register again");
    }
  }

  /**
   * Verifies that the LocalTime contains a value. The check will fail if localtime is null.
   *
   * @param localTime the LocalTime to be checked
   * @throws IllegalArgumentException if the LocalTime is null.
   */
  private void verifyLocalTime(LocalTime localTime)
      throws IllegalArgumentException {
    if (localTime == null) {
      throw new IllegalArgumentException("The time can not be null");
    }
  }

  /**
   * Return the LocalTime representing the time the train departs.
   *
   * @return LocalTime of the train departing.
   */
  public LocalTime getDepartureTime() {
    return departureTime;
  }

  /**
   * Returns the String representing the line the train travels.
   *
   * @return a String with a combination of integers and letters.
   */
  public String getLine() {
    return line;
  }

  /**
   * Returns a String representing the unique train number.
   *
   * @return String consisting of integer and letters.
   */
  public String getTrainNumber() {
    return trainNumber;
  }

  /**
   * Returns a String representing the destination of the train.
   *
   * @return String with destination name.
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Returns an Integer representing the track which the train will depart from.
   *
   * @return Integer representing track number.
   */
  public int getTrack() {
    return track;
  }

  /**
   * Sets an Integer as the new track the train will depart from.
   *
   * @param track is an integer value.
   */
  public void setTrack(int track) {
    if (track < 0) {
      throw new IllegalArgumentException("Track cant be less than 0");
    }
    this.track = track;
  }

  /**
   * Returns LocalTime representing how long the train is delayed.
   *
   * @return LocalTime.
   */
  public LocalTime getDelay() {
    return delay;
  }

  /**
   * Sets a LocalTime as the new time the train is delayed.
   *
   * @param delay is a LocalTime value more than 0.
   */
  public void setDelay(LocalTime delay) {
    verifyLocalTime(delay);
    this.delay = delay;
  }

  /**
   * Checks if there is a delay, else it returns an empty string.
   *
   * @return String with delay if delay has been set. else an empty string is returned.
   */
  private String delayToString() {
    if (this.delay.equals(LocalTime.of(0, 0))) {
      return " ";
    } else {
      return this.delay.toString();
    }
  }

  /**
   * Converts the track information to a string representation.
   *
   * @return An empty string if no track has been assigned, otherwise the track as a string.
   */
  private String trackToString() {
    if (this.track == -1) {
      return " ";
    } else {
      return Integer.toString(track);
    }
  }

  /**
   * Returns a formatted string representing the train departure information for end-users.
   *
   * @return A formatted string with departure time, line, train number, destination, track,
   and delay.
   */
  public String printTrainDeparture() {
    return String.format("| %-14.14s | %-4.4s | %-6.6s | %-15.15s | %-5.5s | %-5.5s |",
        departureTime, line, trainNumber, destination, trackToString(), delayToString());
  }

  /**
   * Prints a string that represent the train departure object with all its attribute information.
   *
   * @return a string that represent the train departure information.
   */
  @Override
  public String toString() {
    return "TrainDeparture{"
        + "trainNumber= " + trainNumber + '\''
        + ", departureTime= " + departureTime
        + ", line= " + line + '\''
        + ", destination= " + destination + '\''
        + ", track= " + track
        + ", delay= " + delay
        + '}';
  }

  /**
   * Indicates whether another object is "equal to" this one.
   * Equality is based on the equality of the train numbers.
   *
   * @param o The reference object with which to compare.
   * @return true if this object is the same as the o argument, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TrainDeparture that = (TrainDeparture) o;
    return Objects.equals(trainNumber, that.trainNumber);
  }

  /**
   * Returns a hash code value for the object. This implementation uses the hash code of the train
   * number.
   *
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(trainNumber);
  }
}




