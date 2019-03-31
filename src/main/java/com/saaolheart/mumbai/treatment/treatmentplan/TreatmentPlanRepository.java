package com.saaolheart.mumbai.treatment.treatmentplan;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.saaolheart.mumbai.configuration.repositoryconfig.CustomRepository;

@Repository
public interface TreatmentPlanRepository extends CustomRepository<TreatmentPlanDomain, Long> {

	Optional<List<TreatmentPlanDomain>> findByTreatmentStatusIgnoreCaseIn(Collection<String> status);  
}
