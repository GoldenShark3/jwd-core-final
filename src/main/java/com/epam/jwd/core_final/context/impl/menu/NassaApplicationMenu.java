package com.epam.jwd.core_final.context.impl.menu;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.context.impl.NassaContext;

public class NassaApplicationMenu implements ApplicationMenu {
    private static NassaApplicationMenu instance;

    public static NassaApplicationMenu getInstance() {
        if (instance == null) {
            instance = new NassaApplicationMenu();
        }
        return instance;
    }

    private NassaApplicationMenu() {
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return NassaContext.getInstance();
    }



}
