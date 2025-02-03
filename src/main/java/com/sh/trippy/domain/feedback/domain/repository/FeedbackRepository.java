package com.sh.trippy.domain.feedback.domain.repository;

import com.sh.trippy.domain.feedback.domain.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
