package com.bennyhuo.kotlin.advancedtypes.visibilities;

public class JavaVisibilities {
    public static void main(String... args) {
        ApiJava apiJava = new ApiJava();
        apiJava.a();

        CoreApiJavaA coreApiJavaA = new CoreApiJavaA();
        coreApiJavaA.a();

//        CoreApiKotlinA coreApiKotlinA = new CoreApiKotlinA();
//        coreApiKotlinA.%abcd();
    }
}
