package com.evacodekitchen.realestateportalserver.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long>  {

	List<Property> findByCity(String city, Pageable pageable);

}