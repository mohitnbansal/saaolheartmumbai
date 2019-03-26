package com.saaolheart.mumbai.treatment.treatmentplan;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentPlanDetailsRepo extends JpaRepository<TreatmentPlanDetailDomain,Long> {
	
	Optional<List<TreatmentPlanDetailDomain>> findByIsTreatmentDoneIgnoreCaseAndTreatmentScheduledDateEqualsAndTreatmentType(String status, Date dt ,String type);	
}
