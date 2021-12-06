package com.bennyhuo.kotlin.builtintypes.basics;

public class JavaBasicTypes {
    public static void main(String... args) {

        int a = 2;
        final String b = "Hello Java";

        long c = 12345678910l; // ok but not good.
        long d = 12345678910L; // ok

        int e = 10;
        long f = e; // implicit conversion

        // no unsigned numbers.

        String j = "I❤️China";
        System.out.println("Value of String 'j' is: " + j);
        System.out.println("Length of String 'j' is: " + j.length());
        System.out.printf("Length of String 'j' is: %d\n",  j.length());

        String k = "Today is a sunny day.";
        String m = new String("Today is a sunny day.");
        System.out.println(k == m); // compare references.
        System.out.println(k.equals(m)); // compare values.

        String n = "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <title>Hello World</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id=\"container\">\n" +
                "        <H1>Hello World</H1>\n" +
                "        <p>This is a demo page.</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        System.out.println(n);
    }
}
