package com.saaolheart.mumbai.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRecieptDetailRepo extends JpaRepository<InvoiceRecieptDetailDomain, Long>{

}
