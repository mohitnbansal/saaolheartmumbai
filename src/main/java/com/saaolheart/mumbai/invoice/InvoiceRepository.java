package com.saaolheart.mumbai.invoice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.saaolheart.mumbai.configuration.repositoryconfig.CustomRepository;

@Repository
public interface InvoiceRepository extends CustomRepository<InvoiceDomain, Long> {

	
Optional<List<InvoiceDomain>> findByBalanceAmtGreaterThan(Double d);
}
