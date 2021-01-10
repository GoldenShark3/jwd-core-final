package com.epam.jwd.core_final.context.impl.menu.update;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.context.impl.menu.NassaApplicationMenu;
import com.epam.jwd.core_final.util.ConsoleColors;

public class UpdateEntitiesMenu {

    public static void menu() {
        System.out.println("----------------------------");
        System.out.println("1) Update crew member");
        System.out.println("2) Update spaceship");
        System.out.println("3) Update flight mission");
        System.out.println("4) Back to menu");
        handleUserInput(ApplicationMenu.receiveUserChoose());
    }

    private static void handleUserInput(int choose) {
        switch (choose) {
            case 1:
                UpdateCrewMemberMenu.menu();
                break;
            case 2:
                UpdateSpaceshipMenu.menu();
                break;
            case 3:
                UpdateFlightMissionMenu.menu();
                break;
            case 4:
                NassaApplicationMenu.getInstance().printAvailableOptions();
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "It seems you made a mistake. " +
                        "Check given menu and enter value: " + ConsoleColors.RESET);
                break;
        }
        menu();
    }
}
