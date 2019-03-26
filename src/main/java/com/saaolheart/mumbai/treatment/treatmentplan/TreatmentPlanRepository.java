package com.saaolheart.mumbai.treatment.treatmentplan;

import org.springframework.stereotype.Repository;

import com.saaolheart.mumbai.configuration.repositoryconfig.CustomRepository;

@Repository
public interface TreatmentPlanRepository extends CustomRepository<TreatmentPlanDomain, Long> {

}
