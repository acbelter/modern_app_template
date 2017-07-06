package com.acbelter.modernapp.data.network;

import com.acbelter.modernapp.data.netmodel.NetworkUser;

import java.util.List;

import io.reactivex.Observable;

public interface NetworkService {
    Observable<List<NetworkUser>> getUsers();
}
