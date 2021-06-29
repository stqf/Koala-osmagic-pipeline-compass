package com.osmagic.pipeline.compass

/**
 *  使用失败, 添加启动参数 -Dgroovy.grape.enable=false 可以正常启动
 *
 *   @Grapes([
 *       @GrabResolver(name = 'aliyun', root = 'http://maven.aliyun.com/nexus/content/groups/public/'),
 *       @Grab(group = 'cn.hutool', module = 'hutool-all', version = '5.6.6'),
 *       @GrabConfig(systemClassLoader = true)
 *   ])
 *
 * Created By admin
 * <br/>Date: 2021/6/29 08:07
 * <br/>Description:
 */
class CompassApplication {

    static {
        System.setProperty("groovy.grape.enable", "false")
    }

    static void main(String[] args) {
        println("Hello CompassApplication .. ")


    }

}

