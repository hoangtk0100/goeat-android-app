package tkh_15110211.vn.edu.hcmute.goeat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{
    Button btnLogIn;
    EditText edEmailDN, edPasswordDN;
    private FirebaseAuth firebaseAuthAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        firebaseAuthAuth = FirebaseAuth.getInstance();
        firebaseAuthAuth.signOut();

        Mapping();

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });
    }

    private void LogIn(){
        String email = edEmailDN.getText().toString();
        String pass = edPasswordDN.getText().toString();

        firebaseAuthAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this,"Failed Log In", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Mapping() {
        btnLogIn = findViewById(R.id.btnLogin);
        edEmailDN= findViewById(R.id.edEmailDN);
        edPasswordDN = findViewById(R.id.edPasswordDN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuthAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuthAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            Toast.makeText(LogInActivity.this,"Successfully Log In " + user.getEmail(), Toast.LENGTH_SHORT).show();
        }
    }
}
