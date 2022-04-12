package com.aadya.gist.retrofit;

import com.aadya.gist.registration.model.RegistrationResponseModel;
import com.google.gson.JsonObject;

import retrofit2.Response;

public interface APIResponseListener {
    void onSuccess(Response response);

    void onFailure();
}
