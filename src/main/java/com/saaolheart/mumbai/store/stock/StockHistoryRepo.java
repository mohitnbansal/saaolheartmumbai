package com.saaolheart.mumbai.store.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHistoryRepo extends JpaRepository<StockHistoryDetailsDomain, Long> {

}
