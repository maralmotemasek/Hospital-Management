package Personal;

public abstract class Person{
    String name;
    String gender;
    String lastName;

    public Person(String name , String gender ,String lastName) {
        this.name = name;
        this.gender = gender;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

}