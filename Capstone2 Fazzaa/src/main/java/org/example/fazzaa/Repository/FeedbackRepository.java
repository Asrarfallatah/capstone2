package org.example.fazzaa.Repository;

import org.example.fazzaa.Model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    Feedback findFeedbackByFeedbackId(Integer feedbackId);


    List<Feedback> findFeedbacksByRequestId(Integer requestId);
    List<Feedback> findFeedbacksByClientId(Integer clientId);
}
