package com.aadya.gist.world.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class WorldNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public WorldNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WorldNewsViewModel.class))
            return (T) new WorldNewsViewModel(new WorldNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
