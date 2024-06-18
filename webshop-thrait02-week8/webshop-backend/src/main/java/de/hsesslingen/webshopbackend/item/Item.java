package de.hsesslingen.webshopbackend.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;

@Entity
@Table(name = "item")
public class Item implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Column(name = "description")
    @NotBlank(message = "Description is mandatory")
    private String description;

    @Column(name = "price")
    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/)
    private BigDecimal price;

    @Column(name= "availability")
    @Positive
    private int availability;    

    public Item() {
        
    }


    public Item(String title, String description, BigDecimal price, int availability) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.availability = availability;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", price=" + price + ", availability=" + availability +"]";
    }
    public String toJSONString() {
        return "{\"title\" : \"" + title + ", \"description\" : \"" + description + "\", \"price\" : \"" + price + "\", \"availability\" : \"" + availability + "\"}";
    }

}