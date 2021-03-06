package com.saaolheart.mumbai.store.stock;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saaolheart.mumbai.customer.CustomerDetail;

@Repository
public interface StockRepo extends JpaRepository<StockDomain,Long> {
	
	public List<StockDomain> findAllByOrderByAddedOnDesc();


	Optional<List<StockDomain>> findByStockNameContaining(String name);


	public Optional<List<StockDomain>> findByQtyOfStockAvailableGreaterThanEqual(Long limit);


	public Optional<List<StockDomain>> findByStockNameIgnoreCaseLike(String searchParam);


	public Optional<List<StockDomain>> findByQtyOfStockAvailableLessThanEqual(Long limit);
}
