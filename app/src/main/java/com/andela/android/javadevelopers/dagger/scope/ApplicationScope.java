package com.andela.android.javadevelopers.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


/**
 * The interface Application scope.
 */
@Scope
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationScope {
    // left blank intentionally
}
