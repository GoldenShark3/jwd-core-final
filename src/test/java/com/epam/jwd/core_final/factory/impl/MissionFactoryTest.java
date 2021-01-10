package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import org.junit.BeforeClass;
import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class MissionFactoryTest {
    private static MissionFactory missionFactory;

    @BeforeClass
    public static void setUp() {
        missionFactory = MissionFactory.INSTANCE;
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_throwException_whenListOfSpaceshipsEmpty() {
        String name = "mission";
        Long flightDistance = 150000L;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();

        FlightMission mission = missionFactory.create(name, startDate, endDate, flightDistance);

        assertEquals(name, mission.getName());
        assertEquals(flightDistance, mission.getDistance());
        assertEquals(startDate, mission.getStartDate());
        assertEquals(endDate, mission.getEndDate());
    }
}