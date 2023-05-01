package com.springboot.RateLimiter2;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class EndPoint {

    private final RateLimit rateLimit = new RateLimit();

    @GetMapping("/example")
    public ResponseEntity<String> exampleEndpoint(HttpServletRequest request) {
        try {
            if (rateLimit.checkRateLimit("exampleApiKey", "exampleUserId")) {
                // Your API logic here
                return ResponseEntity.ok("Success!");
            } else {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Retry-After", String.valueOf(RateLimit.DEFAULT_WINDOW_SIZE)); // retry after default window size
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                	    .headers(headers)
                	    .body("Too many requests. Please try again later.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
