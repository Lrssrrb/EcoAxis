package com;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//EnergyController.java
@RestController
public class EnergyController {
	
	@Autowired
	private EnergyMeterService energyService;
	
	@PostMapping("")
	public EnergyMeter insertEnergyMeterValues(@RequestBody EnergyMeter energyMeter){
		EnergyMeter insertEnergyMeterValues = energyService.insertEnergyMeterValues(energyMeter);
		return insertEnergyMeterValues;
	}
	
	@GetMapping("/sortedByTime")
	public List<EnergyMeter> fetchSortedTable() {
		List<EnergyMeter> fetchTableValues = energyService.refresh();
		return fetchTableValues;
	}
	
	@GetMapping("/forEveryHour")
	public List<EnergyMeter> forEveryHour() {
		List<EnergyMeter> fetchTableValues = energyService.getHourly();
		return fetchTableValues;
	}
	

	@GetMapping("/all")
	public List<EnergyMeter> fetchTableValues() {
		List<EnergyMeter> fetchTableValues = energyService.fetchTableValues();
		return fetchTableValues;
	}
	
}

