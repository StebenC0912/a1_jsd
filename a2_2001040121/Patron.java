package a2_2001040121;

import common.PatronType;

import java.util.Date;

public class Patron {
    private String PatronID;
    private String name;
    private Date DOB;
    private String email;
    private String phoneNum;
    private PatronType type;
    private static int currentID = 1;
    private String generatePatronID() {
        String result = "P";
        if (currentID < 10) {
            result = result.concat("00").concat(Integer.toString(currentID));
        } else if (currentID < 99) {
            result = result.concat("0").concat(Integer.toString(currentID));
        } else {
            result = result.concat(Integer.toString(currentID));
        }
        currentID++;
        return result;
    }
    private String generatePatronID(int id) {
        String result = "P";
        if (id < 10) {
            result = result.concat("00").concat(Integer.toString(id));
        } else if (id < 99) {
            result = result.concat("0").concat(Integer.toString(id));
        } else {
            result = result.concat(Integer.toString(id));
        }
        return result;
    }
    public Patron(int id,String name, Date DOB, String email, String phoneNum, PatronType type) {
        this.PatronID = generatePatronID(id);
        this.name = name;
        this.DOB = DOB;
        this.email = email;
        this.phoneNum = phoneNum;
        this.type = type;
    }
    public Patron(String name, Date DOB, String email, String phoneNum, PatronType type) {
        this.name = name;
        this.DOB = DOB;
        this.email = email;
        this.phoneNum = phoneNum;
        this.type = type;
        this.PatronID = generatePatronID();
    }

    public String getPatronID() {
        return PatronID;
    }

    public void setPatronID(String patronID) {
        PatronID = patronID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public PatronType getType() {
        return type;
    }

    public void setType(PatronType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Patron{" +
                "PatronID='" + PatronID + '\'' +
                ", name='" + name + '\'' +
                ", DOB=" + DOB +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", type=" + type +
                '}';
    }
}
