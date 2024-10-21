package com.calendar.service;

import com.calendar.dto.FreeSlotRequest;
import com.calendar.dto.MeetingRequest;
import com.calendar.dto.TimeSlot;
import com.calendar.entity.Employee;
import com.calendar.entity.Meeting;
import com.calendar.repository.EmployeeRepository;
import com.calendar.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void bookMeeting(MeetingRequest request) {
        List<Employee> participants = employeeRepository.findAllById(request.getParticipantIds());
        
        if (participants.stream().anyMatch(emp -> hasMeetingConflict(emp.getId(), request.getStartTime(), request.getEndTime()))) {
            throw new IllegalStateException("One or more participants have a scheduling conflict");
        }

        Meeting meeting = new Meeting();
        meeting.setParticipants(participants);
        meeting.setStartTime(request.getStartTime());
        meeting.setEndTime(request.getEndTime());

        meetingRepository.save(meeting);
    }

    public List<TimeSlot> findFreeSlots(FreeSlotRequest request) {
        List<Meeting> emp1Meetings = meetingRepository.findByParticipants_Id(request.getEmp1Id());
        List<Meeting> emp2Meetings = meetingRepository.findByParticipants_Id(request.getEmp2Id());

        // Logic to find free slots between emp1 and emp2
        return FreeSlotFinder.findFreeSlots(emp1Meetings, emp2Meetings, request.getMeetingDuration());
    }

    public List<String> findConflicts(MeetingRequest request) {
        return request.getParticipantIds().stream()
                .filter(empId -> hasMeetingConflict(empId, request.getStartTime(), request.getEndTime()))
                .map(empId -> employeeRepository.findById(empId).orElseThrow().getName())
                .collect(Collectors.toList());
    }

    private boolean hasMeetingConflict(Long empId, LocalDateTime start, LocalDateTime end) {
        List<Meeting> meetings = meetingRepository.findByParticipants_Id(empId);
        return meetings.stream().anyMatch(m -> isOverlapping(m.getStartTime(), m.getEndTime(), start, end));
    }

    private boolean isOverlapping(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return !(start1.isAfter(end2) || end1.isBefore(start2));
    }
}
