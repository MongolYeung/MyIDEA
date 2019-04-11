package com.itheima.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 *
 *bean 工厂
 *
 *
 */
public class BeanFactory {
    /**
     * 单例设计模式
     */
    private static Document document=null;
    static{
        SAXReader saxReader = new SAXReader();

        InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        try {
            document = saxReader.read(in);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    public static <T>T newInstance(Class<T> interfaceClass){
        try {
            //传进来参数是接口
            //我的要求 是interfaceClass类的对象
            //获取接口的名字
            String interfaceName = interfaceClass.getSimpleName();


            //返回该类型的实现类对象
            //根据你给我类型  去配置文件找到 你想要的具体实现类的全限定名
            String implClassName=getClassName(interfaceName);
            //获取字节码对象
            Class<?> implClass = Class.forName(implClassName);

            //反射创建对象
            Object o = implClass.newInstance();


            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static String getClassName(String interfaceName) throws DocumentException {


        Element node = (Element) document.selectSingleNode("//bean[@name='" + interfaceName + "']");

        return node.attributeValue("class");
    }


}
