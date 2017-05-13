package com.the.dionisio.apk.client.dao.api.personApi;

import android.util.Log;
import com.the.dionisio.apk.client.dao.api.ServiceGenerator;
import com.the.dionisio.apk.client.model.dto.Person;
import com.the.dionisio.apk.client.model.dto.Validation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dantas on 3/25/17.
 */

public class PersonDataConverter {

    public static final String TAG = "Person";

    public void getPerson(String id)
    {
        ServicePersonApi ServicePersonApi = ServiceGenerator.createService(ServicePersonApi.class);
        Call<Person> request = ServicePersonApi.getPerson(id);

        request.enqueue(new Callback<Person>()
        {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response)
            {
                Person person = response.body();

                if(person != null)
                {
                    if(response.isSuccessful())
                    {
                        Log.i(TAG, "Sucessfull - code: " + response.code() + " username: " + person.email + "; _id: " + person._id);
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

            @Override
            public void onFailure(Call<Person> call, Throwable t)
            {
                Log.e(TAG, "Failure to communication with the server!");
            }
        });
    }

    public void postPerson(Person person)
    {
        ServicePersonApi ServicePersonApi = ServiceGenerator.createService(ServicePersonApi.class);
        Call<Validation> request = ServicePersonApi.postPerson(person);

        request.enqueue(new Callback<Validation>()
        {
            @Override
            public void onResponse(Call<Validation> call, Response<Validation> response)
            {
                String[] _id;
                Validation validation = response.body();

                if(validation != null)
                {
                    if(response.isSuccessful())
                    {
                        Log.i(TAG, "Sucessfull - code: " + response.code() + " additional: " + validation.additional);
                        _id = validation.additional.toString().trim().split("=");
                        person._id = _id[1];
                        Log.i(TAG, "_id: " + person._id);
                        //SQLite.actionPerson.createPerson(person);
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

            @Override
            public void onFailure(Call<Validation> call, Throwable t)
            {
                Log.e(TAG, "Failure to communication with the server!");
            }
        });
    }

    public void deletePerson(String id)
    {
        ServicePersonApi ServicePersonApi = ServiceGenerator.createService(ServicePersonApi.class);
        Call<Validation> request = ServicePersonApi.deletePerson(id);

        request.enqueue(new Callback<Validation>()
        {
            @Override
            public void onResponse(Call<Validation> call, Response<Validation> response)
            {
                Validation validation = response.body();

                if(validation != null)
                {
                    if(response.isSuccessful())
                    {
                        Log.i(TAG, "Sucessfull - code: " + response.code() + " additional: " + validation.additional);
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

            @Override
            public void onFailure(Call<Validation> call, Throwable t)
            {
                Log.e(TAG, "Failure to communication with the server!");
            }
        });
    }

    public void updatePerson(Person person)
    {
        ServicePersonApi ServicePersonApi = ServiceGenerator.createService(ServicePersonApi.class);
        Call<Validation> request = ServicePersonApi.updatePerson(person);

        request.enqueue(new Callback<Validation>()
        {
            @Override
            public void onResponse(Call<Validation> call, Response<Validation> response)
            {
                Validation validation = response.body();

                if(validation != null)
                {
                    if(response.isSuccessful())
                    {
                        Log.i(TAG, "Sucessfull - code: " + response.code() + " additional: " + validation.additional);
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

            @Override
            public void onFailure(Call<Validation> call, Throwable t)
            {
                Log.e(TAG, "Failure to communication with the server!");
            }
        });
    }
}
