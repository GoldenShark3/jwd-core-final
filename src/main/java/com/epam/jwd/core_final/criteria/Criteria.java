package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.BaseEntity;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {
    private final String name;
    private final Long id;

    Criteria(Builder<?> builder){
        this.name = builder.name;
        this.id = builder.id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static abstract class Builder<T extends Builder<T>> {
        private String name;
        private Long id;

        public T withName(String name){
            this.name = name;
            return receiveBuilder();
        }

        public T withId(long id) {
            this.id = id;
            return receiveBuilder();
        }

        abstract T receiveBuilder();

        public abstract Criteria<? extends AbstractBaseEntity> build();
    }
}
