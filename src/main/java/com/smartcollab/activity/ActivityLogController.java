package com.smartcollab.activity;

import com.smartcollab.activity.dto.ActivityLogResponse;
import com.smartcollab.activity.service.ActivityLogService;
import com.smartcollab.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
public class ActivityLogController {

    private final ActivityLogService activityLogService;
    private final JwtService jwtService;

    public ActivityLogController(
            ActivityLogService activityLogService,
            JwtService jwtService
    ) {
        this.activityLogService = activityLogService;
        this.jwtService = jwtService;
    }

    @GetMapping("/my")
    public List<ActivityLogResponse> getMyActivities(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        return activityLogService.getUserActivities(userEmail)
                .stream()
                .map(ActivityLogResponse::new)
                .collect(Collectors.toList());
    }
}