package com.epam.jwd.core_final.context.impl.menu.view;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.service.impl.SimpleMissionService;
import com.epam.jwd.core_final.util.ConsoleColors;

import java.util.List;

class ShowMissionsMenu {
    private static final SimpleMissionService MISSION_SERVICE = SimpleMissionService.INSTANCE;

    public static void menu() {
        System.out.println("----------------------------");
        System.out.println("1) Show all missions");
        System.out.println("2) Show missions by criteria");
        System.out.println("3) Back");
        handleUserInput(ApplicationMenu.receiveUserChoose());
    }

    private static void handleUserInput(int choose) {
        switch (choose) {
            case 1:
                showAllMissions();
                break;
            case 2:
                showMissionsByCriteria(FlightMissionCriteria.builder());
                break;
            case 3:
                ViewEntitiesMenu.menu();
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "It seems you made a mistake. " +
                        "Check given menu and enter value: " + ConsoleColors.RESET);
                break;
        }
        menu();
    }

    private static void showAllMissions() {
        List<FlightMission> missionList = MISSION_SERVICE.findAllMissions();
        displayMissionsList(missionList);
    }

    private static void displayMissionsList(List<FlightMission> missionList) {
        for (FlightMission mission : missionList) {
            String nameColor;
            if (mission.getMissionResult() == MissionResult.FAILED) {
                nameColor = ConsoleColors.RED_BOLD;
            } else {
                nameColor = ConsoleColors.GREEN_BOLD;
            }

            System.out.print(nameColor + "id: " + mission.getId() + ", " + mission.getName()
                    + " - " + ConsoleColors.PURPLE_BOLD + mission.getDistance() + ", "
                    + mission.getAssignedSpaceShip() + ";\n" + ConsoleColors.RESET);
        }
    }

    private static void showMissionsByCriteria(FlightMissionCriteria.FlightMissionBuilder builder) {
        System.out.println("----------------------------");
        System.out.println("Choose which criteria you want to search by: \n" +
                "0) Back \n" +
                "1) Name \n" +
                "2) Distance \n" +
                "3) Show");

        switch (ApplicationMenu.receiveUserChoose()) {
            case 0:
                ViewEntitiesMenu.menu();
                break;
            case 1:
                System.out.println("Enter name of mission: ");
                builder.withName(ApplicationMenu.SCANNER.nextLine());
                break;
            case 2:
                System.out.println("Enter the distance: ");
                builder.withDistance(ApplicationMenu.SCANNER.nextInt());
                break;
            case 3:
                List<FlightMission> missionList = MISSION_SERVICE.findAllMissionsByCriteria(builder.build());
                if (missionList.isEmpty()) {
                    System.out.println(ConsoleColors.RED_BOLD + "Not a single match was found" + ConsoleColors.RESET);
                } else {
                    displayMissionsList(missionList);
                }
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "It seems you made a mistake. " +
                        "Check given menu and enter value: " + ConsoleColors.RESET);
                break;
        }
        showMissionsByCriteria(builder);
    }
}
