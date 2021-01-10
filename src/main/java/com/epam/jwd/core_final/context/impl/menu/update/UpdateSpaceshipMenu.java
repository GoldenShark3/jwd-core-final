package com.epam.jwd.core_final.context.impl.menu.update;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.impl.SimpleSpaceshipService;
import java.util.Optional;

class UpdateSpaceshipMenu {
    private final static SimpleSpaceshipService SPACESHIP_SERVICE = SimpleSpaceshipService.INSTANCE;

    public static void menu() {
        System.out.println("----------------------------");
        System.out.println("Enter ID of spaceship you want to update: ");
        int id = ApplicationMenu.receiveUserChoose();

        Optional<Spaceship> optionalSpaceship = SPACESHIP_SERVICE.findSpaceshipByCriteria(
                SpaceshipCriteria.builder().withId(id).build());

        if (!optionalSpaceship.isPresent()) {
            System.out.println("Spaceship with id = " + id + " - not found, please check your input and try again");
            menu();
        } else {
            updateSpaceshipMenu(optionalSpaceship.get());
        }
        UpdateEntitiesMenu.menu();
    }

    private static void updateSpaceshipMenu(Spaceship spaceship) {
        System.out.println("Choose property to update: \n" +
                "1) Flight distance");
        int choose = ApplicationMenu.receiveUserChoose();
        if (choose == 1) {
            updateSpaceshipFlightDistance(spaceship);
        } else {
            System.out.println("It seems you made a mistake. Check given menu and enter value:");
            updateSpaceshipMenu(spaceship);
        }
    }

    private static void updateSpaceshipFlightDistance(Spaceship spaceship) {
        System.out.println("Enter new flight distance");

        int flightDistance = ApplicationMenu.receiveUserChoose();
        spaceship.setFlightDistance((long) flightDistance);
        SPACESHIP_SERVICE.updateSpaceshipDetails(spaceship);
    }


}
