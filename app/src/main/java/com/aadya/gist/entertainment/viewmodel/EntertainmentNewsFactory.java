package com.aadya.gist.entertainment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EntertainmentNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public EntertainmentNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EntertainmentNewsViewModel.class))
            return (T) new EntertainmentNewsViewModel(new EntertainmentNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
