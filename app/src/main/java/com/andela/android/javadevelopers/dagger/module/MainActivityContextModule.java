package com.andela.android.javadevelopers.dagger.module;

import android.content.Context;

import com.andela.android.javadevelopers.dagger.qualifier.ActivityContext;
import com.andela.android.javadevelopers.dagger.scope.ActivityScope;
import com.andela.android.javadevelopers.home.view.MainActivity;

import dagger.Module;
import dagger.Provides;


/**
 * The type Main activity context module.
 */
@Module
public class MainActivityContextModule {
    private final MainActivity mainActivity;

    /**
     * The Context.
     */
    public Context context;

    /**
     * Instantiates a new Main activity context module.
     *
     * @param mainActivity the main activity
     */
    public MainActivityContextModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        context = mainActivity;
    }

    /**
     * Provides main activity.
     *
     * @return the main activity
     */
    @Provides
    @ActivityScope
    MainActivity providesMainActivity() {
        return mainActivity;
    }

    /**
     * Provide main activity context.
     *
     * @return the context
     */
    @Provides
    @ActivityScope
    @ActivityContext
    Context provideContext() {
        return context;
    }
}
