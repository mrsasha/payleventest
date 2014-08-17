package com.sasekulic.paylevencodingchallenge.api;

import com.sasekulic.paylevencodingchallenge.api.responses.PaylevenTestResponse;

import retrofit.http.GET;
import rx.Observable;

public interface IPaylevenApi {

    @GET("/payleven_challenge_list.json")
    Observable<PaylevenTestResponse> getTestItems();

}
