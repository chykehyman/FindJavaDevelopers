package com.andela.android.javadevelopers.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


/**
 * The interface Activity scope.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
    // left blank intentionally
}
