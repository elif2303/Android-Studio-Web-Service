package com.example.asansor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;

public class MainActivity extends AppCompatActivity {

    Button connect;
    EditText Emaill,Sifree;
    final String url = "http://www.pioturk.com/api/values";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = (Button) findViewById(R.id.connect);
        Emaill = (EditText) findViewById(R.id.Email);
        Sifree = (EditText) findViewById(R.id.Sifre);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Emaill.getText().toString();
                String sifre = Sifree.getText().toString();

                new LoginUser().execute(email,sifre);
            }
        });
    }

    public class LoginUser extends AsyncTask <String, Void, String>{

        protected String doInBackground(String... strings){

            String email = strings[0];
            String sifre = strings[1];
            String final_url = url + "?id=1071"+
                              "&Email=" + email+
                              "&Sifre=" + sifre;


            OkHttpClient okHttpClient= new OkHttpClient();

            //RequestBody formBody = new FormBody.Builder()
                    //.add("Email", email)
                    //.add("Sifre", sifre)
                    //.build();

            Request request = new Request.Builder()
                    .url(final_url)
                    .get()
                    .build();




            okHttpClient.newCall(request).enqueue(new Callback(){
                @Override
                public void onFailure(final Call call, final IOException e){
                    runOnUiThread(new Runnable(){
                       @Override
                       public void run(){
                           Toast.makeText(MainActivity.this,"Hatalı Giriş", Toast.LENGTH_SHORT).show();
                           call.cancel();
                       }
                    });
                }
                @Override
                public void onResponse(Call call, final Response response) throws IOException{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Toast.makeText(MainActivity.this,response.body().string(),Toast.LENGTH_SHORT).show();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

            return null;
        }
    }
}
