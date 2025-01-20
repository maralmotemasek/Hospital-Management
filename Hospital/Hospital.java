package Hospital;

import Personal.Doctor;
import Personal.Nurse;
import Personal.Patient;
import Personal.Staff;
import Reservation.Payment;
import Reservation.Reserve;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private String name = "Mehgregan";
    private String location = "Babol";
    private ArrayList<Room> rooms;
    private ArrayList<Staff> staffs;
    private ArrayList<Patient> patients;
    private ArrayList<Reserve> reserves;
    private ArrayList<Department> departments;
    private ArrayList<Doctor> doctors;
    private ArrayList<Nurse> nurses;
    private Staff reception;

    public Hospital() {
        this.rooms = new ArrayList<>();
        this.staffs = new ArrayList<>();
        this.patients= new ArrayList<>();
        this.reserves = new ArrayList<>();
        this.departments = new ArrayList<>();
        this.doctors = new ArrayList<>();
        this.nurses = new ArrayList<>();
    }

    // Room management
    public void addRoom(Room room) {
        for (Room existingRoom : rooms) {
            if (existingRoom.getRoomNumber() == room.getRoomNumber()) {
                System.out.println("Room with this number already exists.");
                return;
            }
        }
        rooms.add(room);
        System.out.println("Room added and saved successfully.");
    }

    public void removeRoom(int roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
        rooms.remove(roomNumber);
    }

    public void showRooms() {
        System.out.println("Rooms: ");
        for (Room room : this.rooms) {
            System.out.println(room);
        }
        System.out.println(">----------------------<");
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.checkAvailability()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }


    // Staff Management
    public void addStaff(Staff staffMember) {
        for (Staff staff : staffs) {
            if (staff.getStaffID() == staffMember.getStaffID()) {
                System.out.println("Duplicate staff not added: " + staffMember);
                return;
            }
        }
        staffs.add(staffMember);
        System.out.println("Staff added and saved successfully.");
    }

    public void removeStaff(int staffID) {
        staffs.removeIf(staff -> staff.getStaffID() == staffID);
        staffs.remove(staffID);
    }

    public void showStaffs() {
        System.out.println("Staffs: ");
        for (Staff staff : this.staffs) {
            System.out.println(staff);
        }
        System.out.println(">----------------------<");
    }

    public Staff selectStaff(int staffID) {
        for (Staff staff : this.staffs) {
            if (staff.getStaffID() == staffID)
                return staff;
        }
        System.out.println("Staff ID is not valid");
        return null;
    }

    // Reserve management
    public void addReseve(Reserve reserving, Room reservingRoom) {
        for (Room room : this.rooms) {
            if (room.getRoomNumber() == reservingRoom.getRoomNumber())
                room.setAvailable(false);
        }
        this.reserves.add(reserving);
        System.out.println("Booking added successfully.");
    }

    // Department management
    public void addDepartment(Department department) {
        for (Department existingDepartment : departments) {
            if (existingDepartment.getDepartmentNumber() == department.getDepartmentNumber()) {
                System.out.println("Department with this number already exists.");
                return;
            }
        }
        departments.add(department);
        System.out.println("Department added and saved successfully.");
    }

    public void removeDepartmnt(int departmentNumber) {
        departments.removeIf(department -> department.getDepartmentNumber() == departmentNumber);
        departments.remove(departmentNumber);
    }

    public void showDepartment() {
        System.out.println("Department: ");
        for (Department department : this.departments) {
            System.out.println(department);
        }
        System.out.println(">----------------------<");
    }

    public List<Department> getAvailableDepartment() {
        List<Department> availableDepartment = new ArrayList<>();
        for (Department department: departments) {
            if (department.checkAvailability()) {
                availableDepartment.add(department);
            }
        }
        return availableDepartment;
    }

    // Patient management
    public void addPatient(Patient patientMember) {
        for (Patient patient : patients) {
            if (patient.getNationalCodeMeli() == patientMember.getNationalCodeMeli()) {
                System.out.println("Duplicate staff not added: " + patientMember);
                return;
            }
        }
        patients.add(patientMember);
        System.out.println("Staff added and saved successfully.");
    }

    public void showPatients() {
        System.out.println("Patients: ");
        for (Patient patient : this.patients) {
            System.out.println(patient);
        }
        System.out.println(">----------------------<");
    }




    // Dr management
    public void addDoctor(Doctor doctorMember) {
        for (Doctor doctor : doctors) {
            if (doctor.getMedicalCode() == doctorMember.getMedicalCode()) {
                System.out.println("Duplicate doctor not added: " + doctorMember);
                return;
            }
        }
        doctors.add(doctorMember);
        System.out.println("Doctor added and saved successfully.");
    }

    public void showDoctors() {
        System.out.println("Doctors: ");
        for (Doctor doctor : this.doctors) {
            System.out.println(doctor);
        }
        System.out.println(">----------------------<");
    }

    public void removeDoctor(int medicalCode) {
        doctors.removeIf(doctor -> doctor.getMedicalCode() == medicalCode);
        doctors.remove(medicalCode);
    }

    // Nurse management
    public void addNurse(Nurse nurseMember) {
        for (Nurse nurse : nurses) {
            if (nurse.getNationalCode() == nurseMember.getNationalCode()) {
                System.out.println("Duplicate nurse not added: " + nurseMember);
                return;
            }
        }
        nurses.add(nurseMember);
        System.out.println("Doctor added and saved successfully.");
    }

    public void showNurses() {
        System.out.println("Nurses: ");
        for (Nurse nurse : this.nurses) {
            System.out.println(nurse);
        }
        System.out.println(">----------------------<");
    }

    public void removeNurse(int nationalCode) {
        nurses.removeIf(nurse -> nurse.getNationalCode() == nationalCode);
        nurses.remove(nationalCode);
    }

    // Reserving Management
    public void addReserving(Reserve reserving, Room reservingRoom) {
        for (Room room : this.rooms) {
            if (room.getRoomNumber() == reservingRoom.getRoomNumber())
                room.setAvailable(false);
        }
        this.reserves.add(reserving);
        System.out.println("Reserving added successfully.");
    }

    public String toString() {
        return "Hotel{" +
                "name= Mehregan"+ '\'' +
                ", location= Babol" + '\'' +
                ", rooms=" + rooms +
                ", staffs=" + staffs +
                ", patients=" + patients +
                ", reseverves=" + reserves +
                ", reception=" + reception +
                '}';
    }
}
