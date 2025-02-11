package com.lucas.agenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class ManagementAgendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementAgendaApplication.class, args);
//		Calendar c = Calendar.getInstance();
//		c.set(2013, Calendar.FEBRUARY, 28);
//		Date data = c.getTime();
//		System.out.println("Data atual sem formatação: "+data);
//
//		//Formata a data
//		DateFormat formataData = DateFormat.getDateInstance();
//		System.out.println("Data atual com formatação: "+ formataData.format(data));
//
//		//Formata Hora
//		DateFormat hora = DateFormat.getTimeInstance();
//		System.out.println("Hora formatada: "+hora.format(data));
//
//		//Formata Data e Hora
//		DateFormat dtHora = DateFormat.getDateTimeInstance();
//		System.out.println(dtHora.format(data));
	}

}
