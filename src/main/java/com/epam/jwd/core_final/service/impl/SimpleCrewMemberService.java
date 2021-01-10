package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.AssignedOnMissionException;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.service.api.CrewService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleCrewMemberService implements CrewService {
    public static final SimpleCrewMemberService INSTANCE = new SimpleCrewMemberService();
    private final static NassaContext NASSA_CONTEXT = NassaContext.getInstance();

    private SimpleCrewMemberService() {
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return (List<CrewMember>) NASSA_CONTEXT.retrieveBaseEntityList(CrewMember.class);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        CrewMemberCriteria memberCriteria = (CrewMemberCriteria) criteria;
        return findAllCrewMembers().stream()
                .filter(e -> memberCriteria.getName() == null
                        || memberCriteria.getName().equals(e.getName()))
                .filter(e -> memberCriteria.getId() == null
                        || memberCriteria.getId().equals(e.getId()))
                .filter(e -> memberCriteria.getRank() == null
                        || memberCriteria.getRank() == e.getRank())
                .filter(e -> memberCriteria.getRole() == null
                        || memberCriteria.getRole() == e.getRole())
                .filter(e -> memberCriteria.getReadyForNextMission() == null
                        || memberCriteria.getReadyForNextMission() == e.isReadyForNextMissions())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        return findAllCrewMembersByCriteria(criteria).stream().findFirst();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        Optional<CrewMember> optional = findAllCrewMembers().stream()
                .filter(e -> crewMember.getName().equals(e.getName()))
                .findFirst();

        if (optional.isPresent()) {
            optional.get().setRole(crewMember.getRole());
            optional.get().setRank(crewMember.getRank());
            optional.get().setReadyForNextMissions(crewMember.isReadyForNextMissions());
        } else {
            throw new IllegalArgumentException(crewMember.getName() + " - this crew member not found");
        }
        return optional.get();
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws AssignedOnMissionException {
        if (crewMember == null) {
            throw new IllegalArgumentException("Crew member can not be a null");
        }
        if (crewMember.isReadyForNextMissions()) {
            crewMember.setReadyForNextMissions(false);
        } else {
            throw new AssignedOnMissionException("Crew member already on mission - " + crewMember);
        }
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws DuplicateEntityException {
        Optional<CrewMember> optionalCrewMember = findAllCrewMembers().stream()
                .filter(e -> crewMember.getName().equals(e.getName()))
                .findFirst();

        if (optionalCrewMember.isPresent()) {
            throw new DuplicateEntityException("This entity" + crewMember.getName() + "is already consist");
        } else {
            findAllCrewMembers().add(crewMember);
            return crewMember;
        }
    }
}
