package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.service.impl.SimpleCrewMemberService;
import com.epam.jwd.core_final.service.impl.SimpleSpaceshipService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MissionFactory implements EntityFactory<FlightMission> {
    public final static MissionFactory INSTANCE = new MissionFactory();

    private MissionFactory() {
    }

    @Override
    public FlightMission create(Object... args) {
        Spaceship assignedSpaceship = findSuitableSpaceship((Long) args[3]);
        List<CrewMember> assignedCrew = findSuitableCrewMembers(assignedSpaceship);

        FlightMission flightMission = new FlightMission((String) args[0],
                (LocalDateTime) args[1],
                (LocalDateTime) args[2],
                (Long) args[3]);

        flightMission.setAssignedSpaceShip(assignedSpaceship);
        flightMission.setAssignedCrew(assignedCrew);

        return flightMission;
    }

    private Spaceship findSuitableSpaceship(Long flightDistance) {
        Spaceship spaceship = null;
        Collection<Spaceship> availableSpaceships = SimpleSpaceshipService.INSTANCE
                .findAllSpaceshipsByCriteria(SpaceshipCriteria.builder().readyForNextMission(true).build());
        for (Spaceship availableSpaceship : availableSpaceships) {
            if (availableSpaceship.getFlightDistance() >= flightDistance) {
                spaceship = availableSpaceship;
                break;
            }
        }
        SimpleSpaceshipService.INSTANCE.assignSpaceshipOnMission(spaceship);
        return spaceship;
    }

    private List<CrewMember> findSuitableCrewMembers(Spaceship spaceship) {
        List<CrewMember> crewMembers = new ArrayList<>();
        for (Map.Entry<Role, Short> entry : spaceship.getCrew().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                Optional<CrewMember> optionalCrewMember = SimpleCrewMemberService.INSTANCE.findCrewMemberByCriteria(
                        CrewMemberCriteria.builder()
                                .withRole(entry.getKey())
                                .isReadyForNextMission(true)
                                .build());
                SimpleCrewMemberService.INSTANCE.assignCrewMemberOnMission(optionalCrewMember.get());
                crewMembers.add(optionalCrewMember.get());
            }
        }
        return crewMembers;
    }
}
