package com.shopp.domain.dto;

import com.shopp.domain.Store;

import java.util.List;

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

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }
}
