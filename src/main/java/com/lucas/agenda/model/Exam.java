package com.lucas.agenda.model;


import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name =  "exam")
public class Exam {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String subject;
    private String name;
    private String description;
    private Calendar date;

    public Exam(){}

    public Exam(String subject, String name, String description, Calendar date) {
        this.subject = subject;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setDate(Calendar date){
        this.date = date;
    }

    public Calendar getDate(){
        return date;
    }

    //Calendar
    public int getYear() {
        return date.get(Calendar.YEAR);
    }

    public int getMonth() {
        return date.get(Calendar.MONTH);
    }

    public int getDay() {
        return date.get(Calendar.DAY_OF_MONTH);
    }

    public int getHour() {
        return date.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return date.get(Calendar.MINUTE);
    }
}
