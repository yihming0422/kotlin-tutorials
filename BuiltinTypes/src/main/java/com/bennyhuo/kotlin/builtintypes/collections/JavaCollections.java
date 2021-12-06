package com.bennyhuo.kotlin.builtintypes.collections;

import java.util.*;

public class JavaCollections {
    public static void main(String... args) {

        List<Integer> intList = new ArrayList<>(Arrays.asList(1, 2, 3, 4));


        List<String> stringList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            stringList.add("num: " + i);
        }

        for (int i = 0; i < 10; i++) {
            stringList.remove("num: " + i);
        }

        stringList.set(5, "HelloWorld");
        String valueAt5 = stringList.get(5);

        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Hello", 10);
        System.out.println(hashMap.get("Hello"));
    }
}
