package org.miaixz.bus.core.xyz;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.beans.copier.CopyOptions;
import org.miaixz.bus.core.center.map.MapBuilder;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xml.XPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.xpath.XPathConstants;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@link XmlKit} 工具类
 */
public class XmlKitTest {

    @Test
    public void parseTest() {
        final String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "</returnsms>";
        final Document docResult = XmlKit.parseXml(result);
        final String elementText = XmlKit.elementText(docResult.getDocumentElement(), "returnstatus");
        Assertions.assertEquals("Success", elementText);
    }

    @Test
    @Disabled
    public void writeTest() {
        final String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success（成功）</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "</returnsms>";
        final Document docResult = XmlKit.parseXml(result);
        XmlKit.write(docResult, FileKit.file("/test/core/aaa.xml"), Charset.UTF_8);
    }

    @Test
    public void xpathTest() {
        final String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success（成功）</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "</returnsms>";
        final Document docResult = XmlKit.parseXml(result);
        final Object value = XPath.getByXPath("//returnsms/message", docResult, XPathConstants.STRING);
        Assertions.assertEquals("ok", value);
    }

    @Test
    public void xpathTest2() {
        final String result = ResourceKit.readString("test.xml");
        final Document docResult = XmlKit.parseXml(result);
        final Object value = XPath.getByXPath("//returnsms/message", docResult, XPathConstants.STRING);
        Assertions.assertEquals("ok", value);
    }

    @Test
    public void xmlToMapTest() {
        final String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
                + "<returnsms>"//
                + "<returnstatus>Success</returnstatus>"//
                + "<message>ok</message>"//
                + "<remainpoint>1490</remainpoint>"//
                + "<taskID>885</taskID>"//
                + "<successCounts>1</successCounts>"//
                + "<newNode><sub>subText</sub></newNode>"//
                + "</returnsms>";
        final Map<String, Object> map = XmlKit.xmlToMap(xml);

        Assertions.assertEquals(6, map.size());
        Assertions.assertEquals("Success", map.get("returnstatus"));
        Assertions.assertEquals("ok", map.get("message"));
        Assertions.assertEquals("1490", map.get("remainpoint"));
        Assertions.assertEquals("885", map.get("taskID"));
        Assertions.assertEquals("1", map.get("successCounts"));
        Assertions.assertEquals("subText", ((Map<?, ?>) map.get("newNode")).get("sub"));
    }

    @Test
    public void xmlToMapTest2() {
        final String xml = "<root><name>张三</name><name>李四</name></root>";
        final Map<String, Object> map = XmlKit.xmlToMap(xml);

        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals(ListKit.of("张三", "李四"), map.get("name"));
    }

