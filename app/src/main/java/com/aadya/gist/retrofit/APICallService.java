package com.aadya.gist.retrofit;

import com.aadya.gist.addnews.model.AddNewsRequestModel;
import com.aadya.gist.addnews.model.AddNewsResponseModel;
import com.aadya.gist.addtag.model.AddTAGResponseModel;
import com.aadya.gist.addtag.model.AddTagRequestModel;
import com.aadya.gist.bookmark.model.BookMarkRequestModel;
import com.aadya.gist.bookmark.model.BookMarkResponseModel;
import com.aadya.gist.bookmarkednewslist.model.ListBookMArkedRequestModel;
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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APICallService {


    @POST(CommonUtils.APIURL.CREATE_USER)
    Call<RegistrationResponseModel> registerUser(@Body RegistrationRequestModel registrationrequestModel);

    @POST(CommonUtils.APIURL.LOGIN_USER)
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel loginRequestModel);

    @GET(CommonUtils.APIURL.CATEGORY_LIST)
    Call<NavigationResponseModel> getCategoryList();


    @POST(CommonUtils.APIURL.INDIA_LIST)
    Call<ResponseModel> IndiaList(@Body RequestModel indiaRequestModel);

    @POST(CommonUtils.APIURL.BookMarked_List)
    Call<ResponseModel> BookMArkedList(@Body ListBookMArkedRequestModel listBookMArkedRequestModel);

    @POST(CommonUtils.APIURL.BookMarkNews)
    Call<BookMarkResponseModel> BookMArkNews(@Body BookMarkRequestModel bookMArkedRequestModel);

    @POST(CommonUtils.APIURL.ADDTAG)
    Call<AddTAGResponseModel> ADDTAG(@Body AddTagRequestModel addTagRequestModel);

    @POST(CommonUtils.APIURL.ADDNEWS)
    Call<AddNewsResponseModel> ADDNEWS(@Body AddNewsRequestModel addNewsRequestModel);

    @GET(CommonUtils.APIURL.CURRENTWEATHER)
    Call<CurrentweatherResponseModel> getCurrentWeather(@Query("lat") Double lat, @Query("lon") Double lon, @Query("appid") String appid);

}