package com.android.nabila.usermakeup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.nabila.usermakeup.Model.GetLogin;
import com.android.nabila.usermakeup.Model.User;
import com.android.nabila.usermakeup.Rest.ApiClient;
import com.android.nabila.usermakeup.Rest.ApiInterface;

import java.util.List;
import retrofit2.Callback;

import retrofit2.Response;

public class Login extends AppCompatActivity {
    private CheckBox chkRemember;
    private Button btnLogin,btnSignup;
    private EditText txtUsername, txtPassword;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.btnLogin    = this.findViewById(R.id.buttonLogin);
        this.btnSignup    = this.findViewById(R.id.buttonSigup);
        this.txtUsername = this.findViewById(R.id.txtUsername);
        this.txtPassword = this.findViewById(R.id.txtPassword);

        if(this.isLoggedIn()) {
            Intent intent = new Intent(this.getApplicationContext(), Home.class);
            this.startActivity(intent);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                // Cek form login
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Lakukan login
                    doLogin(username, password);
                } else {
                    // Notif user
                    Toast.makeText(getApplicationContext(),
                            "Isikan username dan password!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(Login.this,SignUp.class);
               startActivity(i);
            }
        });
    }
    private boolean isLoggedIn()
    {
        // Cek apakah ada shared pref login
        SharedPreferences pref = getSharedPreferences("LoginData", MODE_PRIVATE);
        return pref.getBoolean("isloggedin", false);

    }

    // Buka layar home
    private void openHome()
    {
        Intent intent = new Intent(this.getApplicationContext(), Home.class);
        this.startActivity(intent);
    }

    private void saveLogin(String username, String idUser)
    {
        // Simpan data login ke shared pref
        SharedPreferences sharedpref = getSharedPreferences("TokTikLoginData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();

        // Simpan isloggedin true berupa boolean
        editor.putBoolean("isloggedin", true);
        // Simpan data lainnya berupa string
        editor.putString("username", username);
        editor.putString("idUser", idUser);
        editor.apply();
    }

    // Untuk proses login menggunakan REST
    private void doLogin(final String username,final String password){
        // Panggil request Api
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call loginCall = mApiInterface.loginUser(username, password);

        loginCall.enqueue(new Callback() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                if(response.isSuccessful()){

                    // Buat object model GetLogin dari response
                    GetLogin loginobject = (GetLogin) response.body();
                    String idUser = loginobject.getResult();

                    // Jika status = success (sesuai respon dari REST server)
                    if(loginobject.getStatus().equals("success")){
                        // Simpan data email user ke sharedpref
                        saveLogin(username, idUser);
                        // Buka layar home
                        openHome();
                    } else {
                        Toast.makeText(Login.this,
                                "Username atau password salah",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this,
                            "Error! Coba lagi!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {
                // Jika gagal, beri notif
                Toast.makeText(Login.this, "Gagal:" + t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
