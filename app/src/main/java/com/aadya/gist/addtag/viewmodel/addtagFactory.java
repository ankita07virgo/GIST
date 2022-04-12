package com.aadya.gist.addtag.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class addtagFactory implements ViewModelProvider.Factory {

    private Application application;

    public addtagFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(addtagViewModel.class))
            return (T) new addtagViewModel(new addtagRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
