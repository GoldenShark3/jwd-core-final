package com.epam.jwd.core_final.context.impl.menu.update;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.service.impl.SimpleMissionService;

import java.util.Optional;

class UpdateFlightMissionMenu {
    private final static SimpleMissionService MISSION_SERVICE = SimpleMissionService.INSTANCE;

    public static void menu() {
        System.out.println("----------------------------");
        System.out.println("Enter ID of mission you want to update: ");
        int id = ApplicationMenu.receiveUserChoose();

        Optional<FlightMission> optionalFlightMission = MISSION_SERVICE.findMissionByCriteria(
                FlightMissionCriteria.builder().withId(id).build());

        if (!optionalFlightMission.isPresent()) {
            System.out.println("Mission with id = " + id + " - not found, please check your input and try again");
            menu();
        } else {
            updateMissionMenu(optionalFlightMission.get());
        }
        UpdateEntitiesMenu.menu();
    }

    private static void updateMissionMenu(FlightMission mission) {
        System.out.println("Choose property to update: \n" +
                "1) Distance");
        int choose = ApplicationMenu.receiveUserChoose();
        if (choose == 1) {
            updateMissionDistance(mission);
        } else {
            System.out.println("It seems you made a mistake. Check given menu and enter value:");
            updateMissionMenu(mission);
        }
    }

    private static void updateMissionDistance(FlightMission mission) {
        System.out.println("Enter new distance: ");
        int flightDistance = ApplicationMenu.receiveUserChoose();
        mission.setDistance((long) flightDistance);
        MISSION_SERVICE.updateSpaceshipDetails(mission);
    }

}
