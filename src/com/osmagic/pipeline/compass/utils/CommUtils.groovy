package com.osmagic.pipeline.compass.utils

import com.alibaba.fastjson.JSONArray
@Grapes(value = [
        @Grab('org.codehaus.groovy:groovy-xml:3.0.8'),
        @Grab('com.alibaba:fastjson:1.2.75')
])

import com.alibaba.fastjson.JSONObject
import groovy.xml.XmlParser
import groovy.xml.XmlUtil

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
     * 处理JSON映射文件
     * @param text
     * @param names
     * @return
     */
    static Map<String, List<String>> prepare(String text, List<String> names) {
        Map<String, List<String>> reItem = new LinkedHashMap<>()

        List<String> abItems = new ArrayList<>()
        List<String> apiItems = new ArrayList<>()
        List<String> nameItems = new ArrayList<>()
        Set<String> modelItems = new HashSet<>()

        JSONArray items = JSONObject.parseArray(text);
        for (i in 0..<items.size()) {
            JSONObject objItem = items.getJSONObject(i)

            String nameIt = objItem.getString("name")
            if (!names.contains(nameIt)) {
                nameItems.add(nameIt)
                continue
            }

            String abIt = objItem.getString("ab")
            if (Objects.nonNull(abIt) && !abIt.trim().isEmpty()) {
                abItems.add(abIt)
            }

            String apiIt = objItem.getString("api")
            if (Objects.nonNull(apiIt) && !apiIt.trim().isEmpty()) {
                apiItems.add(apiIt)
            }

            JSONArray modelIt = objItem.getJSONArray("models")
            if (Objects.nonNull(modelIt) && !modelIt.isEmpty()) {
                def modelItPro = modelIt.toJavaList(String.class)
                modelItems.addAll(modelItPro)
            }
        }

        reItem.put("names", nameItems)
        reItem.put("abs", abItems)
        reItem.put("apis", apiItems)
        reItem.put("models", new ArrayList<String>(modelItems))

        return reItem
    }

    static String modelFileName(String text) {
        def objItem = JSONObject.parseObject(text)

        JSONObject algoItem = objItem.getJSONObject("algo")

        def mpItem = algoItem.getString("model_path")

        return mpItem
    }

    /**
     * xml配置文件内容截取
     * @param text
     * @return
     */
    static String xmlContent(String text, List<String> abs, List<String> apis) {
        //def file = new File(path)
        XmlParser parser = new XmlParser()
        def docItem = parser.parseText(text)
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
    }


}
