package com.sasekulic.paylevencodingchallenge;

import android.app.Application;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;

import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class MainApplication extends Application {

    public static final String LOGGING_TAG = "PAYLEVEN_TEST_APP";
    private static final IAndroidLogger.LoggingLevel LOGGING_LEVEL = IAndroidLogger.LoggingLevel.VERBOSE;
    private SettingsManager mSettingsManager;
    public static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setupSettingsManager();
    }

    private void setupSettingsManager() {
        IAndroidLogger logger = new DetailedAndroidLogger(LOGGING_TAG, LOGGING_LEVEL);
        mSettingsManager = new SettingsManager(this, logger);
    }

    public SettingsManager getSettingsManager() {

        if (mSettingsManager == null) {
            setupSettingsManager();
        }

        return mSettingsManager;
    }


}
