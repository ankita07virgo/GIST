package com.aadya.gist.registration.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.aadya.gist.health.viewmodel.HealthNewsRepository;
import com.aadya.gist.health.viewmodel.HealthNewsViewModel;

public class RegistrationFactory implements ViewModelProvider.Factory {

    private Application application;

    public RegistrationFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class))
            return (T) new RegistrationViewModel(new RegistrationRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
