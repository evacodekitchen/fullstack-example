package com.evacodekitchen.realestateportalserver.rest.dto;

import java.util.List;

import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

public class PropertyPageDTO {

	private List<Property> propertyList;
	private int totalNrOfPages;

	public PropertyPageDTO() {
	}

	public PropertyPageDTO(List<Property> propertyList, int totalNrOfPages) {
		this.propertyList = propertyList;
		this.totalNrOfPages = totalNrOfPages;
	}

	public List<Property> getPropertyList() {
		return propertyList;
	}

	public int getTotalNrOfPages() {
		return totalNrOfPages;
	}

	public void setPropertyList(List<Property> propertyList) {
		this.propertyList = propertyList;
	}

	public void setTotalNrOfPages(int totalNrOfPages) {
		this.totalNrOfPages = totalNrOfPages;
	}

}
