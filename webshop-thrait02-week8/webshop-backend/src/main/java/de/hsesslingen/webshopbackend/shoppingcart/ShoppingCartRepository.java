package de.hsesslingen.webshopbackend.shoppingcart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("ShoppingCartRepository")
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {}