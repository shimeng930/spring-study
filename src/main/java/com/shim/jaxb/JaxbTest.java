package com.shim.jaxb;

/**
 * Created by xn064961 on 2017/2/9.
 */
public class JaxbTest {

    public static void main(String[] s){
        marshaller();
//        unMarshaller();
    }

    public static void marshaller(){
        Book book = new Book();
        book.setId(100);
        book.setAuthor("James");
        book.setName("Java");
        book.setPrice("23.45");

        Person p = new Person();
        p.setName("James");
        p.setAge(30);

        book.setAuthors(p);

        String str = JaxbUtil.convertToXml(book);
        System.out.println(str);
    }

    public static void unMarshaller() {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
                "<book>"+
                "    <id>12</id>"+
                "    <name>java</name>"+
                "    <price>12.34</price>"+
                "    <author>班长</author>"+
                "</book>";
        Book book = JaxbUtil.convertToJavaBean(str, Book.class);
        System.out.println(book);
    }
}
