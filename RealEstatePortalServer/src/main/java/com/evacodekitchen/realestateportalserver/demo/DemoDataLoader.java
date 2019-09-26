package com.evacodekitchen.realestateportalserver.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.evacodekitchen.realestateportalserver.data.PropertyRepository;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;
import com.evacodekitchen.realestateportalserver.usecase.entity.PropertyType;
import com.evacodekitchen.realestateportalserver.usecase.entity.SaleOrRent;

@Component
public class DemoDataLoader implements CommandLineRunner {

	private final PropertyRepository repository;

	@Autowired
	public DemoDataLoader(PropertyRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... strings) throws Exception {
		byte[] byteOfPicture1 = getBytesOf("house1.jpg");
		byte[] byteOfPicture2 = getBytesOf("house2.jpg");
		byte[] byteOfPicture3 = getBytesOf("house3.jpg");
		byte[] byteOfPicture4 = getBytesOf("flat1.jpg");
		byte[] byteOfPicture5 = getBytesOf("flat2.jpg");
		repository.save(new Property(PropertyType.HOUSE, SaleOrRent.RENT, 1000d, "The most beautiful house is for rent now!", "Debrecen",
				null, byteOfPicture1));
		repository.save(new Property(PropertyType.HOUSE, SaleOrRent.SALE, 2000d, "Our house is for sale. It is 200 m2.", "Budapest", "Kossuth",
				byteOfPicture2));
		repository.save(new Property(PropertyType.HOUSE, SaleOrRent.SALE, 3000d, "House for sale not far from city center.", "Budapest", "Bartok",
				byteOfPicture3));
		repository.save(new Property(PropertyType.FLAT, SaleOrRent.RENT, 60d, "Flat for rent for 2 years. Brand new furnitures.", "Kolozsvár", "Bartok",
				byteOfPicture4));
		repository.save(new Property(PropertyType.FLAT, SaleOrRent.SALE, 2000d, "Flat for sale in Bartok street.", "Budapest", "Bartok",
				byteOfPicture5));
		repository.save(new Property(PropertyType.HOUSE, SaleOrRent.RENT, 150d, "House for rent immediately.", "Szolnok", "Wass",
				byteOfPicture3));
		repository.save(new Property(PropertyType.FLAT, SaleOrRent.SALE, 1000d, "Big flat for sale in the city center.", "Budapest", "Bartok",
				byteOfPicture3));
		repository.save(new Property(PropertyType.FLAT, SaleOrRent.SALE, 3000d, "Small flat for sale. Reduced price!", "Budapest", "Albert",
				byteOfPicture5));
		repository.save(new Property(PropertyType.FLAT, SaleOrRent.RENT, 100d, "Flat for rent for a single person! Pets are not allowed!", "Budapest", "Bartok",
				byteOfPicture4));
		repository.save(new Property(PropertyType.HOUSE, SaleOrRent.RENT, 9000d, "Beautiful house for rent for 1 year time.", "Debrecen", "Donath",
				byteOfPicture3));
	}

	private byte[] getBytesOf(String pictureName) throws FileNotFoundException, IOException {
		FileInputStream picStream = new FileInputStream("src/test/resources/" + pictureName);
		byte[] byteOfPicture = picStream.readAllBytes();
		picStream.close();
		return byteOfPicture;
	}
}