package com.the.dionisio.apk.client.dao.api;

import android.content.Context;
import android.util.Log;
import com.the.dionisio.apk.client.model.dto.Person;
import com.the.dionisio.apk.client.model.dto.Token;
import com.the.dionisio.apk.client.model.dto.Validation;
import com.the.dionisio.apk.client.model.presenter.Presenter;
import com.the.dionisio.apk.client.retrofit.RetrofitFactory;
import com.the.dionisio.apk.client.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by igorm on 3/25/17.
 */

public class RequestPerson
{
    private RetrofitFactory retrofitFactory = new RetrofitFactory();
    public static final String TAG = "PERSON";

    public void postPerson(Person person, Context context, String typeLogin)
    {
        try
        {
            Call request = retrofitFactory.getPersonService().postPerson(person);

            request.enqueue(new Callback()
            {
                @Override
                public void onResponse(Call call, Response response)
                {
                    validationPerson(response, null, person, context, typeLogin);
                }

                @Override
                public void onFailure(Call call, Throwable t)
                {
                    Log.e(TAG, "Failure to communication with the server!");
                }
            });
        }
        catch(Exception error)
        {

        }
    }

    public void updatePerson(Token token, Person person, Context context)
    {
        Call request = retrofitFactory.getPersonService().putPerson(token.token, person);

        request.enqueue(new Callback()
        {
            @Override
            public void onResponse(Call call, Response response)
            {
                validationPerson(response, token, person, context, null);
            }

            @Override
            public void onFailure(Call call, Throwable t)
            {
                Log.e(TAG, "Failure to communication with the server!");
            }
        });
    }

    private void validationPerson(Response response, Token token, Person person, Context context, String typeLogin)
    {
        Validation validation = (Validation) response.body();

        if(validation != null)
        {
            if(response.isSuccessful())
            {
                Log.i(TAG, "Sucessfull - code: " + response.code() + " description: " + validation.description);

                Person newPerson = validation.additional;

                Util.validationResponse.validationPerson(response.code(), newPerson, context);
                isPostOrUpdate(response, token, newPerson, context, typeLogin);
            }
            else
            {
                Log.e(TAG, "Failed - code: " + response.code());
                Util.validationResponse.validationPerson(response.code(), person, context);
            }
        }
        else
        {
            Log.e(TAG, "Failed, the data does not match, code: " + response.code());
            Util.validationResponse.validationPerson(response.code(), person, context);
        }
    }

    private void isPostOrUpdate(Response response, Token token, Person person, Context context, String typeLogin)
    {
        if(token == null && typeLogin != null)
        {
            Presenter.loginAction.startLogin(person ,Util.setData.setLogin(person.email, person.password), context, typeLogin);
        }
        else
        {
            Util.validationResponse.validationLogin(response.code(), person, null, context, token, null);
        }
    }

    public void deletePerson(Token token, Person person, Context context)
    {
        Call<Validation> request = retrofitFactory.getPersonService().deletePerson(token.token, person._id);

        request.enqueue(new Callback<Validation>()
        {
            @Override
            public void onResponse(Call<Validation> call, Response<Validation> response)
            {
                validationDelete(response);
            }

            @Override
            public void onFailure(Call<Validation> call, Throwable t)
            {
                Log.e(TAG, "Failure to communication with the server!");
            }
        });
    }

    public void validationDelete(Response response)
    {
        Validation validation = (Validation) response.body();

        if(validation != null)
        {
            if(response.isSuccessful())
            {
                Log.i(TAG, "Sucessfull - code: " + response.code() + " description: " + validation.description);
            }
            else
            {
                Log.e(TAG, "Failed - code: " + response.code());
            }
        }
        else
        {
            Log.e(TAG, "Failed, the data does not match, code: " + response.code());
        }
    }
}
