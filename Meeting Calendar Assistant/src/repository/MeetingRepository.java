package com.calendar.repository;

import com.calendar.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByParticipants_Id(Long participantId);
}
