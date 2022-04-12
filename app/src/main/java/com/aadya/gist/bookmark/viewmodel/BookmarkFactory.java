package com.aadya.gist.bookmark.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BookmarkFactory implements ViewModelProvider.Factory {

    private Application application;

    public BookmarkFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BookmarkViewModel.class))
            return (T) new BookmarkViewModel(new BookMarkRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
