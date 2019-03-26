package com.saaolheart.mumbai.treatment.doctorconsultation;

import org.springframework.stereotype.Repository;

import com.saaolheart.mumbai.configuration.repositoryconfig.CustomRepository;

@Repository
public interface DoctorConsultationRepository extends CustomRepository<DoctorConsultationDomain, Long> {

}
