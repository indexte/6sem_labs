package com.example.oop.hotel.controller;

import com.example.oop.hotel.entities.Feedback;
import com.example.oop.hotel.services.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")

public class FeedbackController {

    private final FeedbackService reservService;

    public FeedbackController(FeedbackService reservService) {

        this.reservService = reservService;
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getAllReservs(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(reservService.findAllFeedbacksByUser(userId));
        } catch (Exception exception) {
            log.error("Feedback can`be receive: {}", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
