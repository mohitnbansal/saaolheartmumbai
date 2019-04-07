package com.saaolheart.mumbai.store.customersales;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPurchasesRepo extends JpaRepository<CustomerPurchasesDomain, Long>{

	Optional<List<CustomerPurchasesDomain>> findByStockDomainId(Long id);

}
