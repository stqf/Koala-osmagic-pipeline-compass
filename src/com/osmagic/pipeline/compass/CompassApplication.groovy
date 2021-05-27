package com.osmagic.pipeline.compass

/*
使用失败

@Grapes([
        @Grab(group = 'cn.hutool', module = 'hutool-all', version = '5.6.6'),
        @GrabConfig(systemClassLoader = true)
])
*/

class CompassApplication {

    static {
        System.setProperty("groovy.grape.enable", "false")
    }

    static void main(String[] args) {
        println("Hello CompassApplication .. ")


    }

}

