package com.evacodekitchen.realestateportalserver.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long>  {

}