881130283560133
JN182256970INpackage com.calendar.service;

import com.calendar.dto.MeetingRequest;
import com.calendar.entity.Employee;
import com.calendar.entity.Meeting;
import com.calendar.repository.EmployeeRepository;
import com.calendar.repository.MeetingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest
public class MeetingServiceTest {

    @Autowired
    private MeetingService meetingService;

    @MockBean
    private MeetingRepository meetingRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void testBookMeeting_noConflicts() {
        Employee emp1 = new Employee();
        emp1.setId(1L);
        Employee emp2 = new Employee();
        emp2.setId(2L);

        MeetingRequest request = new MeetingRequest();
        request.setParticipantIds(Arrays.asList(1L, 2L));
        request.setStartTime(LocalDateTime.now().plusDays(1));
        request.setEndTime(LocalDateTime.now().plusDays(1).plusHours(1));

        Mockito.when(employeeRepository.findAllById(Mockito.anyList())).thenReturn(Arrays.asList(emp1, emp2));

        meetingService.bookMeeting(request);
        Mockito.verify(meetingRepository, Mockito.times(1)).save(Mockito.any(Meeting.class));
    }
}
