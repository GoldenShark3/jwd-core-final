package com.epam.jwd.core_final.strategy.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.impl.SimpleCrewMemberService;
import com.epam.jwd.core_final.strategy.api.ReadFileStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CrewFileReader implements ReadFileStrategy {
    private static CrewFileReader instance;
    private final CrewMemberFactory CREW_MEMBER_FACTORY = CrewMemberFactory.getInstance();
    private final SimpleCrewMemberService CREW_MEMBER_SERVICE = SimpleCrewMemberService.INSTANCE;
    private final Logger LOGGER = LoggerFactory.getLogger(CrewFileReader.class);

    private CrewFileReader() {
    }

    public static CrewFileReader getInstance() {
        if (instance == null) {
            instance = new CrewFileReader();
        }
        return instance;
    }

    @Override
    public void readFile(String rootDir, String fileName) throws FileNotFoundException {
        String path = "src" + File.separator + "main" + File.separator
                + "resources" + File.separator + rootDir + File.separator + fileName;
        Scanner scanner = new Scanner(new File(path));
        parseFile(scanner);

    }

    private void parseFile(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (nextLine.charAt(0) != '#') {
                String[] crewMembersData = nextLine.split("[;]");
                for (String crewData : crewMembersData) {
                    createCrewMember(crewData.split("[,]"));
                }
            }
        }
        scanner.close();
    }

    private void createCrewMember(String[] args) {
        try {
            CrewMember crewMember = CREW_MEMBER_FACTORY.create(
                    Role.resolveRoleById(Integer.parseInt(args[0])),
                    args[1],
                    Rank.resolveRankById(Integer.parseInt(args[2])));

            CREW_MEMBER_SERVICE.createCrewMember(crewMember);
        } catch (DuplicateEntityException | UnknownEntityException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
