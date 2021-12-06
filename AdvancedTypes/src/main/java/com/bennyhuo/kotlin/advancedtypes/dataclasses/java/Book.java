package com.bennyhuo.kotlin.advancedtypes.dataclasses.java;

public class Book {
    private long id;
    private String name;
    private Person person;

    public long getId() {
        return id;
    }

    public Book id(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Book name(String name) {
        this.name = name;
        return this;
    }

    public Person getPerson() {
        return person;
    }

    public Book person(Person person) {
        this.person = person;
        return this;
    }
}
