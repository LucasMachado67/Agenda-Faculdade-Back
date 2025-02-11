package com.lucas.agenda.validations;

import com.lucas.agenda.model.Activity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidationActivityMethods {

    public void validateCreateActivity(Activity activity){

        if(activity.getName() == null || activity.getName().isEmpty() || activity.getName().length() > 30){
            throw new IllegalArgumentException("fill the field 'Name'");
        }else if(activity.getSubject() == null || activity.getSubject().isEmpty() || activity.getSubject().length() > 30){
            throw new IllegalArgumentException("fill the field 'Subject'");
        } else if(activity.getDescription() == null || activity.getDescription().isEmpty() || activity.getDescription().length() > 30) {
            throw new IllegalArgumentException("fill the field 'Description'");
        } else if(activity.getDate() == null || activity.getDate().before(LocalDate.now())) {
            throw new IllegalArgumentException("fill the field 'Date'");
        }
    }
}
