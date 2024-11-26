package edu.ntnu.stud.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TestTrainDeparture {

  TrainDeparture trainDeparture;


  /**
   * Makes an instance of TrainDeparture with test values
   */
  @BeforeEach
  public void setup() {
    trainDeparture = new TrainDeparture(LocalTime.of(0, 0), "L1",
        "01", "Trondheim");

  }

  @Nested
  @DisplayName("Negative tests for TrainDeparture, throws exceptions on wrong input parameters")
  public class methodsThrowsExceptions {

    @Test
    @DisplayName("Constructor throws Ill.Arg.Exc. on blank line")
    public void trainDepartureThrowsOnNullDepartureTime() {
      try {
        new TrainDeparture(null, "L1", "01", "Trondheim");
        fail(
            "The test trainDepartureThrowsOnNullDepartureTime failed since it did not throw"
                + " Ill.Arg.Exc as expected");
      } catch (IllegalArgumentException e) {
        assertEquals("The time can not be null", e.getMessage());
      }
    }

    @Test
    @DisplayName("Constructor throws Ill.Arg.Exc. on blank line")
    public void trainDepartureThrowsOnBlankLine() {
      try {
        new TrainDeparture(LocalTime.of(0, 0), "", "01",
            "Trondheim");
        fail("The test trainDepartureThrowsOnBlankString failed since it did not throw Ill.Arg.Exc "
            + "as expected");
      } catch (IllegalArgumentException e) {
        assertEquals("The string for the parameter "
            + "Line" + " was a blank string, try to register again", e.getMessage());
      }
    }

    @Test
    @DisplayName("Constructor throws Ill.Arg.Exc. on blank train number")
    public void trainDepartureThrowsOnBlankTrainNumber() {
      try {
        new TrainDeparture(LocalTime.of(0, 0), "L1", "",
            "Trondheim");
        fail(
            "The test trainDepartureThrowsOnBlankTrainNumber failed since it did not throw "
                + "Ill.Arg.Exc as expected");
      } catch (IllegalArgumentException e) {
        assertEquals("The string for the parameter "
            + "Train number" + " was a blank string, try to register again", e.getMessage());
      }
    }

    @Test
    @DisplayName("Constructor throws Ill.Arg.Exc. on blank destination")
    public void trainDepartureThrowsOnBlankDestination() {
      try {
        new TrainDeparture(LocalTime.of(0, 0), "L1", "01", "");
        fail(
            "The test trainDepartureThrowsOnBlankDestination failed since it did not throw"
                + " Ill.Arg.Exc as expected");
      } catch (IllegalArgumentException e) {
        assertEquals("The string for the parameter "
            + "Destination" + " was a blank string, try to register again", e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDispatch-setter for Track throws Ill.Arg.Exc.")
    public void trainDepartureSetterTrackThrows() {
      try {
        trainDeparture.setTrack(-20);
        fail("The test trainDepartureSetterTrackThrows failed since it did not throw Ill.Arg.Exc "
            + "as expected");
      } catch (IllegalArgumentException e) {
        assertEquals("Track cant be less than 0", e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDispatch-setter for Track throws Ill.Arg.Exc.")
    public void trainDepartureDelaySetterThrows() {
      try {
        trainDeparture.setDelay(null);
        fail("The test trainDepartureDelaySetterThrows failed since it did not throw Ill.Arg.Exc "
            + "as expected");
      } catch (IllegalArgumentException e) {
        assertEquals("The time can not be null", e.getMessage());
      }
    }

  }

  @Nested
  @DisplayName("Positive tests for TrainDeparture")
  public class methodsDoesNotThrowException {

    @Test
    @DisplayName("TrainDeparture constructor creates an instance of TrainDeparture "
        + "without throwing Ill.Arg.Exc.")
    public void trainDepartureDoesntThrowOnNewTrainDeparture() {
      try {
        TrainDeparture testTrainDeparture = new TrainDeparture(
            LocalTime.of(0, 0), "L1", "01", "Trondheim");
        assertNotNull(testTrainDeparture);
      } catch (IllegalArgumentException e) {
        fail("The test trainDepartureDoesntThrowOnNewTrainDeparture failed with the exception "
            + "message " + e.getMessage());
        assertEquals("The time can not be null", e.getMessage());
      }
    }

    @Test
    @DisplayName("Getter methods return the expected value without throwing Ill.Arg.Exc.")
    public void evaluateTrainDepartureGetter() {
      try {
        assertEquals(LocalTime.of(0, 0), trainDeparture.getDelay());
        assertEquals("01", trainDeparture.getTrainNumber());
        assertEquals(-1, trainDeparture.getTrack());
        assertEquals("L1", trainDeparture.getLine());
        assertEquals("Trondheim", trainDeparture.getDestination());
        assertEquals(LocalTime.of(0, 0), trainDeparture.getDepartureTime());
      } catch (IllegalArgumentException e) {
        fail("The test failed with the exception message " + e.getMessage());
      }
    }

    @Test
    @DisplayName("Set method for delay sets the correct value to TrainDeparture")
    public void testSetDelay() {
      try {
        trainDeparture.setDelay(LocalTime.of(1, 10));
        assertEquals(LocalTime.of(1, 10), trainDeparture.getDelay());
      } catch (IllegalArgumentException e) {
        fail("The test failed with the exception message " + e.getMessage());
      }
    }

    @Test
    @DisplayName("Set method for Destination sets the correct value to TrainDeparture")
    public void testSet() {
      try {
        trainDeparture.setDelay(LocalTime.of(1, 10));
        assertEquals(LocalTime.of(1, 10), trainDeparture.getDelay());
      } catch (IllegalArgumentException e) {
        fail("The test failed with the exception message " + e.getMessage());
      }
    }

    @Test
    @DisplayName("Set method for track sets the correct value to TrainDeparture")
    public void testSetValues() {
      try {
        trainDeparture.setTrack(23);
        assertEquals(23, trainDeparture.getTrack());
      } catch (IllegalArgumentException e) {
        fail("The test failed with the exception message " + e.getMessage());
      }
    }

    @Test
    @DisplayName("printTrainDeparture returns string as expected")
    public void printTrainDepartureReturnsCorrectly() {
      try {
        assertEquals("| 00:00          | L1   | 01     | Trondheim       |       |       |"
          ,trainDeparture.printTrainDeparture());
      } catch (IllegalArgumentException e) {
        fail("The printTrainDeparture method did not return the expected string");
      }
    }

    @Test
    @DisplayName("TrainDeparture.ToString prints the train departure as expected")
    public void TrainDepartureToStringPrintsCorrectly() {
      try {
        assertEquals("TrainDeparture{trainNumber= 01', departureTime= 00:00, line= L1', " +
                "destination= Trondheim', track= -1, delay= 00:00}"
            ,trainDeparture.toString());
      } catch (IllegalArgumentException e) {
        fail("The toString method for the train departure did not print the expected string");
      }
    }
  }
}
