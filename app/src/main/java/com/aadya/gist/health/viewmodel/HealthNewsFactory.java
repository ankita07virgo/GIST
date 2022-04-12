package com.aadya.gist.health.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HealthNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public HealthNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HealthNewsViewModel.class))
            return (T) new HealthNewsViewModel(new HealthNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
