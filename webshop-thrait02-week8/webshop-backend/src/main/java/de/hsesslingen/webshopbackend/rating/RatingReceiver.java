package de.hsesslingen.webshopbackend.rating;

import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class RatingReceiver {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(RatingReceiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  public CountDownLatch getLatch() {
    return latch;
  }

  @JmsListener(destination = "${item.queue}")
  public void receive(String message) {
    LOGGER.info("received message='{}'", message);
    latch.countDown();
    RatingService ratingService = new RatingService();
	  ratingService.createItemForRatings(message);
  }
}