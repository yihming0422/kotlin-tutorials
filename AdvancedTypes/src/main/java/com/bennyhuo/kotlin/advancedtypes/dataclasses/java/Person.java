package com.bennyhuo.kotlin.advancedtypes.dataclasses.java;

public class Person {
    private long id;
    private String name;
    private int age;

    public long getId() {
        return id;
    }

    public Person id(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Person age(int age) {
        this.age = age;
        return this;
    }
}
