package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.service.api.MissionService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleMissionService implements MissionService {
    private static final NassaContext NASSA_CONTEXT = NassaContext.getInstance();
    public static final SimpleMissionService INSTANCE = new SimpleMissionService();

    private SimpleMissionService() {
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return (List<FlightMission>) NASSA_CONTEXT.retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        return findAllMissions().stream()
                .filter(e -> isMissionSuitable(criteria, e))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return findAllMissionsByCriteria(criteria).stream().findFirst();
    }

    @Override
    public FlightMission updateSpaceshipDetails(FlightMission flightMission) {
        Optional<FlightMission> optionalMission = findAllMissions().stream()
                .filter(e -> flightMission.getName().equals(e.getName()))
                .findFirst();

        if (optionalMission.isPresent()) {
            optionalMission.get().setAssignedSpaceShip(flightMission.getAssignedSpaceShip());
            optionalMission.get().setDistance(flightMission.getDistance());
            optionalMission.get().setAssignedCrew(flightMission.getAssignedCrew());
            optionalMission.get().setStartDate(flightMission.getStartDate());
            optionalMission.get().setEndDate(flightMission.getEndDate());
            optionalMission.get().setMissionResult(flightMission.getMissionResult());
        } else {
            throw new IllegalArgumentException(flightMission.getName() + " - this mission not found");
        }
        return optionalMission.get();
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) throws DuplicateEntityException {
        Optional<FlightMission> optionalFlightMission = findAllMissions().stream()
                .filter(e -> flightMission.getName().equals(e.getName())
                        && flightMission.getStartDate().equals(e.getStartDate()))
                .findFirst();

        if (optionalFlightMission.isPresent()) {
            throw new DuplicateEntityException("This mission" + flightMission.getName() + ", "
                    + flightMission.getStartDate().toString() + "is already consist");
        } else {
            findAllMissions().add(flightMission);
            return flightMission;
        }
    }

    private boolean isMissionSuitable(Criteria<? extends FlightMission> criteria, FlightMission mission) {
        FlightMissionCriteria missionCriteria = (FlightMissionCriteria) criteria;
        boolean isMissionSuitable = false;

        if (missionCriteria.getName() != null) {
            isMissionSuitable = missionCriteria.getName().equals(mission.getName());
        }
        if (missionCriteria.getId() != null) {
            isMissionSuitable = missionCriteria.getId().equals(mission.getId());
        }
        if (missionCriteria.getMissionResult() != null) {
            isMissionSuitable = missionCriteria.getMissionResult().equals(mission.getMissionResult());
        }
        if (missionCriteria.getAssignedSpaceship() != null) {
            isMissionSuitable = missionCriteria.getAssignedSpaceship().equals(mission.getAssignedSpaceShip());
        }
        if (missionCriteria.getCrewMember() != null) {
            isMissionSuitable = mission.getAssignedCrew().contains(missionCriteria.getCrewMember());
        }
        if (missionCriteria.getStartDate() != null) {
            isMissionSuitable = mission.getStartDate().equals(missionCriteria.getStartDate());
        }
        if (missionCriteria.getEndDate() != null) {
            isMissionSuitable = mission.getEndDate().equals(missionCriteria.getEndDate());
        }

        return isMissionSuitable;
    }
}
