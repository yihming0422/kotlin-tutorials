package com.bennyhuo.kotlin.generics.startprojections;

import java.util.HashMap;
import java.util.List;

public class JavaRawTypes {

    public static void main(String... args) {
        HashMap<String, Integer> hashMap = getHashMap();
        //....
        HashMap map = hashMap;
        map.put("H", "Hello");
        //...
        Integer i = hashMap.get("H");
        System.out.println(i);
    }

    public static HashMap<String, Integer> getHashMap(){
        return new HashMap<String, Integer>();
    }


}
