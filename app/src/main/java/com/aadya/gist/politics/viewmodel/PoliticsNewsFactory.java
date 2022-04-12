package com.aadya.gist.politics.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PoliticsNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public PoliticsNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PoliticsViewModel.class))
            return (T) new PoliticsViewModel(new PoliticsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
