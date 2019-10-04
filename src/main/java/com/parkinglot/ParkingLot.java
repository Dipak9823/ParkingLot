package com.parkinglot;

import com.parkinglot.exception.CapacityFullException;
import com.parkinglot.exception.CarNotFoundException;
import com.parkinglot.exception.VehicleAlreadyPark;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final int size;
    private final List<Object> vehicles;
    private final Owner owner;
    private String message = "Parking Lot is Parking";

    public ParkingLot(int size) {
        this.size = size;
        this.vehicles = new ArrayList<>();
        owner = null;
    }

    public ParkingLot(int size, Owner owner) {
        this.size = size;
        this.owner = owner;
        this.vehicles = new ArrayList<>();
    }

    public void park(Object vehicle) throws CapacityFullException, VehicleAlreadyPark {
        if (!isSpaceAvailable()) {
            throw new CapacityFullException("capacity is full");
        }

        if (isAlreadyParked(vehicle)) {
            throw new VehicleAlreadyPark("vehicle already park");
        }

        vehicles.add(vehicle);
        if (isFull(size) && owner != null) {
            owner.informIsFull();
        }
    }

    private boolean isFull(int size) {
        return vehicles.size() == size;
    }

    public Object unPark(Object vehicle) throws CarNotFoundException {
        if (isFull(0)) {
            throw new CarNotFoundException("VEHICLE NO LONGER AVAILABLE IN PARKING LOT");
        }
        if (!isAlreadyParked(vehicle)) {
            throw new CarNotFoundException("VEHICLE NO LONGER AVAILABLE IN PARKING LOT");
        }
        vehicles.remove(vehicle);
        if (owner != null) {
            owner.informSpaceAvailable();
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
