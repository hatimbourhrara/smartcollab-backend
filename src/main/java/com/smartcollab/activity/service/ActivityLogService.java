package com.smartcollab.activity.service;

import com.smartcollab.activity.model.ActivityLog;
import com.smartcollab.activity.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogService(
            ActivityLogRepository activityLogRepository
    ) {
        this.activityLogRepository = activityLogRepository;
    }

    public void logActivity(
            String action,
            String userEmail
    ) {

        ActivityLog activityLog = new ActivityLog();

        activityLog.setAction(action);
        activityLog.setUserEmail(userEmail);

        activityLogRepository.save(activityLog);
    }

    public List<ActivityLog> getUserActivities(
            String userEmail
    ) {

        return activityLogRepository
                .findByUserEmailOrderByCreatedAtDesc(userEmail);
    }
}