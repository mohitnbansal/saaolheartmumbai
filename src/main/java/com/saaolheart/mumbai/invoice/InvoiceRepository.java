package com.saaolheart.mumbai.invoice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceDomain, Long> {

	
Optional<List<InvoiceDomain>> findByBalanceAmtGreaterThan(Double d);
}
