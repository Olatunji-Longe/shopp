package com.shopp.domain.dto;

import com.shopp.domain.RootEntity;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
public abstract class EntityDTO<T extends RootEntity> {

    protected EntityDTO(T entity){
        this.load(entity);
    }

    protected abstract void load(T entity);

    @SuppressWarnings("unchecked")
    public static <T extends RootEntity, K extends EntityDTO<T>> List<K> list(List<T> entities){
        return entities != null ? entities.stream().map(entity -> (K)entity.toDTO()).collect(Collectors.toList()) : Collections.emptyList();
    }
}
