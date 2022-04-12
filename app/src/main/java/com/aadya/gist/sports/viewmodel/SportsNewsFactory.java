package com.aadya.gist.sports.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SportsNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public SportsNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SportsNewsViewModel.class))
            return (T) new SportsNewsViewModel(new SportNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
