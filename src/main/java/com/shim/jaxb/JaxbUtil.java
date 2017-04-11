package com.shim.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by xn064961 on 2017/2/9.
 */
public class JaxbUtil {

    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    private static String convertToXml(Object obj, String encoding){
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 转换xml的同时进行格式化
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T convertToJavaBean(String xml, Class<T> clz){
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return t;
    }

}
