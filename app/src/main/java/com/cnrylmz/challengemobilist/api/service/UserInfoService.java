package com.cnrylmz.challengemobilist.api.service;

import com.cnrylmz.challengemobilist.api.model.UserInfoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Caner on 08.05.2019.
 */

public interface UserInfoService {

    @GET("challenge/index.php")
    Observable<UserInfoResponse> getUserInfos(@Query("start") int paging);

}
