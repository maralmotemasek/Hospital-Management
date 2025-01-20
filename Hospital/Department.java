package Hospital;

import Personal.Doctor;

import java.util.ArrayList;

public class Department{
    private String speciality;
    private ArrayList<Room> rooms;
    private ArrayList<Doctor> doctors;
    private int departmentNumber;
    private boolean isAvailable;


    public Department(String speciality, int departmentNumber) {
        this.speciality = speciality;
        this.departmentNumber =departmentNumber;
        this.rooms = new ArrayList<>();
        this.doctors = new ArrayList<>();
        this.isAvailable = true;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public int getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(int departmentNumber) {
        this.departmentNumber = departmentNumber;
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
            System.out.println("Hospital's Department reserved successfully.");
        } else {
            System.out.println("Hospital's Department is not available.");
        }
    }

    public void release() {
        if (!this.isAvailable) {
            isAvailable = true;
            System.out.println("Hospital's Department released successfully.");
        }
    }

    public void addDoctor(Doctor doctor) {
        if (doctor.getSpeciality().equals(this.speciality)) {
            doctors.add(doctor);
            System.out.println("Doctor " + doctor.getName() + " added to the " + speciality + " department.");
        } else {
            System.out.println("Doctor " + doctor.getName() + " does not match the specialty of " + speciality + " department.");
        }
    }

}
