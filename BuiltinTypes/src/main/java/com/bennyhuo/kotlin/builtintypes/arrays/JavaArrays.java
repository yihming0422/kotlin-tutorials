package com.bennyhuo.kotlin.builtintypes.arrays;

import java.util.ArrayList;

public class JavaArrays {
    public static void main(String... args) {

        int[] a = new int[5];
        System.out.println(a.length);// only array use 'length'

        ArrayList<String> b = new ArrayList<>();
        System.out.println(b.size());

        int[] c = new int[]{1, 2, 3, 4, 5};

        String[] d = new String[]{"Hello", "World"};
        d[1] = "Java";
        System.out.println(d[0] + ", " + d[1]);

        float[] e = new float[]{1, 3, 5, 7};
        for (float element : e) {
            System.out.println(element);
        }

        for (int i = 0; i < e.length; i++) {
            System.out.println(e[i]);
        }

        // Test in an Array
        for (float element : e) {
            if(element == 1f){
                System.out.println("1f exists in variable 'e'");
                break;
            }
        }

        //Test not in an Array
        boolean exists = false;
        for (float element : e) {
            if(element == 1.2f){
                exists = true;
                break;
            }
        }

        if(!exists){
            System.out.println("1.2f not exists in variable 'e'");
        }

    }
}
