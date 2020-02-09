package org.aoju.bus.core.utils;

import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.map.MapBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link XmlUtils} 工具类
 */
public class XmlUtilsTest {

    @Test
    public void parseTest() {
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "</returnsms>";
        Document docResult = XmlUtils.parseXml(result);
        String elementText = XmlUtils.elementText(docResult.getDocumentElement(), "returnstatus");
        Assertions.assertEquals("Success", elementText);
    }

    @Test
    public void writeTest() {
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success（成功）</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "</returnsms>";
        Document docResult = XmlUtils.parseXml(result);
        XmlUtils.toFile(docResult, "/data/aaa.xml", "utf-8");
    }

    @Test
    public void xpathTest() {
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success（成功）</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "</returnsms>";
        Document docResult = XmlUtils.parseXml(result);
        Object value = XmlUtils.getByXPath("//returnsms/message", docResult, XPathConstants.STRING);
        Assertions.assertEquals("ok", value);
    }

    @Test
    public void xmlToMapTest() {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "<newNode><sub>subText</sub></newNode>"//
                + "</returnsms>";
        Map<String, Object> map = XmlUtils.xmlToMap(xml);
        Console.log(map);

        Assertions.assertEquals(6, map.size());
        Assertions.assertEquals("Success", map.get("returnstatus"));
        Assertions.assertEquals("ok", map.get("message"));
        Assertions.assertEquals("1490", map.get("remainpoint"));
        Assertions.assertEquals("885", map.get("taskID"));
        Assertions.assertEquals("1", map.get("successCounts"));
        Assertions.assertEquals("subText", map.get("newNode").toString());
    }

    @Test
    public void xmlToMapTest2() {
        String xml = "<root><name>张三</name><name>李四</name></root>";
        Map<String, Object> map = XmlUtils.xmlToMap(xml);

        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals(CollUtils.newArrayList("张三", "李四"), map.get("name"));
    }

    @Test
    public void mapToXmlTest() {
        Map<String, Object> map = MapBuilder.create(new LinkedHashMap<String, Object>())//
                .put("name", "张三")//
                .put("age", 12)//
                .put("game", MapUtils.builder(new LinkedHashMap<String, Object>()).put("昵称", "老K").put("level", 14).build())//
                .build();
        Document doc = XmlUtils.mapToXml(map, "user");
        // Console.log(XmlUtils.toStr(doc, false));
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"//
                        + "<user>"//
                        + "<name>张三</name>"//
                        + "<age>12</age>"//
                        + "<game>"//
                        + "<昵称>老K</昵称>"//
                        + "<level>14</level>"//
                        + "</game>"//
                        + "</user>", //
                XmlUtils.toStr(doc, false));
    }

    @Test
    public void readTest() {
        Document doc = XmlUtils.readXML("test.xml");
        Assertions.assertNotNull(doc);
    }

}
