package com.parkinglot;

import com.parkinglot.exception.CapacityFullException;
import com.parkinglot.exception.CarNotFoundException;
import com.parkinglot.exception.VehicleAlreadyPark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DummyOwner implements Observer {

    public int timesIsFull = 0;
    public int timesIsAvailable = 0;


    @Override
    public void informIsFull() {
        timesIsFull++;
    }

    @Override
    public void informSpaceAvailable() {
        timesIsAvailable++;
    }
}

class SecurityGuard implements Observer {

    public int timesIsFull = 0;
    public int timesIsAvailable = 0;

    @Override
    public void informIsFull() {
        timesIsFull++;
    }

    @Override
    public void informSpaceAvailable() {
        timesIsAvailable++;
    }
}

public class ParkingLotTest {

    List<Observer> observers ;

    @BeforeEach
    void setUp() {
        observers = new ArrayList<>();
    }

    @Test
    void givenParkingLot_whenIsAvailable_ThenShouldBeAvailable() throws Exception {
        ParkingLot parkingLot = new ParkingLot(1,observers);

        assertDoesNotThrow(() -> parkingLot.park(new Object()));
    }

    @Test
    void givenParkingLotWithSizeOne_whenCheckIsAvailable_ThenShouldNotBeAvailable() throws Exception {
        ParkingLot parkingLot = new ParkingLot(1,observers);
        Object vehicle1 = new Object();
        Object vehicle2 = new Object();
        parkingLot.park(vehicle1);
        CapacityFullException thrown = assertThrows(CapacityFullException.class, () -> {
            parkingLot.park(vehicle2);
        });
    }

