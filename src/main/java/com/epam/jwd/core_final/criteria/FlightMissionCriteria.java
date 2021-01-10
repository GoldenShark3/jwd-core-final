package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;

import java.time.LocalDateTime;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long distance;
    private final Spaceship assignedSpaceship;
    private final CrewMember crewMember;
    private final MissionResult missionResult;

    private FlightMissionCriteria(Builder<FlightMissionBuilder> builder, LocalDateTime startDate, LocalDateTime endDate,
                                  Long distance, Spaceship assignedSpaceship,
                                  CrewMember crewMember, MissionResult missionResult) {
        super(builder);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceship = assignedSpaceship;
        this.crewMember = crewMember;
        this.missionResult = missionResult;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceship() {
        return assignedSpaceship;
    }

    public CrewMember getCrewMember() {
        return crewMember;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public static FlightMissionBuilder builder() {
        return new FlightMissionBuilder();
    }

    public static class FlightMissionBuilder extends Builder<FlightMissionBuilder> {
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Long distance;
        private Spaceship assignedSpaceship;
        private CrewMember crewMember;
        private MissionResult missionResult;

        private FlightMissionBuilder() {
        }

        @Override
        FlightMissionBuilder receiveBuilder() {
            return this;
        }

        @Override
        public FlightMissionCriteria build() {
            return new FlightMissionCriteria(this,
                    this.startDate,
                    this.endDate,
                    this.distance,
                    this.assignedSpaceship,
                    this.crewMember,
                    this.missionResult);
        }

        public FlightMissionBuilder withStartData(LocalDateTime startDate) {
            this.startDate = startDate;
            return receiveBuilder();
        }

        public FlightMissionBuilder withEndData(LocalDateTime endDate) {
            this.endDate = endDate;
            return receiveBuilder();
        }

        public FlightMissionBuilder withDistance(long distance) {
            this.distance = distance;
            return receiveBuilder();
        }

        public FlightMissionBuilder byAssignedSpaceship(Spaceship assignedSpaceship) {
            this.assignedSpaceship = assignedSpaceship;
            return receiveBuilder();
        }

        public FlightMissionBuilder withCrewMember(CrewMember crewMember) {
            this.crewMember = crewMember;
            return receiveBuilder();
        }

        public FlightMissionBuilder byMissionStatus(MissionResult missionStatus) {
            this.missionResult = missionStatus;
            return receiveBuilder();
        }
    }
}
