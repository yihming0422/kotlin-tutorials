package com.bennyhuo.kotlin.advancedtypes.constructors.java;

public class Person {
    private int age;
    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

}
