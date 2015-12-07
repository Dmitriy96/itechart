package by.itechart.javalab.entity;

/**
 * Created by Dmitriy on 14.11.2015.
 */
public enum Gender {
    MALE("мужской"), FEMALE("женский");

    private String gender;

    private Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
