package com.aadya.gist.technology.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TechnologyNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public TechnologyNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TechnologyNewsViewModel.class))
            return (T) new TechnologyNewsViewModel(new TechnologyNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
