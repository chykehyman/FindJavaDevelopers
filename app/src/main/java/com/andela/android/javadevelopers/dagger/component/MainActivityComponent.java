package com.andela.android.javadevelopers.dagger.component;

import android.content.Context;

import com.andela.android.javadevelopers.dagger.module.ApiModule;
import com.andela.android.javadevelopers.dagger.module.GitHubAdapterModule;
import com.andela.android.javadevelopers.dagger.module.MainActivityContextModule;
import com.andela.android.javadevelopers.dagger.qualifier.ActivityContext;
import com.andela.android.javadevelopers.dagger.scope.ActivityScope;
import com.andela.android.javadevelopers.home.view.MainActivity;

import dagger.Component;


/**
 * The interface Main activity component.
 */
@ActivityScope
@Component(modules = {MainActivityContextModule.class, ApiModule.class, GitHubAdapterModule.class},
        dependencies = AppComponent.class)
public interface MainActivityComponent {

    /**
     * Gets main activity context.
     *
     * @return the context
     */
    @ActivityContext
    Context getContext();

    /**
     * Inject.
     *
     * @param target the target
     */
    void inject(MainActivity target);
}
