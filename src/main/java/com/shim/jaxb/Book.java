package com.shim.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xn064961 on 2017/2/9.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "BOOK")
public class Book {

//    @XmlElement(name = "XXXXXXXXXXXX")
    private Person authors;

    private String price;

    private String name;

    private String author;

    private int id;

    public Person getAuthors() {
        return authors;
    }

    public void setAuthors(Person authors) {
        this.authors = authors;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "authors=" + authors +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", id=" + id +
                '}';
    }
}
