package com.saaolheart.mumbai.customer;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAppointmentRepo extends JpaRepository<CustomerAppointmentDomain, Long>{
	
	
	@Query("Select max(u.visitNumber) from CustomerAppointmentDomain u where u.customerId = :id and u.typeOfAppointment = :typeOfAppointment")
	Optional<Integer> findMaxVisitByCustomerId(@Param("id") Long id, @Param("typeOfAppointment") AppointmentType typeOfAppointment);
	
	Optional<List<CustomerAppointmentDomain>> findByIsVisitDoneIgnoreCaseAndTypeOfAppointmentIn(String isVisitDone, List<AppointmentType> appointmentList);

	Optional<List<CustomerAppointmentDomain>> findByTypeOfAppointmentIn(List<AppointmentType> appointmentType);


	Optional<List<CustomerAppointmentDomain>> findByTypeOfAppointmentInAndIsVisitDoneNotAndExpectedTime(
			List<AppointmentType> appointmentTypeList, String string, @Temporal(TemporalType.DATE) Date  dat);

	
	Optional<List<CustomerAppointmentDomain>> findByTypeOfAppointmentInAndExpectedTime(
			List<AppointmentType> appointmentTypeList,@Temporal(TemporalType.DATE)Date dat);

	Optional<List<CustomerAppointmentDomain>> findByTypeOfAppointmentInAndExpectedTimeBetween(
			List<AppointmentType> appointmentTypeList, @Temporal(TemporalType.DATE)Date dateStart, @Temporal(TemporalType.DATE)Date dateEnd);

	Optional<List<CustomerAppointmentDomain>> findByTypeOfAppointmentInAndExpectedTimeGreaterThanEqualAndExpectedTimeLessThan(
			List<AppointmentType> appointmentTypeList, Date dateStart, Date dateEnd);

}
