package com.aadya.gist.business.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BusinessNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public BusinessNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BusinessNewsViewModel.class))
            return (T) new BusinessNewsViewModel(new BusinessNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
