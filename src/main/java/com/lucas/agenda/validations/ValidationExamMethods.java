package com.lucas.agenda.validations;

import com.lucas.agenda.model.Exam;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidationExamMethods {

    public void validateCreateExam(Exam exam){

        if(exam.getName() == null || exam.getName().isEmpty() || exam.getName().length() > 30){
            throw new IllegalArgumentException("fill the field 'Name'");
        }else if(exam.getSubject() == null || exam.getSubject().isEmpty() || exam.getSubject().length() > 30){
            throw new IllegalArgumentException("fill the field 'Subject'");
        } else if(exam.getDescription() == null || exam.getDescription().isEmpty() || exam.getDescription().length() > 30) {
            throw new IllegalArgumentException("fill the field 'Description'");
        } else if(exam.getDate() == null || exam.getDate().before(LocalDate.now())) {
            throw new IllegalArgumentException("fill the field 'Date'");
        }
    }

}
