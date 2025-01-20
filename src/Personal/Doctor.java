package Personal;

public class Doctor extends Person {
     int medicalCode ;
     String speciality;

    public Doctor (String name, String lastName, String gender, int medicalCode, String speciality) {
        super(name, lastName, gender);
        this.medicalCode = medicalCode;
        this.speciality = speciality;
    }

    public int getMedicalCode() {
        return medicalCode;
    }

    public void setMedicalCode(int medicalCode) {
        this.medicalCode = medicalCode;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public String getName(){
        return name;
    }
    public void setName(){
        this.name = name;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(){
        this.lastName = lastName;
    }
    public void seePatient(Patient patient) {
        System.out.println("Dr. " + this.name + " is seeing patient: " + patient.getName());
    }
    public void displayInfo(){
        System.out.println("name is :" + name);
        System.out.println("last name is :" + lastName);
        System.out.println("medical code :" + medicalCode);
        System.out.println("expertise: " + speciality);
    }
}