    @Test
    void givenParkingLotWithCapacityTwo_whenParkSameTwoVehicles_thenShouldNotBePark() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2,observers);
        Object vehicle = new Object();

        parkingLot.park(vehicle);
        VehicleAlreadyPark thrown = assertThrows(VehicleAlreadyPark.class, () -> {
            parkingLot.park(vehicle);
        });
    }

    @Test
    void givenParkingLotWithCapacityOne_whenUnParkVehicle_thenShouldReturnVehicle() throws Exception {
        ParkingLot parkingLot = new ParkingLot(1,observers);
        Object vehicle = new Object();

        parkingLot.park(vehicle);
        assertEquals(vehicle, parkingLot.unPark(vehicle));
    }

    @Test
    void givenParkingLotWithCapacityTwo_whenUnParkOneVehicle_thenShouldReturnVehicle() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2,observers);
        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        assertEquals(vehicleTwo, parkingLot.unPark(vehicleTwo));
    }

    @Test
    void givenParkingLotWithCapacityTwo_whenUnParkNotAvailableVehicle_thenShouldThrowException() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2,observers);
        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        assertEquals(vehicleOne, parkingLot.unPark(vehicleOne));
        assertEquals(vehicleTwo, parkingLot.unPark(vehicleTwo));


        CarNotFoundException thrown = assertThrows(CarNotFoundException.class, () -> {
            parkingLot.unPark(vehicleTwo);
        });
    }

    @Test
    void givenParkingLotWithCapacityTwo_whenUnParkVehicle_thenShouldThrowException() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2,observers);
        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        assertEquals(vehicleOne, parkingLot.unPark(vehicleOne));
        assertEquals(vehicleTwo, parkingLot.unPark(vehicleTwo));


        CarNotFoundException thrown = assertThrows(CarNotFoundException.class, () -> {
            parkingLot.unPark(vehicleTwo);
        });
    }

    @Test
    void givenParkingLotWithCapacityOne_WhenIsFull_ThenNotifyToOwner() throws Exception {
        DummyOwner owner = new DummyOwner();
        observers.add(owner);
        ParkingLot parkingLot = new ParkingLot(2, observers);
        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        assertEquals(owner.timesIsFull, 1);
    }

    @Test
    void givenParkingLotWithCapacityOne_WhenCheckHowManyTimesCall_ThenShouldReturnsOne() throws Exception {
        DummyOwner owner = new DummyOwner();
        observers.add(owner);
        ParkingLot parkingLot = new ParkingLot(1, observers);
        Object vehicleOne = new Object();
        parkingLot.park(vehicleOne);

        assertEquals(owner.timesIsFull, 1);
    }

    @Test
    void givenFullParkingLot_WhenUnPark_ThenNotifyToOwner() throws Exception {
        List<Observer> observers = new ArrayList<>();
        DummyOwner owner = new DummyOwner();
        observers.add(owner);
        ParkingLot parkingLot = new ParkingLot(2, observers);
        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        parkingLot.unPark(vehicleTwo);

        assertEquals(owner.timesIsAvailable, 1);
    }

    @Test
    void givenParkingLotWithCapacityOne_WhenUnPark_ThenShouldNotifyToOwner() throws Exception {
        DummyOwner owner = new DummyOwner();
        observers.add(owner);
        ParkingLot parkingLot = new ParkingLot(1, observers);

        Object vehicleOne = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.unPark(vehicleOne);

        assertEquals(owner.timesIsAvailable, 1);
    }

    @Test
    void givenParkingLotWithCapacityTwo_WhenFull_ThenShouldNotifyToSecurityGuard() throws Exception {
        SecurityGuard securityGuard = new SecurityGuard();
        observers.add(securityGuard);
        ParkingLot parkingLot = new ParkingLot(2, observers);

        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        assertEquals(securityGuard.timesIsFull, 1);
    }

    @Test
    void givenFullParkingLot_WhenUnPark_ThenShouldNotifyToSecurityGuard() throws Exception {
        SecurityGuard securityGuard = new SecurityGuard();
        observers.add(securityGuard);
        ParkingLot parkingLot = new ParkingLot(2, observers);

        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        parkingLot.unPark(vehicleTwo);

        assertEquals(securityGuard.timesIsAvailable, 1);
    }

    @Test
    void givenParkingLotWithCapacityTwo_WhenFull_ThenShouldNotifyToSecurityGuardAndOwner() throws Exception {
        SecurityGuard securityGuard = new SecurityGuard();
        DummyOwner owner = new DummyOwner();
        observers.add(securityGuard);
        observers.add(owner);
        ParkingLot parkingLot = new ParkingLot(2, observers);

        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        assertEquals(securityGuard.timesIsFull, 1);
        assertEquals(owner.timesIsFull, 1);
    }

    @Test
    void givenFullParkingLot_WhenUnPark_ThenShouldNotifyToSecurityGuardAndOwner() throws Exception {
        SecurityGuard securityGuard = new SecurityGuard();
        DummyOwner owner = new DummyOwner();
        observers.add(securityGuard);
        observers.add(owner);
        ParkingLot parkingLot = new ParkingLot(2, observers);

        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);
        parkingLot.unPark(vehicleTwo);

        assertEquals(securityGuard.timesIsAvailable, 1);
        assertEquals(owner.timesIsAvailable, 1);
    }

    @Test
    void givenParkingLot_WhenAddingObserverAfterCreationOfParkingLotAndParkIsFull_ThenShouldNotifyToAllObservers() throws Exception {
        DummyOwner owner=new DummyOwner();
        observers.add(owner);
        ParkingLot parkingLot=new ParkingLot(1,observers);

        SecurityGuard securityGuard=new SecurityGuard();
        parkingLot.addObserver(securityGuard);
        Object vehicle=new Object();
        parkingLot.park(vehicle);

        assertEquals(owner.timesIsFull,1);
        assertEquals(securityGuard.timesIsFull,1);
    }

    @Test
    void givenFullParkingLot_WhenAddingObserverAfterCreationOfParkingLotAndUnPark_ThenShouldNotifyToAllObservers() throws Exception {
        DummyOwner owner=new DummyOwner();
        observers.add(owner);
        ParkingLot parkingLot=new ParkingLot(1,observers);

        SecurityGuard securityGuard=new SecurityGuard();
        parkingLot.addObserver(securityGuard);
        Object vehicle=new Object();
        parkingLot.park(vehicle);
        parkingLot.unPark(vehicle);

        assertEquals(owner.timesIsAvailable,1);
        assertEquals(securityGuard.timesIsAvailable,1);
    }
}
