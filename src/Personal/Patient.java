package Personal;

import Reservation.Reserve;

import java.util.Objects;

public class Patient extends Person {

    private int nationalCode;
    private int age;
    private String illness;
    boolean isSeek;
    boolean pregnant;
    private String insurance;

    public Patient(int nationalCode, String name, String lastName, int age, String gender, String illness , String insurance){
        super(name , lastName , gender);
        this.nationalCode =nationalCode;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.illness = illness;
        this.isSeek = true;
        this.pregnant = false;
        this.insurance = insurance;
    }

    public int getNationalCodeMeli() {
        return nationalCode;
    }

    public void setNationalCode(int patientNationalCode) {
        this.nationalCode =  nationalCode;
    }

   public String getName() {
       return name;
    }

   public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public boolean isSeek() {
        return isSeek;
    }

    public void setSeek(boolean seek) {
        isSeek = seek;
    }

    public boolean isPregnant() {
        if(Objects.equals(this.gender, "female"))
            return pregnant;
        return false;
    }

    public void setPregnant(boolean pregnant) {
        if(Objects.equals(this.gender, "female"))
            this.pregnant = pregnant;
    }

    public void Treatable(){
        this.isSeek = false;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public void Pregnancy(){
        if (Objects.equals(gender, "female")){
           this.pregnant = true;
        }
    }
   // public void addReserve(Reserve reserve) {
     //   reserveHistory.add(reserve);
       // System.out.println("Reservation.Reserve added successfully.");
    //}

  //  public void viewReserveHistory() {
    //    System.out.println("Reservation.Reserve History for " + name + ":");
      //  for (Reserve reserve : reserveHistory) {
        //    System.out.println(reserve.toString());
        //}
    //}

    public void displayInfo(){
        System.out.println("name is :" + name);
        System.out.println("last name is:" + lastName);
        System.out.println("national kode is :" + nationalCode);
        System.out.println("illness's name is :" + illness);
        System.out.println(" insurance name is :" + insurance);
    }
}

