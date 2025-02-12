package com.lucas.agenda.serviceTests;

import com.lucas.agenda.model.Work;
import com.lucas.agenda.repository.WorkRepository;
import com.lucas.agenda.service.WorkService;
import com.lucas.agenda.validations.ValidationWorkMethods;
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
public class WorkServiceTests {

    @Mock
    private WorkRepository repository;

    @Mock
    private ValidationWorkMethods validation;

    @InjectMocks
    private WorkService service;

    private Work work;

    @BeforeEach
    public void setup(){

        Calendar simulatedDate = Calendar.getInstance();
        simulatedDate.set(2025, Calendar.MARCH, 10, 14, 30);
        work = new Work(
          "World war 2",
          "History",
          "The history of World war 2",
          simulatedDate
        );
    }


    @Test
    @DisplayName("Given Work Object When Save Expense Then Return Expense Object")
    void testGivenWorkObjectWhenSaveWorkThenReturnExpenseObject(){

        //Given / Arrange
        given(repository.save(work)).willReturn(work);
        //When / Act
        Work savedWork = service.create(work);
        Calendar savedDate = savedWork.getDate();
        //Then / Assert
        assertNotNull(savedWork);
        assertEquals("World war 2", work.getSubject());
        assertEquals("History", work.getName());
        assertEquals("The history of World war 2", work.getDescription());
        assertEquals(savedDate, work.getDate());
    }

    @Test
    @DisplayName("Given Null Parameters To Work Object When Save Work Then Throw IllegalArgumentException From Validation Class")
    void test_GivenNullParametersToWorkObject_WhenSaveWork_ThenThrowIllegalArgumentExceptionFromValidationClass(){

        //Given / Arrange
        Calendar simulatedDate = Calendar.getInstance();
        simulatedDate.set(2025, Calendar.MARCH, 10, 14, 30);
        Work invalidWork = new Work(
                "",
                "",
                "the",
                simulatedDate
        );
        doThrow(new IllegalArgumentException("Field or fields not filled"))
                .when(validation).validateCreateWork(any(Work.class));
        //When / Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> validation.validateCreateWork(invalidWork)
        );
        //Then / Assert
        assertEquals("Field or fields not filled", exception.getMessage());
    }

    @Test
    @DisplayName("Given Work List When allWorks Then Return Work List")
    void testGivenWorkListWhenAllWorksThenReturnWorkList(){
        //Given / Arrange
                Calendar simulatedDate = Calendar.getInstance();
        simulatedDate.set(2025, Calendar.MARCH, 10, 14, 30);
        Work work2 = new Work(
                "World war 3",
                "Histories",
                "The histories of World war 3",
                simulatedDate
        );
        repository.save(work);
        repository.save(work2);
        //When / Act
        given(repository.findAll()).willReturn(List.of(work, work2));
        List<Work> workList = service.findAll();
        //Then / Assert
        assertNotNull(workList);
        assertEquals(2, workList.size());
    }

    @Test
    @DisplayName("Given Work Id When FindById Then Return Work Object")
    void testGivenWorkId_WhenFindById_ThenReturnWorkObject(){
        //Given / Arrange
        work.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(work));
        //When / Act
        Work workOptional = service.findById(1L);
        //Then / Assert
        assertNotNull(workOptional);
        assertEquals(1L, workOptional.getId());
        assertEquals("World war 2", workOptional.getSubject());
    }

    @Test
    @DisplayName("Given Work Id When updateWork Then Return Work Object")
    void testGivenWorkId_WhenUpdateWork_ThenReturnUpdatedWorkObject(){
        //Given / Arrange
        work.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(work));
        given(repository.save(any(Work.class))).willReturn(work);
        //When / Act
        Work workToUpdate  = service.findById(1L);
        workToUpdate.setName("Algebra");
        workToUpdate.setSubject("Algebra 1");

        Work updatedWork = service.update(workToUpdate);
        //Then / Assert
        assertNotNull(updatedWork);
        assertEquals("Algebra 1", updatedWork.getSubject());
        assertEquals("Algebra", updatedWork.getName());
    }

    @Test
    @DisplayName("Given Work Id When deleteWork Then Return Nothing")
    void testGivenWorkId_WhenDeleteWork_ThenReturnNothing(){
        //Given / Arrange
        Long workId = 1L;
        given(repository.existsById(workId)).willReturn(true);
        willDoNothing().given(repository).deleteById(workId);
        //When / Act
        service.delete(workId);
        //Then / Assert
        verify(repository, times(1)).deleteById(workId);
    }
}
