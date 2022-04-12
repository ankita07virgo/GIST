package com.aadya.gist.currentweather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.aadya.gist.addtag.viewmodel.currentweatherRepository;
import com.aadya.gist.addtag.viewmodel.currentweatherViewModel;

public class currentweatherFactory implements ViewModelProvider.Factory {

    private Application application;

    public currentweatherFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(currentweatherViewModel.class))
            return (T) new currentweatherViewModel(new currentweatherRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
