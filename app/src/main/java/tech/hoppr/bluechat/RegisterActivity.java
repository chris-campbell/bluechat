package tech.hoppr.bluechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText userEmail, userPassword;
    private TextView alreadyHaveAccountLink;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        initializeFields();

        alreadyHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLoginActivity();
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create new account");
            loadingBar.setMessage("Please wait while we create new account");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            String currentUserID = mAuth.getCurrentUser().getUid();
                            rootRef.child("Users").child(currentUserID).setValue("");

                            sendUserToMainActivity();
                            Toast.makeText(RegisterActivity.this, "Account Creation successful", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(RegisterActivity.this, "Error, " + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    });
        }
    }

    private void sendUserToMainActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void initializeFields() {
        createAccountButton = findViewById(R.id.register_button);
        userEmail = findViewById(R.id.register_email);
        userPassword = findViewById(R.id.register_password);
        alreadyHaveAccountLink = findViewById(R.id.already_have_account_link);

        loadingBar = new ProgressDialog(this);
    }

    private void sendUserToLoginActivity() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}