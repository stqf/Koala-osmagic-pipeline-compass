package com.osmagic.pipeline.compass

class CommUtils {

    static boolean generic(String... items) {
        println("[TRACE] generic   $items")
        return Arrays.asList(items).contains("Web")
    }

    static boolean algorithm(String... items) {
        println("[TRACE] algorithm $items")
        return Arrays.asList(items).contains("Algo")
    }


}
