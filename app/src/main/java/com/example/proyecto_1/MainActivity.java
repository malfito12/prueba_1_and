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

public class MainActivity extends AppCompatActivity{
    EditText txt_e,txt_p;
    Button btn_i,btn_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_e=findViewById(R.id.txt_email);
        txt_p=findViewById(R.id.txt_password);
        btn_i=findViewById(R.id.btn_login);
        btn_r=findViewById(R.id.btn_register);

    }

    public void LoginAcepted(View view){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params= new RequestParams();
        params.put("email",txt_e.getText().toString());
        params.put("password",txt_p.getText().toString());
        client.post("http://192.168.100.6:8000/login",params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String resp= response.getString("msn");
                    if (resp.equals("autenticacion exitosa")){
                        Intent i = new Intent(MainActivity.this,MainActivity2.class);
                        Utilidades.token=response.getString("token");
                        startActivity(i);
                    }else if (resp.equals("el password es incorrecto")){
                        Toast.makeText(MainActivity.this,"error en la autenticacion", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),resp,Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void register(View view){
        Intent i = new Intent(MainActivity.this, register_usuario.class);
        startActivity(i);
    }

}