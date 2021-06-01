package com.example.collegeinfo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeinfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupFragment extends Fragment {

    private EditText email, password, confirmpassword;
    private EditText name;
    private Button btnSignUp;
    private TextView textViewLogin;

    private String s_name, s_email, s_password, s_confirmpassword;

    private FirebaseAuth mAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        mAuth = FirebaseAuth.getInstance();

        setView(view);
        return view;
    }

    private void setView(View view){
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirmpassword = view.findViewById(R.id.confirmpassword);
        btnSignUp = view.findViewById(R.id.button2);
        textViewLogin = view.findViewById(R.id.textViewLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_name = name.getText().toString();
                s_email = email.getText().toString();
                s_password = password.getText().toString();
                s_confirmpassword = confirmpassword.getText().toString();

                if(s_password.equals(s_confirmpassword)){
                    addUser();
                }
                else{
                    Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame_layout_signup_login,
                        new LoginFragment()).commit();
            }
        });



    }

    private void addUser() {
        mAuth.createUserWithEmailAndPassword(s_email, s_password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(getContext(), "Registration failed"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}