package com.thisispit.tems.repository;

import com.thisispit.tems.entity.Feedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByRouteIdOrderByCreatedAtDesc(Long routeId);

    long countByRouteId(Long routeId);
}