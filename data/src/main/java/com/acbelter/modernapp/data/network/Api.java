package com.acbelter.modernapp.data.network;

import com.acbelter.modernapp.data.netmodel.NetworkUser;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    String BASE_URL = "https://www.dropbox.com";
    // Test users data: https://www.dropbox.com/s/eu7sl0me8n6jcmi/users.json?dl=1
    @GET("/s/eu7sl0me8n6jcmi/{filename}?dl=1")
    Observable<List<NetworkUser>> getUsers(@Path("filename") String filename);
}
