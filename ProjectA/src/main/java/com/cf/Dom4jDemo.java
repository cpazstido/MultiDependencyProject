package com.cf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import org.dom4j.*;


import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jDemo{

    public void createXML() {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("root");
        Element father = root.addElement("father");
        Element child = father.addElement("child");
        Element name = child.addElement("name");
        name.setText("十六笔画");
        Element sex = child.addElement("sex");
        sex.setText("男");
        try {
            PrintWriter pw = new PrintWriter("e:\\wc.xml");
            OutputFormat format = OutputFormat.createPrettyPrint(); // 创建文件输出的时候，自动缩进的格式
            format.setEncoding("UTF-8");//设置编码
            XMLWriter xw = new XMLWriter(pw, format);
            xw.write(doc);
            xw.flush();
            xw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Over");
        }
    }

    public void parseXML() {
        String rootPath = getClass().getClassLoader().getResource("").toString();
        rootPath = rootPath.substring(rootPath.indexOf("/"));
        File myXML = new File(rootPath + "mod.xml");
        SAXReader sr = new SAXReader();
        try {
            Document doc = sr.read(myXML);
            System.out.println(doc.asXML());    //Document 转成String

            Element root = doc.getRootElement();
            Map<String, Object> values = new HashMap<String, Object>();

            getAllElements(root,values);        //将Xml文件的全部节点获取Text转成Map格式
            Set<String> keys=values.keySet();
            for (String string:keys) {
                System.out.println(string+":"+values.get(string));
            }

            Element e=   root.element("CONTROL");  //获取Element下的某一节点
            Element el=   e.element("REQUEST_ID");
            System.out.println(el.getText());


            for (Iterator fathers = root.elementIterator(); fathers.hasNext(); ) {  //遍历输出Xml节点元素
                Element father = (Element) fathers.next();
                System.out.println("    "+father.getName()+"---"+father.getText().trim());
                for (Iterator childs = father.elementIterator(); childs.hasNext(); ) {
                    Element child = (Element) childs.next();
                    System.out.println("        "+child.getName()+"--"+child.getText().trim());
                    for (Iterator nodes = child.elementIterator(); nodes.hasNext(); ) {
                        Element node = (Element) nodes.next();
                        System.out.println("            "+node.getName() + "-----" + node.getText().trim());
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    public void getAllElements(Element element, Map<String, Object> values) {

        Iterator childs = element.elementIterator();
        while (childs.hasNext()) {
            Element child = (Element) childs.next();
            values.put(child.getName(), child.getTextTrim());
            getAllElements(child, values);
        }
    }

    public static void main(String[] args) {
        new Dom4jDemo().parseXML();
    }
}
