package com.shopp.domain.dto;

import com.shopp.domain.Address;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
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

}
