package com.parkinglot;

import com.parkinglot.exception.CapacityFullException;
import com.parkinglot.exception.CarNotFoundException;
import com.parkinglot.exception.VehicleAlreadyPark;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final int size;
    private final List<Object> vehicles;
    private List<Observer> observer;

    public ParkingLot(int size) {
        this.size = size;
        this.vehicles = new ArrayList<>();
        observer = new ArrayList<>();
    }

    public ParkingLot(int size, List<Observer> observer) {
        this.size = size;
        this.observer = observer;
        this.vehicles = new ArrayList<>();
    }

    public void park(Object vehicle) throws CapacityFullException, VehicleAlreadyPark {
        if (!isSpaceAvailable()) {
            throw new CapacityFullException(); // TODO - error message not needed.
        }

        if (isAlreadyParked(vehicle)) {
            throw new VehicleAlreadyPark();
        }

        vehicles.add(vehicle);
        if (isFull(size) && observer != null) { // TODO - should not have any null check.
            for (Observer observer : observer) { // TODO - name loops.
                observer.informIsFull();
            }
        }
    }

    private boolean isFull(int size) {
        return vehicles.size() == size;
    }

    public Object unPark(Object vehicle) throws CarNotFoundException {
        if (isFull(0)) {
            throw new CarNotFoundException();
        }
        if (!isAlreadyParked(vehicle)) {
            throw new CarNotFoundException();
        }
        vehicles.remove(vehicle);
        if (observer != null) {
            for (Observer observer : observer) {
                observer.informSpaceAvailable();
            }
        }
        return vehicle;
    }

    private boolean isAlreadyParked(Object vehicle) {
        return vehicles.contains(vehicle);
    }

    private boolean isSpaceAvailable() {
        return vehicles.size() < size;
    }

}
