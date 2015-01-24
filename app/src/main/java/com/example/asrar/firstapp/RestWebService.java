package com.example.asrar.firstapp;

import android.content.Context;

import com.restservice.Http;
import com.restservice.HttpFactory;
import com.restservice.HttpResponse;
import com.restservice.NetworkError;
import com.restservice.ResponseHandler;

/**
 * Created by asrar on 12/20/2014.
 */
public class RestWebService {

    Context context;

    public RestWebService(Context context){
        this.context = context;
    }

    public void onSuccess(String data, HttpResponse response){

    }

    public void onComplete(){

    }

    public void onFailure(NetworkError error){

    }

    public void onError(String message, HttpResponse response){

    }


    public void getCall(String url) {

        Http http = HttpFactory.create(this.context);
        http.get(url)
                .handler(new ResponseHandler<String>() {
            @Override
            public void success(String data, HttpResponse response) {
                onSuccess(data, response);
                super.success(data, response);
            }

            @Override
            public void complete() {
                onComplete();
                super.complete();
            }

            @Override
            public void failure(NetworkError error) {
                onFailure(error);
                super.failure(error);
            }

            @Override
            public void error(String message, HttpResponse response) {
                onError(message, response);
                super.error(message, response);
            }
        }).send();

    }

}
