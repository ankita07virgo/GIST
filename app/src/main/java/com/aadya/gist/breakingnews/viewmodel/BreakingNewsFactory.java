package com.aadya.gist.breakingnews.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BreakingNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public BreakingNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BreakingNewsViewModel.class))
            return (T) new BreakingNewsViewModel(new BreakingNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
