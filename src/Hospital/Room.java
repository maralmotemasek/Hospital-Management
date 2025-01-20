package Hospital;

import java.util.ArrayList;

public class Room {
    private int roomNumber;
    private String type;
    private double price;
    private int bedCount;
    private boolean isAvailable;

    public Room(int roomNumber, String type, double price, int bedCount) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.bedCount = bedCount;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }


    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    public boolean checkAvailability() {
        return isAvailable;
    }

    public void reserve() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("Hospital's Room reserved successfully.");
        } else {
            System.out.println("Hospital's Room is not available.");
        }
    }

    public void release() {
        if (!this.isAvailable) {
            isAvailable = true;
            System.out.println("Hospital's Room released successfully.");
        }
    }


    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", bedCount=" + bedCount +
                ", isAvailable=" + isAvailable +
                '}';
    }
}