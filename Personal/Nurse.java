package Personal;

public class Nurse extends Person {

    private int nationalCode;

    public Nurse(String name, String gender, String lastName) {
        super(name, gender, lastName);
        this.nationalCode = nationalCode;
    }

    public int getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(int nationalCode) {
        this.nationalCode = nationalCode;
    }
    public void seePatient(Patient patient) {
        System.out.println("Nurse " + this.name + " is seeing patient: " + patient.getName());
    }
}
