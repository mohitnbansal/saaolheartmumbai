package com.saaolheart.mumbai.masters.treatment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentTypeMasterRepo extends JpaRepository<TreatmentTypeMasterDomain, Long>{

}
