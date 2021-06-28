package com.osmagic.pipeline.compass.verify

import groovy.xml.XmlParser
import groovy.xml.XmlUtil

class XmlOpsVerify {

    static void main(String[] args) {
        String path = "D:\\workspace\\Ocean\\dir\\algo\\original\\magic_center_cfg.xml"
        File item = new File(path)
        //item.eachLine { println it }
        XmlParser parser = new XmlParser()
        def docItem = parser.parse(item)

        NodeList children = docItem.children()
        for (def child : children) {
            def nameItem = child.attribute("Name")

            if ("ImageDetectionHttpHandle" == nameItem) {
                NodeList apis = child.children()
                List<Object> swap = new ArrayList<>()
                for (def apiItem : apis) {
                    String prefixItem = apiItem.attribute("Prefix")
                    if (prefixItem.contains("road-damaged")) {
                        swap.add(apiItem)
                    }
                }
                //swap.forEach { child.remove(it) }
                apis.retainAll(swap)
            }

            if ("OsmagicApp" == nameItem) {
                NodeList abs = child.children()
                List<Object> swap = new ArrayList<>()
                for (def abItem : abs) {
                    String tagNameItem = abItem.name()
                    String abNameItem = abItem.attribute("Name")
                    if ("AiAbility" != tagNameItem || abNameItem == "MotorVehicleParking") {
                        swap.add(abItem)
                    }
                }
                //swap.forEach { child.remove(it) }
                abs.retainAll(swap)
            }
        }

        println(XmlUtil.serialize(docItem))

        /*
        def staticItem = docItem.findAll {
            String nameItem = it.attribute("Name")
            return "ImageDetectionHttpHandle".equals(nameItem)
        }
        println(staticItem)

        def dynamicItem = docItem.findAll {
            def nameItem = it.attribute("Name")
            return "OsmagicApp".equals(nameItem)
        }
        println(dynamicItem)
        */


    }

}
