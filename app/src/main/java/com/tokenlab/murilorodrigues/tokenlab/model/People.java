package com.tokenlab.murilorodrigues.tokenlab.model;

import java.io.Serializable;

/**
 * Created by murilo.rodrigues on 03/02/2016.
 * Entidade People representando os contatos
 */
public class People implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String surname;
    private int age;
    private String phoneNumber;

    public People(int id, String name, String surname, int age, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public People() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name + ' ' + surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        People people = (People) o;

        return id == people.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
