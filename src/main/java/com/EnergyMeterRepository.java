package com;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyMeterRepository extends JpaRepository<EnergyMeter, Long> {
	EnergyMeter findTopByOrderByTimeStampDesc();
	List<EnergyMeter> findAllByOrderByTimeStampAsc();
}
