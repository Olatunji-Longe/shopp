package com.shopp.domain.dto;

import com.shopp.domain.Address;

public class AddressDTO extends EntityDTO<Address> {

    private Long id;
    private String country;
    private String state;
    private String city;
    private String street;
    private String buildingNo;
    private String zipCode;

    public AddressDTO(Address address) {
        super(address);
    }

    @Override
    protected void load(Address address) {
        this.id = address.getId();
        this.country = address.getCountry();
        this.state = address.getState();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.buildingNo = address.getBuildingNo();
        this.zipCode = address.getZipCode();
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
