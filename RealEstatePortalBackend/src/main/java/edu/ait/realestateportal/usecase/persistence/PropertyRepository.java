package edu.ait.realestateportal.usecase.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ait.realestateportal.usecase.entities.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {

}
