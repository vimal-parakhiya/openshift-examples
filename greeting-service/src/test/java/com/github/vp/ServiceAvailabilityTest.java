package com.github.vp;

import com.github.vp.statistics.DownTimeTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

/**
 * Created by vimalpar on 03/06/17.
 */
public class ServiceAvailabilityTest {
    private final static Logger logger = LoggerFactory.getLogger(ServiceAvailabilityTest.class);

    public static class Response {
        private int id;
        private String content;

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    ", content='" + content + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static void main(String[] args) {
        DownTimeTracker downTimeTracker = new DownTimeTracker();
        for(int i=0; i<50000; ++i) {
            logger.info("Iteration: {}", i);
            if(!isServiceAvailable()) {
                downTimeTracker.serviceDown();
            } else {
                downTimeTracker.serviceUp();
            }
        }

        logger.info("Down Time Statistics: {}", downTimeTracker);
    }

    private static boolean isServiceAvailable() {
        try {
            long start = System.currentTimeMillis();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Response> response = restTemplate.getForEntity("http://greeting.192.168.64.2.nip.io/greeting", Response.class);
            long end = System.currentTimeMillis();
            logger.info("Response: {} Time Spent: {}", response.getBody(), (end - start));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
