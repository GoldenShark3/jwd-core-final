package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private final Role role;
    private final Rank rank;
    private final Boolean isReadyForNextMission;

    private CrewMemberCriteria(Builder<CrewMemberBuilder> crewMemberBuilder, Role role, Rank rank, Boolean isReadyForNextMission) {
        super(crewMemberBuilder);
        this.role = role;
        this.rank = rank;
        this.isReadyForNextMission = isReadyForNextMission;

    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Boolean getReadyForNextMission() {
        return isReadyForNextMission;
    }

    public static CrewMemberBuilder builder() {
        return new CrewMemberBuilder();
    }

    public static class CrewMemberBuilder extends Criteria.Builder<CrewMemberBuilder> {
        private Role role;
        private Rank rank;
        private Boolean isReadyForNextMission;

        private CrewMemberBuilder() {
        }

        @Override
        CrewMemberBuilder receiveBuilder() {
            return this;
        }

        @Override
        public CrewMemberCriteria build() {
            return new CrewMemberCriteria(
                    this,
                    this.role,
                    this.rank,
                    this.isReadyForNextMission);
        }

        public CrewMemberBuilder withRole(Role role) {
            this.role = role;
            return receiveBuilder();
        }

        public CrewMemberBuilder withRank(Rank rank) {
            this.rank = rank;
            return receiveBuilder();
        }

        public CrewMemberBuilder isReadyForNextMission(boolean isReadyForNextMission) {
            this.isReadyForNextMission = isReadyForNextMission;
            return receiveBuilder();
        }
    }
}
