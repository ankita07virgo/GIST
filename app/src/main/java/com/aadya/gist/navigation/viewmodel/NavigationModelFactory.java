package com.aadya.gist.navigation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.aadya.gist.navigation.repository.NavigationRepository;


public class NavigationModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public NavigationModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NavigationViewModel.class))
            return (T) new NavigationViewModel(new NavigationRepository(application));
        else throw new IllegalArgumentException("IllegalStateArgument");
    }
}
