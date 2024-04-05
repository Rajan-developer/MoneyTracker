package com.nchl.moneytracker.presentation.utils.log;

public class AppState {

    private static AppState INSTANCE = null;

    private BuildType buildType = BuildType.RELEASE;

    private AppState() {
    }

    public static AppState getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AppState();
        return INSTANCE;
    }

    public BuildType getBuildType() {
        return buildType;
    }

    public void setBuildType(BuildType buildType) {
        this.buildType = buildType;
    }

    public boolean isDebug() {
        return this.buildType == BuildType.DEBUG;
    }

    public enum BuildType {
        DEBUG,
        RELEASE
    }
}
