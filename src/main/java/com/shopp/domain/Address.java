package com.shopp.domain;

import com.shopp.domain.dto.AddressDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
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

	@Override
	public AddressDTO toDTO() {
		return new AddressDTO(this);
	}

}