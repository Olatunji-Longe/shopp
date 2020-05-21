package com.shopp.domain.dto;

import com.shopp.domain.RootEntity;

import java.util.List;
import java.util.stream.Collectors;

public abstract class EntityDTO<T extends RootEntity> {

    EntityDTO(T entity){
        this.load(entity);
    }

    protected abstract void load(T entity);

    @SuppressWarnings("unchecked")
    public static <T extends RootEntity, K extends EntityDTO<T>> List<K> list(List<T> entities){
        return entities.stream().map(entity -> (K)entity.toDTO()).collect(Collectors.toList());
    }
}
