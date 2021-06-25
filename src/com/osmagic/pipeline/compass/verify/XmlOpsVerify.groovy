package com.osmagic.pipeline.compass.verify

import groovy.xml.XmlParser

class XmlOpsVerify {

    static void main(String[] args) {
        String path = "D:\\workspace\\Ocean\\dir\\algo\\original\\magic_center_cfg.xml"
        File item = new File(path)
        item.eachLine { println it }
        def parser = new XmlParser()
        def docItem = parser.parse(item)
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




    }

}
