package de.hsesslingen.webshopbackend.rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class RatingService {
    
    private static final Logger LOGGER =
    LoggerFactory.getLogger(RatingService.class);

    public void createItemForRatings(String item){
        LOGGER.info("Rating service invoked for '{}'", item);
        // Processing takes very long
        try {
        TimeUnit.MILLISECONDS.sleep(5000);
        } catch (Exception e) {               
            e.printStackTrace();
        }
        LOGGER.info("created rating for '{}'", item);
    }

}