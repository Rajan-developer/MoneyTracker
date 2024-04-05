package com.nchl.moneytracker.presentation.utils.log;

import java.util.logging.Level;

public class Logger {

    private java.util.logging.Logger javaLogger;

    public static Logger getLogger(String className) {
        return new Logger(className);
    }

    public <T> Logger(Class<T> clazz) {
        this.javaLogger = java.util.logging.Logger.getLogger("MoneyTracker " + clazz.getName());
    }

    public Logger(String className) {
        this.javaLogger = java.util.logging.Logger.getLogger("MoneyTracker " + className);
    }

    public void log(String message) {
        this.javaLogger.info(message);
    }

    public void debug(String message) {
        if (AppState.getInstance().isDebug())
            this.javaLogger.info(message);
    }

    public void error(String message) {
        this.javaLogger.log(Level.SEVERE, message);
    }
}
