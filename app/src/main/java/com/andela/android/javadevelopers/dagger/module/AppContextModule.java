package com.andela.android.javadevelopers.dagger.module;

import android.content.Context;

import com.andela.android.javadevelopers.dagger.application.App;
import com.andela.android.javadevelopers.dagger.qualifier.ApplicationContext;
import com.andela.android.javadevelopers.dagger.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;


/**
 * The type App module.
 */
@Module
public class AppContextModule {
    private final App appContext;

    /**
     * Instantiates a new App module.
     *
     * @param appContext the app context
     */
    public AppContextModule(App appContext) {
        this.appContext = appContext;
    }

    /**
     * Provide application context.
     *
     * @return the context
     */
    @ApplicationScope
    @ApplicationContext
    @Provides
    Context provideContext() {
        return appContext;
    }
}
