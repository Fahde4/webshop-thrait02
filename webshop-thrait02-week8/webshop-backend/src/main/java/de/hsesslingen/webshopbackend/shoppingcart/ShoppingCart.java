package de.hsesslingen.webshopbackend.shoppingcart;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.redis.core.RedisHash;
import de.hsesslingen.webshopbackend.item.Item;

@RedisHash("ShoppingCart")

public class ShoppingCart implements Serializable {

  private String id;
  private List<Item> items = new ArrayList<Item>();
    
  public ShoppingCart(String id, List<Item> items) {
    this.id = id;
    this.items = items;
  }

  public String getID() {
    return id;
  }

  public void setID(String id) {
    this.id = id;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
    
  @Override
  public String toString() {
    return "ShoppingCart{" + "item='" + id + '}';
  }
}