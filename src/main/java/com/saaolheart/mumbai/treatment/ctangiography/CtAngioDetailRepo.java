package com.saaolheart.mumbai.treatment.ctangiography;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CtAngioDetailRepo extends JpaRepository<CtAngioDetailsDomain, Long>{

}
