package com.sasekulic.paylevencodingchallenge;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.alterego.advancedandroidlogger.implementations.NullAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sasekulic.paylevencodingchallenge.api.IPaylevenApi;
import com.sasekulic.paylevencodingchallenge.interfaces.IDisposable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;


@Accessors(prefix = "m")
public class SettingsManager implements IDisposable {

    @Getter @Setter private IAndroidLogger mLogger = NullAndroidLogger.instance;
    @Getter private Activity mParentActivity;
    @Getter private Application mParentApplication;
    @Getter private Gson mGson = new GsonBuilder().create();
    @Getter private IPaylevenApi mPaylevenApiService;
    //@Getter @Setter private IActionBarTitleHandler mActionBarTitleHandler;
    @Getter private ShoppingBasketManager mShoppingBasketManager;


    public SettingsManager(Application app, IAndroidLogger logger) {
        setLogger(logger);
        mParentApplication  = app;
        mPaylevenApiService = createPaylevenService(app, getGson());
        mShoppingBasketManager = new ShoppingBasketManager(this);
    }

    public void setParentActivity(Activity act) {
        mParentActivity = act;
    }

    private IPaylevenApi createPaylevenService(Context ctx, Gson gson) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(ctx.getResources().getString(R.string.reader_server))
                //.setErrorHandler(new PaylevenApiErrorHandler(mLogger))
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog(MainApplication.LOGGING_TAG))
                .build();

        return restAdapter.create(IPaylevenApi.class);
    }

    @Override
    public void dispose() {
        //do nothing
    }

}
