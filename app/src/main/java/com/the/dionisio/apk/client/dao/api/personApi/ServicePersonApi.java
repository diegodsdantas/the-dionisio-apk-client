package com.the.dionisio.apk.client.dao.api.personApi;

import com.the.dionisio.apk.client.dao.api.loginApi.Entity;
import com.the.dionisio.apk.client.model.dto.Person;
import com.the.dionisio.apk.client.model.dto.Validation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Dantas on 3/25/17.
 */

public interface ServicePersonApi
{
    @POST("/person")
    Call<Validation> postPerson(@Body Person p);

    @DELETE("/person")
    Call<Validation> deletePerson(@Header("X-Auth-Token") String token, @Body String id);

    @PUT("/person")
    Call<Validation> updatePerson(@Header("X-Auth-Token") String token, @Body Person p);
}
