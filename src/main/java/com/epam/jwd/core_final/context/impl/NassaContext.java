package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.strategy.Reader;
import com.epam.jwd.core_final.strategy.impl.CrewFileReader;
import com.epam.jwd.core_final.strategy.impl.MissionsFileReader;
import com.epam.jwd.core_final.strategy.impl.SpaceshipsFileReader;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class NassaContext implements ApplicationContext {
    private static NassaContext instance;
    private final ApplicationProperties APPLICATION_PROPERTIES = (ApplicationProperties) PropertyReaderUtil
            .getProperties().get("applicationProperty");
    private final Reader READER = Reader.INSTANCE;

    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final Collection<FlightMission> flightMissions = new ArrayList<>();

    private NassaContext() {
    }

    public static NassaContext getInstance() {
        if (instance == null) {
            instance = new NassaContext();
        }
        return instance;
    }

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> collection;
        switch (tClass.getSimpleName()) {
            case "CrewMember":
                collection = (Collection<T>) crewMembers;
                break;
            case "Spaceship":
                collection = (Collection<T>) spaceships;
                break;
            case "FlightMission":
                collection = (Collection<T>) flightMissions;
                break;
            default:
                throw new UnknownEntityException(tClass.getSimpleName() + " - this class does not exist");
        }
        return collection;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        initListOfCrewMembers();
        initListOfSpaceships();
        initListOfMissions();

    }

    private void initListOfCrewMembers() throws InvalidStateException {
        try {
            READER.setReadFileStrategy(CrewFileReader.getInstance());
            READER.readFile(APPLICATION_PROPERTIES.getInputRootDir(), APPLICATION_PROPERTIES.getCrewFileName());
        } catch (FileNotFoundException | DuplicateEntityException e) {
            throw new InvalidStateException(e.getMessage());
        }
    }

    private void initListOfSpaceships() throws InvalidStateException {
        try {
            READER.setReadFileStrategy(SpaceshipsFileReader.getInstance());
            READER.readFile(APPLICATION_PROPERTIES.getInputRootDir(), APPLICATION_PROPERTIES.getSpaceshipsFileName());
        } catch (FileNotFoundException | DuplicateEntityException e) {
            throw new InvalidStateException(e.getMessage());
        }
    }

    private void initListOfMissions() throws InvalidStateException {
        try {
            READER.setReadFileStrategy(MissionsFileReader.getInstance());
            READER.readFile(APPLICATION_PROPERTIES.getInputRootDir(), APPLICATION_PROPERTIES.getMissionsFileName());
        } catch (FileNotFoundException | DuplicateEntityException e) {
            throw new InvalidStateException(e.getMessage());
        }
    }

    public DateTimeFormatter receiveDateFormat() {
        return DateTimeFormatter.ofPattern(APPLICATION_PROPERTIES.getDateTimeFormat());
    }


}

