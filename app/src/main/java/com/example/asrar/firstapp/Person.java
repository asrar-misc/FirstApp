package com.example.asrar.firstapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asrar on 12/14/2014.
 */
public class Person implements Parcelable {
    private String firstName;
    private String lastName;
    private boolean graduate;
    private boolean postGraduate;
    private String gender;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getGraduate() {
        return graduate;
    }
    public void setGraduate(boolean graduate) {
        this.graduate = graduate;
    }

    public boolean getPostGraduate() {
        return postGraduate;
    }
    public void setPostGraduate(boolean postGraduate) {
        this.postGraduate = postGraduate;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public static final Parcelable.Creator<Person> CREATOR = new Creator<Person>() {
        public Person createFromParcel(Parcel source) {
            Person mPerson = new Person();
            mPerson.firstName = source.readString();
            mPerson.lastName = source.readString();
            mPerson.gender = source.readString();
            mPerson.graduate = source.readByte() != 0;

            return mPerson;
        }
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(gender);
        parcel.writeByte((byte)(graduate ? 1 : 0));
    }
}
