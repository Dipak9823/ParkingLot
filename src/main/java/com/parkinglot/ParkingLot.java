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

    public ParkingLot(int size, List<Observer> observer) {
        this.size = size;
        this.vehicles = new ArrayList<>();
        this.observer = observer;
    }

    public void park(Object vehicle) throws CapacityFullException, VehicleAlreadyPark {
        if (!isSpaceAvailable()) {
            throw new CapacityFullException();
        }

        if (isAlreadyParked(vehicle)) {
            throw new VehicleAlreadyPark();
        }

        vehicles.add(vehicle);
        if (isFull(size) && observer != null) {
            informObservers();
        }
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
            informObservers();
        }
        return vehicle;
    }


    private boolean isAlreadyParked(Object vehicle) {
        return vehicles.contains(vehicle);
    }

    private boolean isSpaceAvailable() {
        return vehicles.size() < size;
    }

    private boolean isFull(int size) {
        return vehicles.size() == size;
    }

    private void informObservers() {
        for (Observer observer : observer) {
            observer.informSpaceAvailable();
        }
    }
}
