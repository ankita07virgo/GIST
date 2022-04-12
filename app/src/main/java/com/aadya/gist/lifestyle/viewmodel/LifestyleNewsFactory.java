package com.aadya.gist.lifestyle.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.aadya.gist.breakingnews.viewmodel.LifestyleNewsRepository;
import com.aadya.gist.breakingnews.viewmodel.LifestyleNewsViewModel;

public class LifestyleNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public LifestyleNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LifestyleNewsViewModel.class))
            return (T) new LifestyleNewsViewModel(new LifestyleNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
