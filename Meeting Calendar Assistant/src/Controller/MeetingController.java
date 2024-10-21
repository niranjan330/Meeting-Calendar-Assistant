package com.calendar.controller;

import com.calendar.dto.FreeSlotRequest;
import com.calendar.dto.MeetingRequest;
import com.calendar.dto.TimeSlot;
import com.calendar.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/book")
    public ResponseEntity<String> bookMeeting(@RequestBody MeetingRequest request) {
        meetingService.bookMeeting(request);
        return ResponseEntity.ok("Meeting successfully booked.");
    }

    @PostMapping("/free-slots")
    public ResponseEntity<List<TimeSlot>> findFreeSlots(@RequestBody FreeSlotRequest request) {
        List<TimeSlot> freeSlots = meetingService.findFreeSlots(request);
        return ResponseEntity.ok(freeSlots);
    }

    @PostMapping("/conflicts")
    public ResponseEntity<List<String>> findMeetingConflicts(@RequestBody MeetingRequest request) {
        List<String> conflictingParticipants = meetingService.findConflicts(request);
        return ResponseEntity.ok(conflictingParticipants);
    }
}
