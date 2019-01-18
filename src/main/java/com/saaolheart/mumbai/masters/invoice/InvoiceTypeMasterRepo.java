package com.saaolheart.mumbai.masters.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceTypeMasterRepo extends JpaRepository<InvoiceTypeMaster,Long>{

}
