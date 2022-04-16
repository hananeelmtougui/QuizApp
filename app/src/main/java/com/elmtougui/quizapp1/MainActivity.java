package com.elmtougui.quizapp1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //Step 1: Declaration
    EditText etLogin, etPassword;
    Button bLogin;
    TextView tvRegister,geolocation;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Step 2: Recuperation des ids
        etLogin = (EditText) findViewById(R.id.etMail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        geolocation=(TextView) findViewById(R.id.geolocation);
        mAuth=FirebaseAuth.getInstance();
        //Step 3: Association de listeners
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Step 4: Traitement
               switch(view.getId()){
                   case R.id.bLogin:
                       userLogin();
                       break;
               }
            }

            private void userLogin(){
                String email=etLogin.getText().toString().trim();
                String password=etPassword.getText().toString().trim();

                if(email.isEmpty()){
                    etLogin.setError("Email est obligatoire !");
                    etLogin.requestFocus();
                    return;
                }

               

                if(password.isEmpty()){
                    etPassword.setError("Le mot de passe est obligatoire !");
                    etPassword.requestFocus();
                    return;
                }

                if(password.length()<6 ){
                    etPassword.setError("Le minimum des caractères requis est 6 !");
                    etPassword.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //redirect to Quiz 1
                            startActivity(new Intent(MainActivity.this, Quiz1.class));
                            Toast.makeText(MainActivity.this, "Bienvenue dans le Quizz!!", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(MainActivity.this, "Impossible de vous connectez réessayez une nouvelle fois !!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });







        //pour bouton de redirection vers registration
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Step 4: Traitement
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

        //pour se rediriger vers la map
        geolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });


    }
}
