package com.aadya.gist.local.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LocalNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public LocalNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LocalNewsViewModel.class))
            return (T) new LocalNewsViewModel(new LocalNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
