package com.acbelter.modernapp.data.network;

import com.acbelter.modernapp.data.netmodel.NetworkUser;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetworkService implements NetworkService {
    private Api mApi;

    public RetrofitNetworkService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(buildOkHttpClient())
                .addConverterFactory(buildConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mApi = retrofit.create(Api.class);
    }

    private static OkHttpClient buildOkHttpClient() {
        return new OkHttpClient();
    }

    private static Converter.Factory buildConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Override
    public Observable<List<NetworkUser>> getUsers() {
        return mApi.getUsers("users.json");
    }
}
