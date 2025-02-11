package com.lucas.agenda.validations;

import com.lucas.agenda.model.Work;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidationWorkMethods {

    public void validateCreateWork(Work work){

        if(work.getName() == null || work.getName().isEmpty() || work.getName().length() > 30){
            throw new IllegalArgumentException("fill the field 'Name'");
        }else if(work.getSubject() == null || work.getSubject().isEmpty() || work.getSubject().length() > 30){
            throw new IllegalArgumentException("fill the field 'Subject'");
        } else if(work.getDescription() == null || work.getDescription().isEmpty() || work.getDescription().length() > 30) {
            throw new IllegalArgumentException("fill the field 'Description'");
        } else if(work.getDate() == null || work.getDate().before(LocalDate.now())) {
            throw new IllegalArgumentException("fill the field 'Date'");
        }
    }
}
