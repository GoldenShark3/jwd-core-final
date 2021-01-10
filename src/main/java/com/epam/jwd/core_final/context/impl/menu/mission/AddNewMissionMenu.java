package com.epam.jwd.core_final.context.impl.menu.mission;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.exception.AssignedOnMissionException;
import com.epam.jwd.core_final.factory.impl.MissionFactory;
import com.epam.jwd.core_final.service.impl.SimpleMissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Random;

public class AddNewMissionMenu {
    private static final SimpleMissionService MISSION_SERVICE = SimpleMissionService.INSTANCE;
    private static final NassaContext NASSA_CONTEXT = NassaContext.getInstance();
    private static final MissionFactory MISSION_FACTORY = MissionFactory.INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddNewMissionMenu.class);

    public static void createNewMission() {
        String missionName;
        Long missionDistance;
        LocalDateTime startDate;
        LocalDateTime endDate;

        receiveRandomResultOfMissions();

        System.out.println("Enter the name of the mission: ");
        missionName = ApplicationMenu.SCANNER.nextLine();

        missionDistance = selectionDistance();
        startDate = LocalDateTime.now().plusDays(new Random().nextInt(20));
        endDate = startDate.plusDays(missionDistance / 5000);
        try {
            MISSION_SERVICE.createMission(MISSION_FACTORY.create(missionName, startDate, endDate, missionDistance));
        }catch (IllegalArgumentException | AssignedOnMissionException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static Long selectionDistance() {
        System.out.println("Enter the distance of the mission: ");
        return (long) ApplicationMenu.receiveUserChoose();
    }


    private static void receiveRandomResultOfMissions() {
        Collection<FlightMission> listOfMissions = NASSA_CONTEXT.retrieveBaseEntityList(FlightMission.class);
        for (FlightMission flightMission : listOfMissions) {
            if (flightMission.getMissionResult() != MissionResult.COMPLETED) {
                receiveStatusOfMission(flightMission);
            }
        }
    }

    private static void receiveStatusOfMission(FlightMission flightMission) {
        Random random = new Random();
        if (random.nextInt(100) <= 20) {
            flightMission.setMissionResult(MissionResult.FAILED);
        } else {
            completedMission(flightMission);
        }
    }

    private static void completedMission(FlightMission flightMission) {
        flightMission.setMissionResult(MissionResult.COMPLETED);
        flightMission.getAssignedSpaceShip().setReadyForNextMissions(true);
        flightMission.getAssignedCrew().forEach(crewMember -> crewMember.setReadyForNextMissions(true));
    }
}
