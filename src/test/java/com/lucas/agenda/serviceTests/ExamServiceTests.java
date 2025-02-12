package com.lucas.agenda.serviceTests;


import com.lucas.agenda.model.Exam;
import com.lucas.agenda.repository.ExamRepository;
import com.lucas.agenda.service.ExamService;
import com.lucas.agenda.validations.ValidationExamMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ExamServiceTests {

    @Mock
    private ExamRepository repository;

    @Mock
    private ValidationExamMethods validation;

    @InjectMocks
    private ExamService service;

    private Exam exam;

    @BeforeEach
    public void setup(){
        Calendar simulatedDate = Calendar.getInstance();
        simulatedDate.set(2025, Calendar.MARCH, 10, 14, 30);
        exam = new Exam(
                "World war 2",
                "History",
                "The history of World war 2",
                simulatedDate
        );
    }

    @Test
    @DisplayName("Given Exam Object When Create Exam Then Return Saved Exam")
    void testGivenExamObject_WhenCreateExam_ThenReturnSavedExam(){
        //Given / Arrange
        given(repository.save(exam)).willReturn(exam);
        //When / Act
        Exam savedExam = service.create(exam);
        Calendar savedDate = savedExam.getDate();
        //Then / Assert
        assertNotNull(savedExam);
        assertEquals("World war 2", savedExam.getSubject());
        assertEquals("History", savedExam.getName());
        assertEquals("The history of World war 2", savedExam.getDescription());
        assertEquals(savedDate, savedExam.getDate());
    }

    @Test
    @DisplayName("Given Invalid Exam Object When Create Exam Then Return IllegalArgumentException From Validation Class")
    void testGivenInvalidExamObject_WhenCreateExam_ThenReturnIllegalArgumentExceptionFromValidationClass(){
        //Given / Arrange
        Calendar simulatedDate = Calendar.getInstance();
        simulatedDate.set(2025, Calendar.MARCH, 10, 14, 30);
        Exam invalidExam = new Exam(
                "",
                "",
                "",
                simulatedDate
        );
        doThrow(new IllegalArgumentException("Field or Fields invalids"))
                .when(validation).validateCreateExam(any(Exam.class));
        //When / Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () ->  validation.validateCreateExam(invalidExam)
        );
        //Then / Assert
        assertEquals("Field or Fields invalids", exception.getMessage());
    }

    @Test
    @DisplayName("Given Exam List When AllExams Then Return Exam List")
    void testGivenExamList_WhenAllExams_ThenReturnExamList(){
        //Given / Arrange
        Calendar simulatedDate = Calendar.getInstance();
        simulatedDate.set(2025, Calendar.MARCH, 10, 14, 30);
        Exam exam1 = new Exam(
                "Algebra",
                "Algebra 1",
                "Exam of Algebra",
                simulatedDate
        );
        repository.save(exam);
        repository.save(exam1);
        //When / Act
        given(repository.findAll()).willReturn(List.of(exam, exam1));
        List<Exam> examList = service.findAll();
        //Then / Assert
        assertEquals(2, examList.size());
    }

    @Test
    @DisplayName("Given ExamId When FindById Then Return Exam Object")
    void testGivenExamId_WhenFindById_ThenReturnExamObject(){
        //Given / Arrange
        exam.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(exam));
        //When / Act
        service.findById(1L);
        //Then / Assert
        assertNotNull(exam);
        assertEquals("World war 2", exam.getSubject());
        assertEquals("History", exam.getName());
        assertEquals("The history of World war 2", exam.getDescription());
    }

    @Test
    @DisplayName("Given ExamId When Update Exam Then Return Updated Exam Object")
    void testGivenExamId_WhenUpdateExam_ThenReturnUpdatedExamObject(){
        //Given / Arrange
        exam.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(exam));
        given(repository.save(any(Exam.class))).willReturn(exam);
        //When / Act
        Exam examToUpdate  = service.findById(1L);
        examToUpdate.setName("Algebra");
        examToUpdate.setSubject("Algebra 1");

        Exam updatedExam = service.update(examToUpdate);
        //Then / Assert
        assertNotNull(updatedExam);
        assertEquals("Algebra 1", updatedExam.getSubject());
        assertEquals("Algebra", updatedExam.getName());
    }

    @Test
    @DisplayName("Given ExamId When Delete Exam Then Return Nothing")
    void testGivenExamId_WhenDeleteExam_ThenReturnNothing(){
        //Given / Arrange
        Long examId = 1L;
        given(repository.existsById(1L)).willReturn(true);
        willDoNothing().given(repository).deleteById(examId);
        //When / Act
        service.delete(examId);
        //Then / Assert
        verify(repository, times(1)).deleteById(examId);
    }



}