    @Test
    public void mapToXmlTest() {
        final Map<String, Object> map = MapBuilder.of(new LinkedHashMap<String, Object>())//
                .put("name", "张三")//
                .put("age", 12)//
                .put("game", MapKit.builder(new LinkedHashMap<String, Object>()).put("昵称", "bus").put("level", 14).build())//
                .build();
        final Document doc = XmlKit.mapToXml(map, "user");
        // Console.log(XmlKit.toStr(doc, false));
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"//
                        + "<user>"//
                        + "<name>张三</name>"//
                        + "<age>12</age>"//
                        + "<game>"//
                        + "<昵称>bus</昵称>"//
                        + "<level>14</level>"//
                        + "</game>"//
                        + "</user>", //
                XmlKit.toString(doc, false));
    }

    @Test
    public void mapToXmlTest2() {
        // 测试List
        final Map<String, Object> map = MapBuilder.of(new LinkedHashMap<String, Object>())
                .put("Town", ListKit.of("town1", "town2"))
                .build();

        final Document doc = XmlKit.mapToXml(map, "City");
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                        "<City>" +
                        "<Town>town1</Town>" +
                        "<Town>town2</Town>" +
                        "</City>",
                XmlKit.toString(doc));
    }

    @Test
    public void readTest() {
        final Document doc = XmlKit.readXml("test.xml");
        Assertions.assertNotNull(doc);
    }

    @Test
    public void readBySaxTest() {
        final Set<String> eles = SetKit.of(
                "returnsms", "returnstatus", "message", "remainpoint", "taskID", "successCounts");
        XmlKit.readBySax(ResourceKit.getStream("test.xml"), new DefaultHandler() {
            @Override
            public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) {
                Assertions.assertTrue(eles.contains(localName));
            }
        });
    }

    @Test
    public void mapToXmlTestWithOmitXmlDeclaration() {

        final Map<String, Object> map = MapBuilder.of(new LinkedHashMap<String, Object>())
                .put("name", "ddatsh")
                .build();
        final String xml = XmlKit.mapToXmlString(map, true);
        Assertions.assertEquals("<xml><name>ddatsh</name></xml>", xml);
    }

    @Test
    public void getByPathTest() {
        final String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <ns2:testResponse xmlns:ns2=\"http://ws.xxx.com/\">\n" +
                "      <return>2020/04/15 21:01:21</return>\n" +
                "    </ns2:testResponse>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>\n";

        final Document document = XmlKit.readXml(xmlStr);
        final Object value = XPath.getByXPath(
                "//soap:Envelope/soap:Body/ns2:testResponse/return",
                document, XPathConstants.STRING);//
        Assertions.assertEquals("2020/04/15 21:01:21", value);
    }

    @Test
    public void beanToXmlIgnoreNullTest() {
        @Data
        class TestBean {
            private String ReqCode;
            private String AccountName;
            private String Operator;
            private String ProjectCode;
            private String BankCode;
        }

        final TestBean testBean = new TestBean();
        testBean.setReqCode("1111");
        testBean.setAccountName("账户名称");
        testBean.setOperator("cz");
        testBean.setProjectCode(null);
        testBean.setBankCode("00001");

        // 不忽略空字段情况下保留自闭标签
        Document doc = XmlKit.beanToXml(testBean, null, false);
        Assertions.assertNotNull(XmlKit.getElement(doc.getDocumentElement(), "ProjectCode"));

        // 忽略空字段情况下无自闭标签
        doc = XmlKit.beanToXml(testBean, null, true);
        Assertions.assertNull(XmlKit.getElement(doc.getDocumentElement(), "ProjectCode"));
    }

    @Test
    public void xmlToBeanTest() {
        @Data
        class TestBean {
            private String ReqCode;
            private String AccountName;
            private String Operator;
            private String ProjectCode;
            private String BankCode;
        }

        final TestBean testBean = new TestBean();
        testBean.setReqCode("1111");
        testBean.setAccountName("账户名称");
        testBean.setOperator("cz");
        testBean.setProjectCode("123");
        testBean.setBankCode("00001");

        final Document doc = XmlKit.beanToXml(testBean);
        Assertions.assertEquals(TestBean.class.getSimpleName(), doc.getDocumentElement().getTagName());

        final TestBean testBean2 = XmlKit.xmlToBean(doc, TestBean.class);
        Assertions.assertEquals(testBean.getReqCode(), testBean2.getReqCode());
        Assertions.assertEquals(testBean.getAccountName(), testBean2.getAccountName());
        Assertions.assertEquals(testBean.getOperator(), testBean2.getOperator());
        Assertions.assertEquals(testBean.getProjectCode(), testBean2.getProjectCode());
        Assertions.assertEquals(testBean.getBankCode(), testBean2.getBankCode());
    }

    @Test
    public void xmlToBeanTest2() {
        @Data
        class SmsRes {
            private String code;
        }

        final String xmlStr = "<?xml version=\"1.0\" encoding=\"gbk\" ?><response><code>02</code></response>";

        final Document doc = XmlKit.parseXml(xmlStr);

        // 标准方式
        final Map<String, Object> map = XmlKit.xmlToMap(doc.getFirstChild());
        final SmsRes res = new SmsRes();
        BeanKit.fillBeanWithMap(map, res, CopyOptions.of().setIgnoreError(true));

        // toBean方式
        final SmsRes res1 = XmlKit.xmlToBean(doc.getFirstChild(), SmsRes.class);

        Assertions.assertEquals(res.toString(), res1.toString());
    }

    @Test
    public void cleanCommentTest() {
        final String xmlContent = "<info><title>bus</title><!-- 这是注释 --><lang>java</lang></info>";
        final String ret = XmlKit.cleanComment(xmlContent);
        Assertions.assertEquals("<info><title>bus</title><lang>java</lang></info>", ret);
    }

    @Test
    @Disabled
    public void formatTest() {
        final Document xml = XmlKit.createXml("NODES");
        xml.setXmlStandalone(true);

        final NodeList parentNode = xml.getElementsByTagName("NODES");

        final Element parent1Node = xml.createElement("NODE");

        final Element node1 = xml.createElement("NODENAME");
        node1.setTextContent("走位");
        final Element node2 = xml.createElement("STEP");
        node2.setTextContent("1");
        final Element node3 = xml.createElement("STATE");
        node3.setTextContent("2");
        final Element node4 = xml.createElement("TIMELIMIT");
        node4.setTextContent("");
        final Element node5 = xml.createElement("STARTTIME");

        parent1Node.appendChild(node1);
        parent1Node.appendChild(node2);
        parent1Node.appendChild(node3);
        parent1Node.appendChild(node4);
        parent1Node.appendChild(node5);

        parentNode.item(0).appendChild(parent1Node);

        final String format = XmlKit.toString(xml, Charset.GBK, true);
        Console.log(format);
    }

    @Test
    public void getParamTest() {
        final String xml = "<Config name=\"aaaa\">\n" +
                "    <url>222222</url>\n" +
                "</Config>";

        final Document doc = XmlKit.parseXml(xml);
        final String name = doc.getDocumentElement().getAttribute("name");
        Assertions.assertEquals("aaaa", name);
    }

    @Test
    @Disabled
    public void issuesTest() {
        // 增加子节点后，格式会错乱，JDK的bug
        final String xmlStr = ResourceKit.readString("I5DO8E.xml");
        final Document doc = XmlKit.readXml(xmlStr);

        final Element item = doc.createElement("item");
        item.setAttribute("id", "cover-image");
        final Element manifestEl = XPath.getElementByXPath("//package/manifest", doc);
        manifestEl.appendChild(item);

        Console.log(XmlKit.format(doc));
    }

    @Test
    public void xmlStrToBeanTest() {
        final String xml = "<userInfo><name>张三</name><age>20</age><email>zhangsan@example.com</email></userInfo>";
        final Document document = XmlKit.readXml(xml);
        final UserInfo userInfo = XmlKit.xmlToBean(document, UserInfo.class);
        Assertions.assertEquals("张三", userInfo.getName());
        Assertions.assertEquals("20", userInfo.getAge());
        Assertions.assertEquals("zhangsan@example.com", userInfo.getEmail());
    }

    @Test
    public void issuesTest2() {
        final String xml = "<r>\n" +
                "  <c>\n" +
                "     <s>1</s>\n" +
                "     <p>str</p>\n" +
                "  </c>\n" +
                "</r>";

        final R r = XmlKit.xmlToBean(XmlKit.parseXml(xml), R.class);
        Assertions.assertEquals("1", r.getC().get(0).getS());
        Assertions.assertEquals("str", r.getC().get(0).getP());
    }

    @Data
    static class UserInfo {

        private String id;
        private String name;
        private String age;
        private String email;
    }

    @Data
    static class C {
        String s;
        String p;
    }

    @Data
    static class R {
        List<C> c;
    }

}
