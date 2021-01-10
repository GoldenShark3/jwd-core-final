package com.epam.jwd.core_final.context.impl.menu.view;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.impl.SimpleSpaceshipService;
import com.epam.jwd.core_final.util.ConsoleColors;
import java.util.List;

class ShowSpaceshipsMenu {
    private static final SimpleSpaceshipService SPACESHIP_SERVICE = SimpleSpaceshipService.INSTANCE;

    public static void menu() {
        System.out.println("----------------------------");
        System.out.println("1) Show all spaceships");
        System.out.println("2) Show spaceships by criteria");
        System.out.println("3) Back");
        handleUserInput(ApplicationMenu.receiveUserChoose());
    }

    private static void handleUserInput(int choose) {
        switch (choose) {
            case 1:
                showAllSpaceships();
                break;
            case 2:
                showSpaceshipsByCriteria(SpaceshipCriteria.builder());
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

    private static void showAllSpaceships() {
        List<Spaceship> spaceshipList = SPACESHIP_SERVICE.findAllSpaceships();
        displaySpaceshipsList(spaceshipList);
    }

    private static void displaySpaceshipsList(List<Spaceship> spaceshipList) {
        for (Spaceship spaceship : spaceshipList) {
            String nameColor;
            if (!spaceship.isReadyForNextMissions()) {
                nameColor = ConsoleColors.RED_BOLD;
            } else {
                nameColor = ConsoleColors.GREEN_BOLD;
            }

            System.out.print(nameColor + "id: " + spaceship.getId() + ", " + spaceship.getName()
                    + " - " + ConsoleColors.PURPLE_BOLD + spaceship.getFlightDistance() + ", "
                    + spaceship.getCrew() + ";\n" + ConsoleColors.RESET);
        }
    }

    private static void showSpaceshipsByCriteria(SpaceshipCriteria.SpaceshipBuilder builder) {
        System.out.println("----------------------------");
        System.out.println("Choose which criteria you want to search by: \n" +
                "0) Back \n" +
                "1) Name \n" +
                "2) Flight distance \n" +
                "3) Ready for next mission \n" +
                "4) Show");

        switch (ApplicationMenu.receiveUserChoose()) {
            case 0:
                ViewEntitiesMenu.menu();
                break;
            case 1:
                System.out.println("Enter name of spaceship: ");
                builder.withName(ApplicationMenu.SCANNER.nextLine());
                break;
            case 2:
                System.out.println("Enter the flight distance: ");
                builder.withFlightDistance(ApplicationMenu.SCANNER.nextInt());
                break;
            case 3:
                System.out.println("Enter the role id (write 'true' or 'false'): ");
                builder.readyForNextMission(ApplicationMenu.SCANNER.nextBoolean());
                break;
            case 4:
                List<Spaceship> spaceshipList = SPACESHIP_SERVICE.findAllSpaceshipsByCriteria(builder.build());
                if (spaceshipList.isEmpty()) {
                    System.out.println("Not a single match was found");
                } else {
                    displaySpaceshipsList(spaceshipList);
                }
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "It seems you made a mistake. " +
                        "Check given menu and enter value:" + ConsoleColors.RESET);
                break;
        }
        showSpaceshipsByCriteria(builder);
    }
}
