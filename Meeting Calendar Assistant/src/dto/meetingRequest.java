package com.calendar.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingRequest {
    private List<Long> participantIds;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // Getters and setters
}
