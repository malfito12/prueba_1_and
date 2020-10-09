package com.example.proyecto_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity2 extends AppCompatActivity {
    ListView lis;
    String aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lis=findViewById(R.id.list_main);


        if (Utilidades.token==null){
            Intent i = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(i);
        }else {
            loadComponents();
        }

    }

    private void loadComponents() {
        AsyncHttpClient client= new AsyncHttpClient();
        final ArrayList<Item> list_data= new ArrayList<Item>();
        client.get("http://192.168.100.6:8000/user",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for(int i=0;i<response.length();i++){
                    try {
                        Item it= new Item();
                        JSONObject resp= response.getJSONObject(i);
                        it.id=resp.getString("_id");
                        it.name=resp.getString("name");
                        it.email=resp.getString("email");
                        aa=it.id;
                        list_data.add(it);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ListAdapter adapter= new ListAdapter(MainActivity2.this,list_data);
                lis.setAdapter(adapter);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }



    public void deleteUsu(View view){
        AsyncHttpClient client = new AsyncHttpClient();
        client.delete("http://192.168.100.6:8000/user/"+aa,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                    loadComponents();
                    Toast.makeText(MainActivity2.this,"Usuario Eliminado", Toast.LENGTH_SHORT).show();
            }
        });
    }


}