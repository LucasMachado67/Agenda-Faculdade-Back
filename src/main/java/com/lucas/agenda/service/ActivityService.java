package com.lucas.agenda.service;

import com.lucas.agenda.model.Activity;
import com.lucas.agenda.repository.ActivityRepository;
import com.lucas.agenda.validations.ValidationActivityMethods;
import com.lucas.agenda.validations.ValidationExamMethods;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;
    @Autowired
    private ValidationActivityMethods validation;

    public Activity create(Activity activity) {
        try {
            validation.validateCreateActivity(activity);
            return repository.save(activity);

        } catch (RuntimeException e) {
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public List<Activity> findAll() {
        try {
            return repository.findAll();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("No data Found: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public Activity findById(long id) {
        try {
            return repository.findById(id).orElseThrow();
        } catch (RuntimeException e) {
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public Activity update(Activity activity) {
        try {
            var entity = repository.findById(activity.getId()).orElseThrow();
            entity.setName(activity.getName());
            entity.setDescription(activity.getDescription());

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
            calendar.set(activity.getYear(), activity.getMonth(), activity.getDay(), activity.getHour(), activity.getMinute(), 0);
            entity.setDate(calendar);
            entity.setDate(activity.getDate());
            return repository.save(activity);
        } catch (RuntimeException e) {
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public void delete(long id) throws RuntimeException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Error: data not found for id " + id);
        }
        repository.deleteById(id);
    }
}
