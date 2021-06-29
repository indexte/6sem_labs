package com.example.oop.hotel;


import com.example.oop.hotel.entities.Feedback;
import com.example.oop.hotel.entities.Status;
import com.example.oop.hotel.entities.User;
import com.example.oop.hotel.repositories.FeedbackRepository;
import com.example.oop.hotel.services.FeedbackService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class FeedbackServiceTest {
    @MockBean
    private FeedbackRepository feedbackRepository;


    @Autowired
    private FeedbackService feedbackService;


    @Test
    public void getAllFeedbacksByUser() {
        List<Feedback> list = new ArrayList<>();
        User user = new User();
        user.setName("Ivan");
        user.setId(1L);

        when(feedbackRepository.getAllByUserId(any())).thenReturn(list);

        assertEquals(list, feedbackService.getAllFeedbacks());

        verify(feedbackRepository, times(1)).getAllByUserId(user);
    }

    @Test
    public void createNewFeedbackByUser() throws Exception {
        Feedback feedback = new Feedback();
        Feedback feedbackDTO = new Feedback();
        User user = new User();
        user.setName("Ivan");

        when(feedbackRepository.save(any())).thenReturn(feedback);

        assertEquals(feedback, feedbackService.createNewFeedback(feedbackDTO));

        verify(feedbackRepository, times(1)).save(any());
    }

}
