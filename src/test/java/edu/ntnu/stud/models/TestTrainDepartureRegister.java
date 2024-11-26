package edu.ntnu.stud.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TestTrainDepartureRegister {

  TrainDepartureRegister trainDepartureRegister;

  /**
   * Makes an instance of TrainDepartureRegister and registers a TrainDeparture before each test.
   */
  @BeforeEach
  public void setup() {
    trainDepartureRegister = new TrainDepartureRegister();
    trainDepartureRegister.registerTrainDeparture(
        new TrainDeparture(LocalTime.of(12, 0), "L1",
            "01", "Trondheim"));
  }

  @Nested
  @DisplayName("Negative test for the TrainDepartureRegister" +
      ", throws exceptions on wrong input parameters")
  public class methodThrowsExceptions {
    @Test
    @DisplayName("TrainDepartureRegister.registerTrainDeparture() throws Ill.Arg.Exc. on blank "
        + "departureTime")
    void registerTrainDepartureThrowsOnBlankDepartureTime() {
      try {
        trainDepartureRegister.registerTrainDeparture(
            new TrainDeparture(null, "L1", "01", "Trondheim"));
        fail("The method registerTrainDepartureThrowsOnBlankDepartureTime did not throw on blank "
            + "departureTime");
      } catch (IllegalArgumentException e) {
        assertEquals("The time can not be null", e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.registerTrainDeparture() throws Ill.Arg.Exc. on blank "
        + "line")
    void registerTrainDepartureThrowsOnBlankLine() {
      try {
        trainDepartureRegister.registerTrainDeparture(
            new TrainDeparture(LocalTime.of(10, 0), "", "01",
                "Trondheim"));
        fail("The method registerTrainDepartureThrowsOnBlankLine did not throw on blank "
            + "line");
      } catch (IllegalArgumentException e) {
        assertEquals(
            "The string for the parameter Line was a blank string, try to register again",
            e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.registerTrainDeparture() throws Ill.Arg.Exc. on blank "
        + "trainNumber")
    void registerTrainDepartureThrowsOnBlankTrainNumber() {
      try {
        trainDepartureRegister.registerTrainDeparture(
            new TrainDeparture(LocalTime.of(10, 0), "L1", "",
                "Trondheim"));
        fail("The method registerTrainDepartureThrowsOnBlankTrainNumber did not throw on blank "
            + "trainNumber");
      } catch (IllegalArgumentException e) {
        assertEquals(
            "The string for the parameter Train number was a blank string, try to "
                + "register again",
            e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.registerTrainDeparture() throws Ill.Arg.Exc. on blank "
        + "destination")
    void registerTrainDepartureThrowsOnBlankDestination() {
      try {
        trainDepartureRegister.registerTrainDeparture(
            new TrainDeparture(LocalTime.of(10, 0), "L1", "01",
                ""));
        fail("The method registerTrainDepartureThrowsOnBlankDestination did not throw on blank "
            + "destination");
      } catch (IllegalArgumentException e) {
        assertEquals(
            "The string for the parameter Destination was a blank string, try to "
                + "register again",
            e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.registerTrainDeparture() throws Ill.Arg.Exc. on duplicate "
        + "trainNumber")
    void registerTrainDepartureThrowsOnDuplicateTrainNumber() {
      try {
        trainDepartureRegister.registerTrainDeparture(
            new TrainDeparture(LocalTime.of(10, 0), "L1", "01",
                "Trondheim"));
        trainDepartureRegister.registerTrainDeparture(
            new TrainDeparture(LocalTime.of(10, 0), "L1", "01",
                "Trondheim"));
        fail(
            "The method registerTrainDepartureThrowsOnDuplicateTrainNumber did not throw on "
                + "duplicate trainNumber");
      } catch (IllegalArgumentException e) {
        assertEquals(
            "The train could not be registered. Since the register already contains a "
                + "train with matching number",
            e.getMessage());
      }
    }

    @Test
    @DisplayName(
        "TrainDepartureRegister.registerTrainDeparture() throws Ill.Arg.Exc. if departureTime "
            + "is before trainDepartureRegister.getTime")
    void registerTrainDepartureThrowsOnTime() {
      try {
        trainDepartureRegister.updateTimeTrainDepartureRegister(LocalTime.of(12, 0));
        trainDepartureRegister.registerTrainDeparture(
            new TrainDeparture(LocalTime.of(8, 0), "L1", "1",
                "Trondheim"));
        fail("The method registerTrainDepartureThrowsOnTime did not throw on blank "
            + "trainNumber");
      } catch (IllegalArgumentException e) {
        assertEquals(
            "The train could not be added to the register. Since its departure time is"
                + " before 12:01",
            e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.updateTimeTrainDepartureRegister() throws Ill.Arg.Exc. If "
        + "user tries to set time before timeNow")
    void updateTimeTrainDepartureRegisterThrowsOnTimeTooEarly() {
      try {
        trainDepartureRegister.updateTimeTrainDepartureRegister(LocalTime.of(12, 0));
        trainDepartureRegister.updateTimeTrainDepartureRegister(LocalTime.of(0, 0));
        fail("The method updateTimeTrainDepartureRegister did not throw on time being before "
            + "timeNow");
      } catch (IllegalArgumentException e) {
        assertEquals(
            "Time must be before 12:00", e.getMessage());
      }
    }
  }

  @Nested
  @DisplayName("Positive tests for TrainDepartureRegister")
  public class methodsDoesNotThrowException {

    @Test
    @DisplayName("TrainDeparture.checkTrainNumber() returns true when finding the train")
    public void TrainDepartureRegisterCheckTrainNumberReturnsTrue() {
      try{
        assertTrue(trainDepartureRegister.checkTrainNumber("01"), "Test passed");
      }catch (IllegalArgumentException e){
        fail("The test TrainDepartureRegisterCheckTrainNumberReturnsTrue() failed with the"
            + "exception message" + e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDeparture.checkTrainDestination() returns true when finding the train")
    public void TrainDepartureRegisterCheckTrainDestinationReturnsTrue() {
      try{
        assertTrue(trainDepartureRegister.checkTrainDestination("Trondheim"),
            "Test passed");
      }catch (IllegalArgumentException e){
        fail("The test TrainDepartureRegisterCheckTrainDestinationReturnsTrue() failed with the"
            + "exception message" + e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.getTrainDepartureNumber() returns the correct train")
    public void TrainDepartureRegisterGetTrainDepartureNumber() {
      try{
        assertEquals(trainDepartureRegister.getTrainDepartureNumber("01"),
            new TrainDeparture(LocalTime.of(0, 0), "L1", "01",
                "Trondheim"));
      }catch (IllegalArgumentException e){
        fail("The test TrainDepartureRegisterGetTrainDepartureNumber() failed with the"
            + "exception message" + e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.getTrainNumbers() returns the correct train numbers")
    public void TrainDepartureRegisterGetTrainNumbers() {
      try{
        String output = "The register contains the following train numbers: \n"
            + "[01]";
        assertEquals(trainDepartureRegister.getTrainNumbers(),output);
      }catch (IllegalArgumentException e){
        fail("The test TrainDepartureRegisterGetTrainNumbers() failed with the"
            + "exception message" + e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.updateTimeTrainDepartureRegister() updates the time of the"
        + " register. And .getTime() returns the correct time")
    public void TrainDepartureRegisterUpdateTimeAndGetTime() {
      try{
        trainDepartureRegister.updateTimeTrainDepartureRegister(
            LocalTime.of(12,12));
        assertEquals(trainDepartureRegister.getTime(), LocalTime.of(12,12));
      }catch (IllegalArgumentException e){
        fail("The test TrainDepartureRegisterUpdateTimeAndGetTime() failed with the"
            + "exception message" + e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.removeDepartedTrains() updates the time of the "
        + "register. And checks that the train leaving at 00.00 is gone")
    public void TrainDepartureRegisterRemoveDepartedTrains() {
      try{
        trainDepartureRegister.updateTimeTrainDepartureRegister(
            LocalTime.of(13,0));
        assertFalse(trainDepartureRegister.checkTrainNumber("01"));
      }catch (IllegalArgumentException e){
        fail("The test TrainDepartureRegister.removeDepartedTrains() failed with the"
            + "exception message" + e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.removeTrainDepartureNumber() removes the train with a "
        + "number. And checks that the train is gone from the register")
    public void TrainDepartureRegisterRemoveTrainDepartureNumber() {
      try{
        trainDepartureRegister.removeTrainDepartureNumber("01");
        assertFalse(trainDepartureRegister.checkTrainNumber("01"));
      }catch (IllegalArgumentException e){
        fail("The test TrainDepartureRegisterRemoveTrainDepartureNumber() failed with the"
            + "exception message" + e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.getSortedTrainDepartures() sorts the trains by departure "
        + " time, then returns an Arraylist with all trainDepartures")
    public void TrainDepartureRegisterGetSortedTrainDepartures() {
      try{
        ArrayList<TrainDeparture> trainDepartureRegisterClone = new ArrayList<>();
        trainDepartureRegisterClone.add(
            trainDepartureRegister.getTrainDepartureNumber("01"));
        TrainDeparture train02 = new TrainDeparture(
            LocalTime.of(14,0), "L2", "02", "Trondheim2");
        TrainDeparture train03 = new TrainDeparture(
            LocalTime.of(15,0), "L2", "03", "Trondheim3");
        TrainDeparture train04 = new TrainDeparture(
            LocalTime.of(13,0), "L2", "04", "Trondheim4");
        //Adds Train Departure to the register with in the wrong departure time order.
        trainDepartureRegister.registerTrainDeparture(train02);
        trainDepartureRegister.registerTrainDeparture(train03);
        trainDepartureRegister.registerTrainDeparture(train04);
        //Adds Train Departure to the clone in the sorted departure timer order.
        trainDepartureRegisterClone.add(train04);
        trainDepartureRegisterClone.add(train02);
        trainDepartureRegisterClone.add(train03);
        assertEquals(trainDepartureRegister.getSortedTrainDepartures(),
            trainDepartureRegisterClone);
      }catch (IllegalArgumentException e){
        fail("The test TrainDepartureRegisterGetSortedTrainDepartures() failed with the "
            + "exception message" + e.getMessage());
      }
    }

    @Test
    @DisplayName("TrainDepartureRegister.toString() returns the correct string")
    public void TrainDepartureRegisterToStringReturnsCorrectInformation() {
      try{
        String toString = "The register contains the following train departures: \n" +
            "TrainDeparture{trainNumber= 01', departureTime= 12:00, line= L1', destination= "
            + "Trondheim', track= -1, delay= 00:00}";
        assertEquals(trainDepartureRegister.toString(), toString);
      }catch (IllegalArgumentException e){
        fail("The test TrainDepartureRegisterToStringReturnsCorrectInformation() failed with the"
            + "exception message" + e.getMessage());
      }
    }
  }
}