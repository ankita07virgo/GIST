package com.aadya.gist.bookmarkednewslist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ListBookmarkedNewsFactory implements ViewModelProvider.Factory {

    private Application application;

    public ListBookmarkedNewsFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListBookmarkedNewsViewModel.class))
            return (T) new ListBookmarkedNewsViewModel(new ListBookmarkedNewsRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
