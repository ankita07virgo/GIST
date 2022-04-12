package com.aadya.gist.india.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class IndiaNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public IndiaNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(IndiaNewsViewModel.class))
            return (T) new IndiaNewsViewModel(new IndiaNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
