package com.aadya.gist.covid19.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class Covid19Factory implements ViewModelProvider.Factory {

    private Application application;

    public Covid19Factory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(Covid19ViewModel.class))
            return (T) new Covid19ViewModel(new Covid19Repository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
