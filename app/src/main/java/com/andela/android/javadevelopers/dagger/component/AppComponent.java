package com.andela.android.javadevelopers.dagger.component;

import android.content.Context;

import com.andela.android.javadevelopers.dagger.application.App;
import com.andela.android.javadevelopers.dagger.module.AppContextModule;
import com.andela.android.javadevelopers.dagger.qualifier.ApplicationContext;
import com.andela.android.javadevelopers.dagger.scope.ApplicationScope;

import dagger.Component;


/**
 * The interface Application component.
 */
@ApplicationScope
@Component(modules = AppContextModule.class)
public interface AppComponent {

    /**
     * Gets application level context.
     *
     * @return the context
     */
    @ApplicationContext
    Context getContext();

    /**
     * Inject.
     *
     * @param app the app
     */
    void inject(App app);
}
