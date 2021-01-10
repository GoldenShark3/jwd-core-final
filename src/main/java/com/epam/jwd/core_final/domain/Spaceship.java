package com.epam.jwd.core_final.domain;
import java.util.Map;
import java.util.Objects;

/**
 * crew {@link java.util.Map<Role,Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {
    private static Long id = 0L;
    private Map<Role, Short> crew;
    private Long flightDistance;
    private boolean isReadyForNextMissions;

    public Spaceship(String name, Long flightDistance, Map<Role, Short> crew) {
        super(id++, name);
        this.crew = crew;
        this.flightDistance = flightDistance;
        this.isReadyForNextMissions = true;
    }

    @Override
    public String toString() {
        return "Spaceship{" +
                "name=" + getName() +
                ", id=" + getId() +
                ", crew=" + crew +
                ", flightDistance=" + flightDistance +
                ", isReadyForNextMissions=" + isReadyForNextMissions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Spaceship spaceship = (Spaceship) o;
        return  getId().equals(spaceship.getId())
                && Objects.equals(getName(), spaceship.getName())
                && Objects.equals(crew, spaceship.crew)
                && Objects.equals(flightDistance, spaceship.flightDistance)
                && Objects.equals(isReadyForNextMissions, spaceship.isReadyForNextMissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, flightDistance, isReadyForNextMissions);
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setCrew(Map<Role, Short> crew) {
        this.crew = crew;
    }

    public void setFlightDistance(Long flightDistance) {
        this.flightDistance = flightDistance;
    }

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

}
