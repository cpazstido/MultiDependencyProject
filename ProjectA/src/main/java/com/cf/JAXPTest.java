package com.cf;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JAXPTest {

    public void test() throws Exception{

        // 创建XML解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        factory.setValidating(true);
        factory.setNamespaceAware(true);
        /*
         * om.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl
         * xerces是由IBM公司提供的XML解析器
         */
        System.out.println(factory.getClass().getName());

        // 创建XML解析器
        DocumentBuilder builder = factory.newDocumentBuilder();
        System.out.println(builder.getClass().getName());

        /**
         * <pre>
         * 获取到Document对象,Document对象代表着DOM树的根节点
         * 使用Document对象可以对XML文档的数据进行基本操作
         * Document对象在DOM解析模型中也属于一个Node对象,
         * 它是其他Node对象所在的上下文,其他Node对象包括
         * Element,Text,Attr,Comment,Processing Instruction等等
         * 根节点 != 根元素,它们是包含关系
         * </pre>
         */
        String rootPath = getClass().getClassLoader().getResource("book.xml").getPath().toString();
        System.out.println(rootPath);
        rootPath = getClass().getClassLoader().getResource("").getFile();
        Enumeration<URL> urls = getClass().getClassLoader().getResources("com/cf/");
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            System.out.println(url.getFile());
        }
        System.out.println(getClass().getClassLoader().getResource("").getFile());

        Document document = builder.parse(new File(rootPath));

        /**
         * <pre>
         * 获取到XML文档的根元素
         * 对于XML中的Document是XML文档的根节点,而它的子元素是XML文档的XML文档根元素
         * </pre>
         */
        Element root = document.getDocumentElement();
        System.out.println("获取的XML文档的根元素节点:" + root.getTagName());

        /**
         * <pre>
         * 获取根元素节点的子节点
         * 对于XML文档而言,无论是Document,Elment,Text等等,
         * 它们在DOM解析模型中都属性一个Node,因此这里需要注意的一点是
         * 空白字符在DOM解析中也会被作为一个Node元素来处理。
         * </pre>
         */
        // 获取到根元素的子节点
        NodeList nodeList = root.getChildNodes();

        /*
         * 空白字符也会被当作子节点来处理,因为它也是一个Node来处理, 但是属性不会被作为子节点
         */
        System.out.println("子节点的个数为:" + nodeList.getLength());

        // 遍历所有的子节点
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            switch (node.getNodeType()) {
                case Node.ELEMENT_NODE:
                    Element element = (Element) node;
                    System.out.println("获取的是元素:" + node.getNodeName());
                    break;
                case Node.TEXT_NODE:
                    System.out.println("获取的是文本" + node.getNodeName());
                    break;
                case Node.COMMENT_NODE:
                    System.out.println("获取的是注释:" + node.getNodeName());
                    Comment comment = (Comment) node;
                    System.out.println("注释的内容是:" + comment.getData());
                    break;
            }
        }

        System.out.println("--------访问属性----------");
        // 访问属性
        Attr attr = root.getAttributeNode("名字");
        System.out.println("根元素节点属性的值:" + attr.getValue());

        // 可以是使用一种更加直接的方式访问属性的值
        String attrValue = root.getAttribute("名字");
        System.out.println("另一种方式获取根元素节点属性的值:" + attrValue);

        // 获取元素的全部属性
        NamedNodeMap attrs = root.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            Attr attr2 = (Attr) attrs.item(i);
            System.out.println("name:" + attr2.getName() + ",value:"
                    + attr2.getValue());
        }

    }

    public static void main(String[] args) throws Exception {
        JAXPTest jaxpTest = new JAXPTest();
        jaxpTest.test();
        ClassB classB = new ClassB();
        System.out.println(classB instanceof InterfaceA);

    }


    /**
     * <pre>
     * 使用JAXP完成添加元素节点操作
     * 对于DOM解析模型来说,因为XML文档的上下文是Document对象
     * 所以对于XML文档的操作都使用Document对象来完成
     * </pre>
     */

    public void addElementTest() throws Exception {

        // 创建解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建解析器
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 获取XML文档的根节点(root node)
        Document document = builder.parse(new File("xml/book2.xml"));

        // 返回新创建的Element节点
        Element book = document.createElement("书");

        Element bookName = document.createElement("书名");
        bookName.setTextContent("Liunx");

        Element author = document.createElement("作者");
        author.setTextContent("XXX");

        Element price = document.createElement("价格");
        price.setTextContent(String.valueOf(80.5));

        // 通过父元素添加子元素
        book.appendChild(bookName);
        book.appendChild(author);
        book.appendChild(price);

        document.getDocumentElement().appendChild(book);

        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(
                new File("xml/book2.xml")));
    }

    /**
     * 更新节点元素
     */

    public void updateElementTest() throws Exception {

        // 创建解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建解析器
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 获取XML文档的根节点(root node)
        Document document = builder.parse(new File("xml/book2.xml"));

        // 获取第3个价格元素
        Node priceNode = document.getElementsByTagName("价格").item(2);
        // 判断是否是Element类型
        if (priceNode.getNodeType() == Node.ELEMENT_NODE) {
            priceNode.setTextContent("200");
        }

        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(
                new File("xml/book2.xml")));
    }


    public void deleteElementTest() throws Exception {
        // 创建解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建解析器
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 获取XML文档的根节点(root node)
        Document document = builder.parse(new File("xml/book2.xml"));

        Node node = document.getElementsByTagName("书").item(2);

        document.getDocumentElement().removeChild(node);

        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(
                new File("xml/book2.xml")));
    }

    /**
     * 添加子元素到到指定位置
     * @throws Exception
     */

    public void insertElementTest() throws Exception{

        // 创建解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建解析器
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 获取XML文档的根节点(root node)
        Document document = builder.parse(new File("xml/book2.xml"));

        // 返回新创建的Element节点
        Element book = document.createElement("书");

        Element bookName = document.createElement("书名");
        bookName.setTextContent("Liunx");

        Element author = document.createElement("作者");
        author.setTextContent("XXX");

        Element price = document.createElement("价格");
        price.setTextContent(String.valueOf(80.5));

        // 通过父元素添加子元素
        book.appendChild(bookName);
        book.appendChild(author);
        book.appendChild(price);

        document.getDocumentElement().insertBefore(book, document.getElementsByTagName("书").item(1));

        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(
                new File("xml/book2.xml")));
    }


    /**
     * 添加属性
     * @throws Exception
     */

    public void addAttrTest() throws Exception{

        // 创建解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建解析器
        DocumentBuilder builder = factory.newDocumentBuilder();

        // 获取XML文档的根节点(root node)
        Document document = builder.parse(new File("xml/book2.xml"));

        Node node = document.getElementsByTagName("书").item(1);
        if(node.getNodeType() == Node.ELEMENT_NODE){
            Element element  = (Element) node;
            element.setAttribute("名称","Java开发");
        }
        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(
                new File("xml/book2.xml")));
    }



}
