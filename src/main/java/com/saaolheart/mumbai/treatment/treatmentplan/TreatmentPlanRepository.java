package com.saaolheart.mumbai.treatment.treatmentplan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlanDomain, Long> {

}
