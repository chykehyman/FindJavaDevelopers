package com.andela.android.javadevelopers.dagger.application;

import android.app.Activity;
import android.app.Application;

import com.andela.android.javadevelopers.dagger.component.AppComponent;
import com.andela.android.javadevelopers.dagger.component.DaggerAppComponent;
import com.andela.android.javadevelopers.dagger.module.AppContextModule;


/**
 * The type App.
 */
public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appContextModule(new AppContextModule(this))
                .build();

        appComponent.inject(this);
    }

    public static App get(Activity activity) {
        return (App) activity.getApplication();
    }

    /**
     * Gets app component.
     *
     * @return the app component
     */
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
