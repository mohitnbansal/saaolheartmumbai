package com.saaolheart.mumbai.masters.invoice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceTypeMasterRepo extends JpaRepository<InvoiceTypeMaster,Long>{
	
	

}
