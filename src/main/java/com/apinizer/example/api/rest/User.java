package com.apinizer.example.api.rest;

import java.io.Serializable;

public class User implements Serializable {

    private long id;

    private String name;

    private String username;

    private String password;

    private int age;

    private double salary;

    public User() {
        id = 0;
    }

    public User(long id, String name, int age, double salary, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age
                + ", salary=" + salary + "]";
    }


}
