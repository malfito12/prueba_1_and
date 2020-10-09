package com.example.proyecto_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class register_usuario extends AppCompatActivity {
    EditText tr_name,tr_email,tr_pass,tr_address;
    Button btn_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_usuario);
        tr_name=findViewById(R.id.regiter_name);
        tr_pass=findViewById(R.id.register_password);
        tr_email=findViewById(R.id.register_email);
        tr_address=findViewById(R.id.resgister_address);
        btn_r=findViewById(R.id.btn_register);
    }

    public void registerUser(final View view) {
        AsyncHttpClient client= new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("name",tr_name.getText().toString());
        params.put("password",tr_pass.getText().toString());
        params.put("email",tr_email.getText().toString());
        params.put("address",tr_address.getText().toString());
        client.post("http://192.168.100.6:8000/user",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if(view.getId()==R.id.btn_register){
                    Intent i = new Intent(register_usuario.this, MainActivity.class);
                    Toast.makeText(register_usuario.this,"Usuario regitrado",Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }


}