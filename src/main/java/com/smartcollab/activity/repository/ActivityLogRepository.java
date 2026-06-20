package com.smartcollab.activity.repository;

import com.smartcollab.activity.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    List<ActivityLog> findByUserEmailOrderByCreatedAtDesc(
            String userEmail
    );
}