package com.sisihub.pushnotification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

/**
 * Created by HERONS on 8/10/2016.
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String REG_TOKEN = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recentToken);
        registerToken(recentToken);
    }

    public void registerToken(String token){

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder()
                .add( "Token", token )
                .build();
        Request request = new Request.Builder()
                .url( "http://localhost/fcm/register.php" )
                .post( formBody )
                .build();
        try {
            client.newCall( request ).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()

                .add( "token", token )
                .build();
        Request request = new Request.Builder()
                .url( "" )
                .post( formBody )
                .build();
        try {
            client.newCall( request ).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
}
