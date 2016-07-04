package com.d500px.fivehundredpx.application.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by KD on 2/5/16.
 *
 * In Dagger, an unscoped component cannot depend on a scoped component. Custom
 * scope to be used by all activities components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
