package com.aadya.gist.retrofit;

import com.aadya.gist.addnews.model.AddNewsRequestModel;
import com.aadya.gist.addnews.model.AddNewsResponseModel;
import com.aadya.gist.addtag.model.AddTAGResponseModel;
import com.aadya.gist.addtag.model.AddTagRequestModel;
import com.aadya.gist.bookmark.model.BookMarkRequestModel;
import com.aadya.gist.bookmark.model.BookMarkResponseModel;
import com.aadya.gist.bookmarkednewslist.model.ListBookMArkedRequestModel;
import com.aadya.gist.currentweather.model.CurrentWeatherRequestModel;
import com.aadya.gist.currentweather.model.CurrentweatherResponseModel;
import com.aadya.gist.india.model.RequestModel;
import com.aadya.gist.india.model.ResponseModel;
import com.aadya.gist.login.model.LoginRequestModel;
import com.aadya.gist.login.model.LoginResponseModel;
import com.aadya.gist.navigation.model.NavigationResponseModel;
import com.aadya.gist.registration.model.RegistrationRequestModel;
import com.aadya.gist.registration.model.RegistrationResponseModel;
import com.aadya.gist.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;







public class RetrofitService {

    private CommonUtils mCommonUtils = new CommonUtils();
    public void registereUser(RegistrationRequestModel registrationModel, final APIResponseListener apiResponseListener) {
        APICallService service = APIClient.getRetrofitInstance().create(APICallService.class);


        Call<RegistrationResponseModel> call = service.registerUser(registrationModel);
        call.enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(Call<RegistrationResponseModel> call, Response<RegistrationResponseModel> response) {
                mCommonUtils.LogPrint("TAG","1"+response.code());
                apiResponseListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<RegistrationResponseModel> call, Throwable t) {
                apiResponseListener.onFailure();
            }
        });
    }


    public void checkAuthentication(LoginRequestModel loginRequestModel, final APIResponseListener apiResponseListener) {
        APICallService service = APIClient.getRetrofitInstance().create(APICallService.class);


        Call<LoginResponseModel> call = service.loginUser(loginRequestModel);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                apiResponseListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                apiResponseListener.onFailure();
            }
        });
    }

    public void getCategoryList(final APIResponseListener apiResponseListener) {
        APICallService service = APIClient.getRetrofitInstance().create(APICallService.class);
        Call<NavigationResponseModel> call = service.getCategoryList();
        call.enqueue(new Callback<NavigationResponseModel>() {
            @Override
            public void onResponse(Call<NavigationResponseModel> call, Response<NavigationResponseModel> response) {
                apiResponseListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<NavigationResponseModel> call, Throwable t) {
                apiResponseListener.onFailure();
            }
        });
    }


    public void getNewsList(RequestModel requestModel, final APIResponseListener apiResponseListener) {
        APICallService service = APIClient.getRetrofitInstance().create(APICallService.class);

        Call<ResponseModel> call = service.IndiaList(requestModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                apiResponseListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                apiResponseListener.onFailure();
            }
        });
    }


    public void getBookmArkedList(ListBookMArkedRequestModel listBookMArkedRequestModel, final APIResponseListener apiResponseListener) {
        APICallService service = APIClient.getRetrofitInstance().create(APICallService.class);

        Call<ResponseModel> call = service.BookMArkedList(listBookMArkedRequestModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                apiResponseListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                apiResponseListener.onFailure();
            }
        });
    }

    public void BookMarkNews(BookMarkRequestModel bookMArkedRequestModel, final APIResponseListener apiResponseListener) {
        APICallService service = APIClient.getRetrofitInstance().create(APICallService.class);

        Call<BookMarkResponseModel> call = service.BookMArkNews(bookMArkedRequestModel);
        call.enqueue(new Callback<BookMarkResponseModel>() {
            @Override
            public void onResponse(Call<BookMarkResponseModel> call, Response<BookMarkResponseModel> response) {
                apiResponseListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<BookMarkResponseModel> call, Throwable t) {
                apiResponseListener.onFailure();
            }
        });
    }

    public void addTag(AddTagRequestModel addTagRequestModel, final APIResponseListener apiResponseListener) {
        APICallService service = APIClient.getRetrofitInstance().create(APICallService.class);

        Call<AddTAGResponseModel> call = service.ADDTAG(addTagRequestModel);
        call.enqueue(new Callback<AddTAGResponseModel>() {
            @Override
            public void onResponse(Call<AddTAGResponseModel> call, Response<AddTAGResponseModel> response) {
                apiResponseListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<AddTAGResponseModel> call, Throwable t) {
                apiResponseListener.onFailure();
            }
        });
    }

    public void addNews(AddNewsRequestModel addNewsRequestModel, final APIResponseListener apiResponseListener) {
        APICallService service = APIClient.getRetrofitInstance().create(APICallService.class);

        Call<AddNewsResponseModel> call = service.ADDNEWS(addNewsRequestModel);
        call.enqueue(new Callback<AddNewsResponseModel>() {
            @Override
            public void onResponse(Call<AddNewsResponseModel> call, Response<AddNewsResponseModel> response) {
                apiResponseListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<AddNewsResponseModel> call, Throwable t) {
                apiResponseListener.onFailure();
            }
        });
    }

    public void getCurrentWeathers(CurrentWeatherRequestModel currentWeatherRequestModel , final APIResponseListener apiResponseListener) {
        APICallService service = APIClient.getRetrofitInstance().create(APICallService.class);

        Call<CurrentweatherResponseModel> call = service.getCurrentWeather(currentWeatherRequestModel.getLat(),currentWeatherRequestModel.getLon(), currentWeatherRequestModel.getAppid());
        call.enqueue(new Callback<CurrentweatherResponseModel>() {
            @Override
            public void onResponse(Call<CurrentweatherResponseModel> call, Response<CurrentweatherResponseModel> response) {
                apiResponseListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<CurrentweatherResponseModel> call, Throwable t) {
                apiResponseListener.onFailure();
            }
        });
    }
}

