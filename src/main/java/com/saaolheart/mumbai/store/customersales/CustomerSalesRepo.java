package com.saaolheart.mumbai.store.customersales;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSalesRepo extends JpaRepository<CustomerSalesDomain, Long> {

	Optional<List<CustomerSalesDomain>> findByOrderByIdDesc();

}
