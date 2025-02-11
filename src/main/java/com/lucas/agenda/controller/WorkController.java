package com.lucas.agenda.controller;

import com.lucas.agenda.model.Work;
import com.lucas.agenda.service.WorkService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/work")
public class WorkController {

    @Autowired
    private WorkService service;

    @PostMapping("/new")
    public ResponseEntity<?> createWork(@Valid @RequestBody Work work){
        try {
            return ResponseEntity.ok(service.create(work));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<Work> findAll(){
        try {
            return service.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while trying to fetch data: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Work> findById(@PathVariable long id){
        try{
            return ResponseEntity.ok(service.findById(id));
        }catch (EntityNotFoundException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }catch (Exception e ){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while trying to fetch data: " + id);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Work> updateWork(@PathVariable long id, @RequestBody Work work){
        try {
            return ResponseEntity.ok(service.update(work));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Data not found with ID: " + id);
        } catch (Exception e ){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred while trying to fetch data: " + e.getMessage());
        }
    }

    @DeleteMapping("/[{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable long id){
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with ID: " + id + " does not exist");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data received: " + id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred: " + e.getMessage());
        }
    }
}
