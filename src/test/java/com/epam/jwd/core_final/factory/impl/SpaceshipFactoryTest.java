package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class SpaceshipFactoryTest {
    private static SpaceshipFactory spaceshipFactory;

    @BeforeClass
    public static void setUp() {
        spaceshipFactory = SpaceshipFactory.INSTANCE;
    }

    @Test
    public void create_receiveNewSpaceship_always() {
        String name = "spaceship";
        Long flightDistance = 150L;
        Map<Role, Short> crew = new HashMap<>();
        crew.put(Role.MISSION_SPECIALIST, (short) 4);
        crew.put(Role.FLIGHT_ENGINEER, (short) 10);
        crew.put(Role.PILOT, (short) 5);
        crew.put(Role.COMMANDER, (short) 1);

        Spaceship spaceship = spaceshipFactory.create(name, flightDistance, crew);

        assertEquals(name, spaceship.getName());
        assertEquals(flightDistance, spaceship.getFlightDistance());
        assertEquals(crew, spaceship.getCrew());
    }
}