package com;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class EnergyMeterService {

     @Autowired
     private EnergyMeterRepository energyMeterRepository;
     
     @PostConstruct
     public void insertEnergyMeterValues() {
    	 
         LocalDateTime currentTimestamp = LocalDateTime.now();
         LocalDateTime startTimestamp = currentTimestamp.minusHours(5);

         float hourValue = 0;
         float reminder = 0;
         Random random = new Random();

         for (int i = 0; i < 200; i++) {
             LocalDateTime timestamp = startTimestamp.plusMinutes(i);
             float minuteValue = random.nextFloat() * 100; // Generate random minute value
             reminder += minuteValue;

             if(timestamp.getMinute() == 0) {
            	 hourValue = reminder;
            	 reminder = 0;
            	 EnergyMeter energyMeter = new EnergyMeter();
                 energyMeter.setTimeStamp(timestamp);
                 energyMeter.setMinuteValue(minuteValue);
                 energyMeter.setHourValue(hourValue);

                 energyMeterRepository.save(energyMeter);
                 hourValue = 0;
             }
             else {
            	 EnergyMeter energyMeter = new EnergyMeter();
                 energyMeter.setTimeStamp(timestamp);
                 energyMeter.setMinuteValue(minuteValue);
                 energyMeter.setHourValue(hourValue);

                 energyMeterRepository.save(energyMeter);
             }
         }
     }
     
     public EnergyMeter insertEnergyMeterValues(EnergyMeter energyMeter){
 		return energyMeterRepository.save(energyMeter);
 	}
     
     public List<EnergyMeter> refresh(){
    	 List<EnergyMeter> energyMeterReadings = energyMeterRepository.findAllByOrderByTimeStampAsc();
    	 
         float hourValue = 0;
         float reminder = 0;

         // Loop through each existing reading
         for (EnergyMeter reading : energyMeterReadings) {
             float minuteValue = reading.getMinuteValue();

             reminder += minuteValue;

             if (reading.getTimeStamp().getMinute() == 0) {
                 hourValue = reminder;
                 reminder = 0;
                 reading.setHourValue(hourValue);
                 energyMeterRepository.save(reading);
                 hourValue = 0;
             }
             else {
            	 reading.setHourValue(hourValue);
                 energyMeterRepository.save(reading);
             }
         }
         return energyMeterReadings;
     }
     
     public List<EnergyMeter> getHourly(){
    	 List<EnergyMeter> energyMeterReadings = energyMeterRepository.findAllByOrderByTimeStampAsc();
    	 
         float hourValue = 0;
         float reminder = 0;

         List<EnergyMeter> onlyHour = new ArrayList<>();
         // Loop through each existing reading
         for (EnergyMeter reading : energyMeterReadings) {
             float minuteValue = reading.getMinuteValue();

             reminder += minuteValue;

             if (reading.getTimeStamp().getMinute() == 0) {
                 hourValue = reminder;
                 reminder = 0;
                 reading.setHourValue(hourValue);
                 onlyHour.add(reading);
                 hourValue = 0;
             }
             else {
            	 reading.setHourValue(hourValue);
             }
         }
         return onlyHour;
     }
     
     public List<EnergyMeter> fetchTableValues() {
    	 
         List<EnergyMeter> energyMeterReadings = energyMeterRepository.findAll();

         List<String> tableValues = new ArrayList<>();

         for (EnergyMeter reading : energyMeterReadings) {
             LocalDateTime timestamp = reading.getTimeStamp();
             float minuteValue = reading.getMinuteValue();
             float hourValue = reading.getHourValue();

             String formattedTimestamp = timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

             tableValues.add("Timestamp: " + formattedTimestamp +
                             ", Minute Value: " + minuteValue +
                             ", Hour Value: " + hourValue);
         }

         for (String tableValue : tableValues) {
             System.out.println(tableValue);
         }

         return energyMeterReadings;
     }
}