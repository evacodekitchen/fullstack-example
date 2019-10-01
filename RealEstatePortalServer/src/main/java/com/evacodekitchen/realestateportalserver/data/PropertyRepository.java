package com.evacodekitchen.realestateportalserver.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long>  {

	Page<Property> findByCityOrderById(String city, Pageable pageable);

	Page<Property> findAllByOrderByIdAsc(Pageable pageable);

}