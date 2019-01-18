package com.saaolheart.mumbai.treatment.doctorconsultation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorConsultationRepository extends JpaRepository<DoctorConsultationDomain, Long> {

}
