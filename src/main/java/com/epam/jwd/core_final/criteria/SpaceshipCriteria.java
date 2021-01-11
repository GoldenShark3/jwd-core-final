package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    private final Map<Role, Short> crew;
    private final Long flightDistance;
    private final Boolean isReadyForNextMission;

    private SpaceshipCriteria(Builder<SpaceshipBuilder> builder, Map<Role, Short> crew,
                                Long flightDistance, boolean isReadyForNextMission) {
        super(builder);
        this.crew = crew;
        this.flightDistance = flightDistance;
        this.isReadyForNextMission = isReadyForNextMission;
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public Boolean getReadyForNextMission() {
        return isReadyForNextMission;
    }

    public static SpaceshipBuilder builder() {
        return new SpaceshipBuilder();
    }

    public static class SpaceshipBuilder extends Criteria.Builder<SpaceshipBuilder> {
        private Map<Role, Short> crew;
        private Long flightDistance;
        private Boolean isReadyForNextMission;

        private SpaceshipBuilder(){
        }

        @Override
        SpaceshipBuilder receiveBuilder() {
            return this;
        }

        @Override
        public SpaceshipCriteria build() {
            return new SpaceshipCriteria(
                    this,
                    this.crew,
                    this.flightDistance,
                    this.isReadyForNextMission);
        }

        public SpaceshipBuilder withFlightDistance(long flightDistance) {
            this.flightDistance = flightDistance;
             return receiveBuilder();
        }

        public SpaceshipBuilder readyForNextMission(boolean isReadyForNextMission) {
            this.isReadyForNextMission = isReadyForNextMission;
            return receiveBuilder();
        }

        public SpaceshipBuilder withCrew(Map<Role, Short> crew) {
            this.crew = crew;
            return receiveBuilder();
        }

    }
}
