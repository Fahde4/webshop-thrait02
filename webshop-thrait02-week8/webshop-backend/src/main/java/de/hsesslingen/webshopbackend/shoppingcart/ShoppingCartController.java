package de.hsesslingen.webshopbackend.shoppingcart;

import java.util.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsesslingen.webshopbackend.item.Item;

import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin(origins = "http://localhost:8206")
@RestController
@RequestMapping("/api-thrait02/shopping-cart-management/v1")
@Service("ShoppingCartController")

public class ShoppingCartController {

  @Autowired
  ShoppingCartRepository ShoppingCartRepository;


  @PostMapping("/GetShoppingCart")
  public ResponseEntity<ShoppingCart>getShoppingCart(@RequestParam(required = false) String sessionID) {

    Optional<ShoppingCart> ShoppingCart = ShoppingCartRepository.findById(sessionID);

        if (ShoppingCart.isPresent()) {
          return new ResponseEntity<>(ShoppingCart.get(), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
  }

  @PostMapping("/PutItemsToShoppingCart")
  public ResponseEntity<ShoppingCart> putItemsToShoppingCart(@RequestParam(required = false) String sessionID, @RequestBody List<Item> items) {
    
    
    ShoppingCart _ShoppingCart = new ShoppingCart(sessionID, items);
    ShoppingCartRepository.save(_ShoppingCart);

    return new ResponseEntity<>(_ShoppingCart, HttpStatus.CREATED);
  }

  @PostMapping("/DeleteShoppingCart")
  public ResponseEntity<HttpStatus> deleteShoppingCart(@RequestParam(required = false) String sessionID) {
  
    try {
      ShoppingCartRepository.deleteById(sessionID);;
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}