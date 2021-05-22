package com.example.studentscms.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentscms.MainActivity;
import com.example.studentscms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText fName, email, pwd, enroll, num;
    private Button btnReg;
    private String sName, sEmail, sPwd, sEnroll, sNum, department, semester;

    private FirebaseAuth auth;
    private DatabaseReference reference, deRef;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        pd = new ProgressDialog(this);

        fName = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        pwd = findViewById(R.id.reg_password);
        enroll = findViewById(R.id.reg_enroll);
        num = findViewById(R.id.reg_phone);
        btnReg = findViewById(R.id.btn_reg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            openMain();
        }
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void validateData() {

        Spinner sDepartment = findViewById(R.id.reg_department);
        Spinner sSemester = findViewById(R.id.reg_semester);

        department = sDepartment.getSelectedItem().toString();
        semester = sSemester.getSelectedItem().toString();

        sName = fName.getText().toString();
        sEmail = email.getText().toString();
        sPwd = pwd.getText().toString();
        sEnroll = enroll.getText().toString();
        sNum = num.getText().toString();

        if (sName.isEmpty()) {
            fName.setError("Required");
            fName.requestFocus();
        } else if (sEmail.isEmpty()) {
            email.setError("Required");
            email.requestFocus();
        } else if (sPwd.isEmpty()) {
            pwd.setError("Required");
            pwd.requestFocus();
        } else if (sEnroll.isEmpty()) {
            enroll.setError("Required");
            enroll.requestFocus();
        } else if (sNum.isEmpty()) {
            num.setError("Required");
            num.requestFocus();
        } else if (department.equals("Department")){
            Toast.makeText(this, "Select Department", Toast.LENGTH_SHORT).show();
        } else if (semester.equals("Semester")){
            Toast.makeText(this, "Select Semester", Toast.LENGTH_SHORT).show();
        } else {
            createUser();
        }
    }

    private void createUser() {
        pd.setMessage("Loading...");
        pd.show();
        auth.createUserWithEmailAndPassword(sEmail, sPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    uploadData();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData() {
        deRef = reference.child("students");
        String key = deRef.push().getKey();

        HashMap<String, String> map = new HashMap<>();
        map.put("key", key);
        map.put("name", sName);
        map.put("email", sEmail);
        map.put("enroll", sEnroll);
        map.put("department", department);
        map.put("semester", semester);
        map.put("phone", sNum);
        map.put("password", sPwd);

        deRef.child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Registration Successful!!!", Toast.LENGTH_SHORT).show();
                    openMain();
                } else {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}