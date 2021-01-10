package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.AssignedOnMissionException;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.service.api.SpaceshipService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleSpaceshipService implements SpaceshipService {
    private static final NassaContext NASSA_CONTEXT = NassaContext.getInstance();
    public static final SimpleSpaceshipService INSTANCE = new SimpleSpaceshipService();

    private SimpleSpaceshipService() {
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return (List<Spaceship>) NASSA_CONTEXT.retrieveBaseEntityList(Spaceship.class);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        SpaceshipCriteria spaceshipCriteria = (SpaceshipCriteria) criteria;
        return findAllSpaceships().stream()
                .filter(spaceship -> spaceshipCriteria.getName() == null
                        || spaceshipCriteria.getName().equals(spaceship.getName()))
                .filter(spaceship -> spaceshipCriteria.getId() == null
                        || spaceshipCriteria.getId().equals(spaceship.getId()))
                .filter(spaceship -> spaceshipCriteria.getFlightDistance() == null
                        || spaceshipCriteria.getFlightDistance().equals(spaceship.getFlightDistance()))
                .filter(spaceship -> spaceshipCriteria.getReadyForNextMission() == null
                        || spaceshipCriteria.getReadyForNextMission().equals(spaceship.isReadyForNextMissions()))
                .filter(spaceship -> spaceshipCriteria.getCrew() == null
                        || spaceshipCriteria.getCrew().equals(spaceship.getCrew()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        return findAllSpaceshipsByCriteria(criteria).stream().findFirst();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        Optional<Spaceship> optional = findAllSpaceships().stream()
                .filter(e -> spaceship.getName().equals(e.getName()))
                .findFirst();

        if (optional.isPresent()) {
            optional.get().setFlightDistance(spaceship.getFlightDistance());
            optional.get().setReadyForNextMissions(spaceship.isReadyForNextMissions());
            optional.get().setCrew(spaceship.getCrew());
        } else {
            throw new IllegalArgumentException(spaceship.getName() + " - this spaceship not found");
        }
        return optional.get();
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship) throws AssignedOnMissionException {
        if (spaceship == null) {
            throw new IllegalArgumentException("There is no spaceship capable of flying such a distance");
        }
        if (spaceship.isReadyForNextMissions()) {
            spaceship.setReadyForNextMissions(false);
        }else {
            throw new AssignedOnMissionException("Spaceship is already on mission");
        }

    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws DuplicateEntityException {
        Optional<Spaceship> optionalSpaceship = findAllSpaceships().stream()
                .filter(e -> spaceship.getName().equals(e.getName()))
                .findFirst();

        if (optionalSpaceship.isPresent()) {
            throw new DuplicateEntityException("This entity" + spaceship.getName() + "is already consist");
        } else {
            findAllSpaceships().add(spaceship);
            return spaceship;
        }
    }
}
