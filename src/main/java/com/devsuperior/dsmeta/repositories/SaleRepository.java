package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleSellerMinDTO;
import com.devsuperior.dsmeta.dto.SummarySaleSellerDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleSellerMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
			+ "FROM Sale obj "
			+ "WHERE (obj.date BETWEEN :minDate AND :maxDate) "
			+ "AND (UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')))")
	Page<SaleSellerMinDTO> getReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SummarySaleSellerDTO(obj.seller.name, SUM(obj.amount)) "
			+ "FROM Sale obj "
			+ "WHERE (obj.date BETWEEN :minDate AND :maxDate) "
			+ "GROUP BY obj.seller.name")
	List<SummarySaleSellerDTO> getSummary(LocalDate minDate, LocalDate maxDate);
	
}
