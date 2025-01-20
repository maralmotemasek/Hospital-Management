package Reservation;

import Hospital.Room;
import Personal.Patient;
import Personal.Staff;
import java.time.LocalDate;

public class Reserve {

    private int id;
    private static int idGenerator = 1;
    private Staff reception;
    private Patient patient;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean paymentStatus;

    public Reserve(Staff reception, Patient patient, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = idGenerator++;
        this.reception = reception;
        this.patient = patient;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.paymentStatus = true;
        this.room.reserve();
    }

    public static void setIdGenerator(int highestId) {
        idGenerator = highestId + 1;
    }

    public int getBookingId() {
        return this.id;
    }

    public int getId() {
        return id;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public Staff getReception() {
        return reception;
    }

    public void setReception(Staff reception) {
        this.reception = reception;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    private static void updateIdGenerator(int lastId) {
        idGenerator = lastId + 1;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Calculate the total cost of the booking
    public double calculateTotalCost() {
        long days = this.checkOutDate.toEpochDay() - this.checkInDate.toEpochDay();
        return days * this.room.getPrice();
    }

    // Cancel the booking and release the room
    public void cancelReserve() {
        this.room.release();
        System.out.println("Reserve canceled: Room " + room.getRoomNumber() + ", Booking ID - " + id);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", reception=" + (reception != null ? reception.getName() : "null") +
                ", customer=" + (patient != null ? patient.getName() : "null") +
                ", room=" + (room != null ? room.getRoomNumber() : "null") +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
