package com.parkinglot;

import com.parkinglot.exception.CapacityFullException;
import com.parkinglot.exception.CarNotFoundException;
import com.parkinglot.exception.VehicleAlreadyPark;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class ParkingLotTest {

    @Test
    void givenParkingLot_whenIsAvailable_ThenShouldBeAvailable() throws CapacityFullException, VehicleAlreadyPark {
        ParkingLot parkingLot = new ParkingLot(1);

        assertDoesNotThrow(()->parkingLot.park(new Object()));
    }

    @Test
    void givenParkingLotWithSizeOne_whenCheckIsAvailable_ThenShouldNotBeAvailable() throws CapacityFullException, VehicleAlreadyPark {
        ParkingLot parkingLot = new ParkingLot(1);
        Object vehicle1 = new Object();
        Object vehicle2 = new Object();
        parkingLot.park(vehicle1);
        CapacityFullException thrown = assertThrows(CapacityFullException.class, () -> {
            parkingLot.park(vehicle2);
        });
        assertEquals("capacity is full", thrown.getMessage());
    }

    @Test
    void givenParkingLotWithCapacityTwo_whenParkSameTwoVehicles_thenShouldNotBePark() throws CapacityFullException, VehicleAlreadyPark {
        ParkingLot parkingLot = new ParkingLot(2);
        Object vehicle = new Object();

        parkingLot.park(vehicle);
        VehicleAlreadyPark thrown = assertThrows(VehicleAlreadyPark.class, () -> {
            parkingLot.park(vehicle);
        });
        assertEquals("vehicle already park", thrown.getMessage());
    }

    @Test
    void givenParkingLotWithCapacityOne_whenUnParkVehicle_thenShouldReturnVehicle() throws VehicleAlreadyPark, CapacityFullException, CarNotFoundException {
        ParkingLot parkingLot = new ParkingLot(1);
        Object vehicle = new Object();

        parkingLot.park(vehicle);
        assertEquals(vehicle, parkingLot.unPark(vehicle));
    }

    @Test
    void givenParkingLotWithCapacityTwo_whenUnParkOneVehicle_thenShouldReturnVehicle() throws VehicleAlreadyPark, CapacityFullException, CarNotFoundException {
        ParkingLot parkingLot = new ParkingLot(2);
        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        assertEquals(vehicleTwo, parkingLot.unPark(vehicleTwo));
    }

    @Test
    void givenParkingLotWithCapacityTwo_whenUnParkNotAvailableVehicle_thenShouldThrowException() throws VehicleAlreadyPark, CapacityFullException, CarNotFoundException {
        ParkingLot parkingLot = new ParkingLot(2);
        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        assertEquals(vehicleOne, parkingLot.unPark(vehicleOne));
        assertEquals(vehicleTwo, parkingLot.unPark(vehicleTwo));


        CarNotFoundException thrown = assertThrows(CarNotFoundException.class, () -> {
            parkingLot.unPark(vehicleTwo);
        });
        assertEquals("VEHICLE NO LONGER AVAILABLE IN PARKING LOT", thrown.getMessage());
    }

    @Test
    void givenParkingLotWithCapacityTwo_whenUnParkThreeVehicle_thenShouldThrowException() throws VehicleAlreadyPark, CapacityFullException, CarNotFoundException {
        ParkingLot parkingLot = new ParkingLot(2);
        Object vehicleOne = new Object();
        Object vehicleTwo = new Object();
        parkingLot.park(vehicleOne);
        parkingLot.park(vehicleTwo);

        assertEquals(vehicleOne, parkingLot.unPark(vehicleOne));
        assertEquals(vehicleTwo, parkingLot.unPark(vehicleTwo));


        CarNotFoundException thrown = assertThrows(CarNotFoundException.class, () -> {
            parkingLot.unPark(vehicleTwo);
        });
        assertEquals("VEHICLE NO LONGER AVAILABLE IN PARKING LOT", thrown.getMessage());
    }


}
