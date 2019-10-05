package com.parkinglot;

import com.parkinglot.exception.CapacityFullException;
import com.parkinglot.exception.CarNotFoundException;
import com.parkinglot.exception.VehicleAlreadyPark;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final int size;
    private final List<Object> vehicles;
    private List<Observer> observers;

    public ParkingLot(int size, List<Observer> observers) {
        this.size = size;
        this.vehicles = new ArrayList<>();
        this.observers = observers;
    }

    public void park(Object vehicle) throws CapacityFullException, VehicleAlreadyPark {
        if (!isSpaceAvailable()) {
            throw new CapacityFullException();
        }

        if (isAlreadyParked(vehicle)) {
            throw new VehicleAlreadyPark();
        }

        vehicles.add(vehicle);
        if (isFull(size) && observers != null) {
            for (Observer observer : observers) {
                observer.informIsFull();
            }
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
        if (observers != null) {
            for (Observer observer : observers) {
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

    private boolean isFull(int size) {
        return vehicles.size() == size;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }
}
