package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;
import java.util.Map;

public enum SpaceshipFactory implements EntityFactory<Spaceship> {
    INSTANCE;

    @Override
    public Spaceship create(Object... args) {
        return new Spaceship((String) args[0], (Long) args[1], (Map<Role, Short>) args[2]);
    }
}

