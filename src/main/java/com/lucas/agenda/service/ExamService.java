package com.lucas.agenda.service;

import com.lucas.agenda.model.Exam;
import com.lucas.agenda.repository.ExamRepository;
import com.lucas.agenda.validations.ValidationExamMethods;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Service
public class ExamService {

    @Autowired
    private ExamRepository repository;
    @Autowired
    private ValidationExamMethods validation;

    public Exam create(Exam exam) {
        try {
            validation.validateCreateExam(exam);
            return repository.save(exam);
        } catch (RuntimeException e) {
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public List<Exam> findAll() {
        try{
            return repository.findAll();
        } catch (RuntimeException e ){
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public Exam findById(long id) {
        try{
            return repository.findById(id).orElseThrow();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("No data Found: " + e.getMessage());
        } catch (RuntimeException e ){
            throw new RuntimeException("Unknown error occurred: " + e.getMessage());
        }
    }

    public Exam update(Exam exam) {
        try {
            var entity = repository.findById(exam.getId()).orElseThrow();
            entity.setName(exam.getName());
            entity.setDescription(exam.getDescription());

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
            calendar.set(exam.getYear(), exam.getMonth(), exam.getDay(), exam.getHour(), exam.getMinute(), 0);
            entity.setDate(calendar);
            entity.setDate(exam.getDate());
            return repository.save(exam);
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
