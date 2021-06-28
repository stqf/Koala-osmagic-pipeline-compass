package com.osmagic.pipeline.compass.utils

@Grapes(value = [
    @Grab('org.codehaus.groovy:groovy-xml:3.0.8'),
    @Grab('com.alibaba:fastjson:1.2.75')
])

import groovy.xml.XmlParser
import groovy.xml.XmlUtil
import com.alibaba.fastjson.JSONObject;

class CommUtils {

    /**
     * 是否为Java或着Web项目
     * @param items
     * @return
     */
    static boolean generic(String... items) {
        println("[TRACE] generic   $items")
        return Arrays.asList(items).contains("Web")
    }

    /**
     * 是否为算法项目
     * @param items
     * @return
     */
    static boolean algorithm(String... items) {
        println("[TRACE] algorithm $items")
        return Arrays.asList(items).contains("Algo")
    }

    /**
     * xml配置文件内容截取
     * @param path
     * @return
     */
    static String xmlContent(String path, List<String> abs, List<String> apis) {
        def file = new File(path)
        XmlParser parser = new XmlParser()
        def docItem = parser.parse(file)
        NodeList children = docItem.children()
        for (def child : children) {
            def nameItem = child.attribute("Name")

            /*图片处理*/
            if ("ImageDetectionHttpHandle" == nameItem) {
                NodeList apiItems = child.children()
                List<Object> swap = new ArrayList<>()
                for (def apiItem : apiItems) {
                    String prefixItem = apiItem.attribute("Prefix")
                    if (Objects.nonNull(apis) && apis.contains(prefixItem)) {
                        swap.add(apiItem)
                    }
                }
                apiItems.retainAll(swap)
            }

            /*视频处理*/
            if ("OsmagicApp" == nameItem) {
                NodeList abItems = child.children()
                List<Object> swap = new ArrayList<>()
                for (def abItem : abItems) {
                    String tagNameItem = abItem.name()
                    String abNameItem = abItem.attribute("Name")
                    if ("AiAbility" != tagNameItem || (Objects.nonNull(abs) && abs.contains(abNameItem))) {
                        swap.add(abItem)
                    }
                }
                abItems.retainAll(swap)
            }
        }
        return XmlUtil.serialize(docItem)

        //def map =  ["name": "John"]
        //return JSONObject.toJSONString(map)
    }


}
