package com.parkinglot;

public class Owner {
    private boolean isParkingLotFull =false;
    private boolean isAvailable=false;

    public void informIsFull(){
        this.isParkingLotFull =true;
    }

    public void informSpaceAvailable() {
        this.isAvailable =true;
    }
}
