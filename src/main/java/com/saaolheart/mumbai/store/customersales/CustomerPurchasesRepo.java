package com.saaolheart.mumbai.store.customersales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPurchasesRepo extends JpaRepository<CustomerPurchasesDomain, Long>{

}
