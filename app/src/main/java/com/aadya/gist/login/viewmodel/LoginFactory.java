package com.aadya.gist.login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.aadya.gist.registration.viewmodel.RegistrationRepository;
import com.aadya.gist.registration.viewmodel.RegistrationViewModel;

public class LoginFactory implements ViewModelProvider.Factory {

    private Application application;

    public LoginFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class))
            return (T) new LoginViewModel(new LoginRepository(application));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
