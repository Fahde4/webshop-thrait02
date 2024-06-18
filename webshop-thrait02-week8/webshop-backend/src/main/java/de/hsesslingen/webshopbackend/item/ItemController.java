package de.hsesslingen.webshopbackend.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsesslingen.webshopbackend.item.ItemService;

import de.hsesslingen.webshopbackend.rating.RatingService;

@CrossOrigin(origins = "http://localhost:8106")
@RestController
@RequestMapping("/api-thrait02/item-management/v1/")
public class ItemController {

	@Autowired
	JmsTemplate jmsTemplate;

	@Value("${item.queue}")
	String queue;

	@Autowired
	ItemService itemService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

	@GetMapping("/items")
	public ResponseEntity<List<Item>> getAllItems(@RequestParam(required = false) String title) {
		
		try {
			List<Item> items = new ArrayList<Item>();

			if (title == null)
				itemService.findAll().forEach(items::add);
			else
				itemService.findByTitleContaining(title).forEach(items::add);

			if (items.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(items, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/items/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable("id") long id) {

		Optional<Item> itemData = itemService.findById(id);

		if (itemData.isPresent()) {
			return new ResponseEntity<>(itemData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/items")
	@Transactional
	public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {

  		jmsTemplate.setSessionTransacted(true);
		try {
			LOGGER.info("try to create database entry for '{}'",item.toJSONString());
			Item _item = itemService
				.save(new Item(item.getTitle(), item.getDescription(), item.getPrice(), item.getAvailability()));
			LOGGER.info("created database entry for '{}'",item.toJSONString());

			LOGGER.info("try to send message '{}'", item.toJSONString());
			jmsTemplate.convertAndSend(queue, item.toJSONString());
			LOGGER.info("message sent '{}'", item.toJSONString());

			return new ResponseEntity<>(_item, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/items/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable("id") long id, @Valid @RequestBody Item item) {
		Optional<Item> itemData = itemService.findById(id);
		
		if (itemData.isPresent()) {
			Item _item = itemData.get();
			_item.setTitle(item.getTitle());
			_item.setDescription(item.getDescription());
			_item.setPrice(item.getPrice());
			_item.setAvailability(item.getAvailability());
			return new ResponseEntity<>(itemService.save(_item), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/items/{id}")
	public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") long id) {
		try {
			itemService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/items")
	public ResponseEntity<HttpStatus> deleteAllItems() {
		try {
			itemService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}