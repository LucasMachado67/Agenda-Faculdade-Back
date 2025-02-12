package com.lucas.agenda.serviceTests;

import com.lucas.agenda.model.Activity;
import com.lucas.agenda.repository.ActivityRepository;
import com.lucas.agenda.service.ActivityService;
import com.lucas.agenda.validations.ValidationActivityMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTests {


    @Mock
    private ActivityRepository repository;

    @Mock
    private ValidationActivityMethods validation;

    @InjectMocks
    private ActivityService service;

    private Activity activity;

    @BeforeEach
    public void setup(){

        Calendar simulatedDate = Calendar.getInstance();
        simulatedDate.set(2025, Calendar.MARCH, 10, 14, 30);
        activity = new Activity(
                "World war 2",
                "History",
                "The history of World war 2",
                simulatedDate
        );
    }

    @Test
    @DisplayName("Given Activity Object When Save Activity Then Return Saved Activity")
    void testGivenActivityObject_WhenSaveActivity_ThenReturnSavedActivity(){
        //Given / Arrange
        given(repository.save(activity)).willReturn(activity);
        //When / Act
        Activity savedActivity = service.create(activity);
        Calendar savedDate = savedActivity.getDate();
        //Then / Assert
        assertNotNull(savedActivity);
        assertEquals("World war 2", savedActivity.getSubject());
        assertEquals("History", savedActivity.getName());
        assertEquals("The history of World war 2", savedActivity.getDescription());
        assertEquals(savedDate, savedActivity.getDate());
    }

    @Test
    @DisplayName("Given Invalid Activity Object When Save Activity Then Return IllegalArgumentException From Validation Class")
    void testGivenInvalidActivityObject_WhenSaveActivity_ThenReturnIllegalArgumentExceptionFromValidationClass(){
        //Given / Arrange
        Calendar simulatedDate = Calendar.getInstance();
        simulatedDate.set(2025, Calendar.MARCH, 10, 14, 30);
        Activity invalidActivity = new Activity(
                "",
                "",
                "",
                simulatedDate
        );
        doThrow(new IllegalArgumentException("Field or fields not filled"))
                .when(validation).validateCreateActivity(any(Activity.class));
        //When / Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> validation.validateCreateActivity(invalidActivity)
        );
        //Then / Assert
        assertEquals("Field or fields not filled", exception.getMessage());
    }

    @Test
    @DisplayName("Given Activity List When Find All Then Return Activity List")
    void testGivenActivityList_WhenFindAll_ThenReturnActivityList(){
        //Given / Arrange
        Calendar simulatedDate = Calendar.getInstance();
        simulatedDate.set(2025, Calendar.MARCH, 10, 14, 30);
        Activity activity1 = new Activity(
                "Algebra",
                "Algebra 1",
                "Exam of Algebra",
                simulatedDate
        );
        repository.save(activity);
        repository.save(activity1);
        //When / Act
        given(repository.findAll()).willReturn(List.of(activity, activity1));
        List<Activity> activityList = service.findAll();
        //Then / Assert
        assertEquals(2, activityList.size());
    }

    @Test
    @DisplayName("Given Activity Id When FindById Then Return Activity Object")
    void testGivenActivityId_WhenFindById_ThenReturnActivityObject(){
        //Given / Arrange
        activity.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(activity));
        //When / Act
        Activity capturedActivity = service.findById(1L);

        //Then / Assert
        assertEquals("World war 2", capturedActivity.getSubject());
        assertEquals("History", capturedActivity.getName());
        assertEquals("The history of World war 2", capturedActivity.getDescription());
    }

    @Test
    @DisplayName("Given Activity Id When UpdateActivity Then Return Updated Activity Object")
    void testGivenActivityId_WhenUpdateActivity_ThenReturnUpdatedActivityObject(){
        //Given / Arrange
        activity.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(activity));
        given(repository.save(any(Activity.class))).willReturn(activity);
        //When / Act
        Activity activityToUpdate = service.findById(1L);
        activityToUpdate.setSubject("Math");
        activityToUpdate.setName("Algebra");

        Activity updatedActivity = service.update(activityToUpdate);
        //Then / Assert
        assertEquals("Math", updatedActivity.getSubject());
        assertEquals("Algebra", updatedActivity.getName());
    }

    @Test
    @DisplayName("Given Activity Id When UpdateActivity Then Return Updated Activity Object")
    void testGivenActivityId_WhenDeleteActivity_ThenReturnNothing(){
        //Given / Arrange
        Long activityId = 1L;
        given(repository.existsById(activityId)).willReturn(true);
        willDoNothing().given(repository).deleteById(activityId);
        //When / Act

        service.delete(activityId);
        //Then / Assert
        verify(repository, times(1)).deleteById(activityId);
    }
}
