package com.shopp.domain.dto;

import com.shopp.domain.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
public class StoreDTO extends EntityDTO<Store> {

    private long id;
    private String name;
    private List<AddressDTO> addresses;

    public StoreDTO(Store store) {
        super(store);
    }

    @Override
    protected void load(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.addresses = AddressDTO.list(store.getAddresses());
    }

}
