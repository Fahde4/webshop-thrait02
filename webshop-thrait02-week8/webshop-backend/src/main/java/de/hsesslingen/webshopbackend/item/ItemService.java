package de.hsesslingen.webshopbackend.item;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@EnableCaching
public class ItemService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemRepository itemRepository;

    @Cacheable(value = "itemCache")
    public Optional<Item> findById(long id) {

        LOGGER.info("invoked repository for='{}'", id);
        Optional<Item> itemData = itemRepository.findById(id);

        return itemData; 
    }

    public Iterable<Item> findAll(){
      return itemRepository.findAll();
    }
    public Iterable<Item> findByTitleContaining(String title){
      return itemRepository.findByTitleContaining(title);
    }
    public Item save(Item item){
      return itemRepository.save(item);
    }
    public void deleteById(long id){
      itemRepository.deleteById(id);
    }
    public void deleteAll(){
      itemRepository.deleteAll();
    }

}