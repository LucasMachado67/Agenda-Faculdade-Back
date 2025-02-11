package com.lucas.agenda.service;

import com.lucas.agenda.model.Work;
import com.lucas.agenda.repository.WorkRepository;
import com.lucas.agenda.validations.ValidationActivityMethods;
import com.lucas.agenda.validations.ValidationExamMethods;
import com.lucas.agenda.validations.ValidationWorkMethods;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Service
public class WorkService {

    @Autowired
    private WorkRepository repository;
    @Autowired
    private ValidationWorkMethods validation;

    public Work create(Work work) {
        try {
            validation.validateCreateWork(work);
            return repository.save(work);
        } catch (RuntimeException e) {
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public List<Work> findAll() {
        try {
            return repository.findAll();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("No data Found: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public Work findById(long id) {
        try {
            return repository.findById(id).orElseThrow();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("No data Found: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public Work update(Work work) {
        try {
            var entity = repository.findById(work.getId()).orElseThrow();
            entity.setName(work.getName());
            entity.setDescription(work.getDescription());

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
            calendar.set(work.getYear(), work.getMonth(), work.getDay(), work.getHour(), work.getMinute(), 0);
            entity.setDate(calendar);
            entity.setDate(work.getDate());
            return repository.save(work);
        } catch (RuntimeException e) {
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public void delete(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Error: data not found for id " + id);
        }
        repository.deleteById(id);
    }
}
