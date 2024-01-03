package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSellerMinDTO;
import com.devsuperior.dsmeta.dto.SummarySaleSellerDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleSellerMinDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate dataAtual = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxDateParsed = maxDate == null ? dataAtual : LocalDate.parse(maxDate);
		LocalDate minDateParsed = minDate == null ? maxDateParsed.minusYears(1L) : LocalDate.parse(minDate);
		
		return repository.getReport(minDateParsed, maxDateParsed, name, pageable);
	}
	
	public List<SummarySaleSellerDTO> getSummary(String minDate, String maxDate) {
		LocalDate dataAtual = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxDateParsed = maxDate == null ? dataAtual : LocalDate.parse(maxDate);
		LocalDate minDateParsed = minDate == null ? maxDateParsed.minusYears(1L) : LocalDate.parse(minDate);
		
		return repository.getSummary(minDateParsed, maxDateParsed);
	}
	
}
