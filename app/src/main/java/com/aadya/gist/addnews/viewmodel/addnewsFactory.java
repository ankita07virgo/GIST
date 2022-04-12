package com.aadya.gist.addnews.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class addnewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public addnewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(addnewsViewModel.class))
            return (T) new addnewsViewModel(new addnewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
