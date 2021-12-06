package com.bennyhuo.kotlin.generics.constraints;

import java.util.function.Function;
import java.util.function.Supplier;

public class JavaConstraints {

    public static <T extends Comparable<T>> T maxOf(T a, T b) {
        if (a.compareTo(b) > 0) return a;
        else return b;
    }

    public static <T extends Comparable<T> & Supplier<R>, R extends Number>
    R callMax(T a, T b) {
        if (a.compareTo(b) > 0) return a.get();
        else return b.get();
    }

    public static void main(String... args) {

    }

}
