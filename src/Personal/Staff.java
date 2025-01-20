package Personal;

public class Staff extends Person {
    private int staffID;
    private String position;
    private static int idGenerator = 1;

    public Staff(String position, String name , String gender , String Lastname) {
        super(name , gender , Lastname);
        this.staffID = idGenerator++;
        this.position = position;
    }

    public static void setIdGenerator(int highestId) {
        idGenerator = highestId + 1;
    }

    public int getStaffID() {
        return staffID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Override
    public String toString() {
        return "Staff{" +
                "position='" + position + '\'' +
                ", name='" + name + '\'' +
                ", staffID=" + staffID +
                '}';
    }
}