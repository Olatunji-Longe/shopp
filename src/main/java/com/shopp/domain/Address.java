package com.shopp.domain;

import com.shopp.domain.dto.AddressDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="addresses", schema = "public", catalog = "shoppdb")
public class Address extends RootEntity {

	@Column(name = "country", nullable = false, length = 32)
	private String country;

	@Column(name = "state", nullable = false, length = 32)
	private String state;

	@Column(name = "city", nullable = false, length = 32)
	private String city;

	@Column(name = "street", nullable = false, length = 128)
	private String street;

	@Column(name="building_no", nullable = false, length = 8)
	private String buildingNo;

	@Column(name="zip_code", nullable = false, length = 8)
	private String zipCode;

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBuildingNo() {
		return this.buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipcode) {
		this.zipCode = zipcode;
	}

	@Override
	public AddressDTO toDTO() {
		return new AddressDTO(this);
	}

}