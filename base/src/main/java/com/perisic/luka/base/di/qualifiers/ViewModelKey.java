package com.perisic.luka.base.di.qualifiers;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * Created by Luka Perisic on 18.6.2019..
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@MapKey
public @interface ViewModelKey {

    Class<? extends ViewModel> value();

}