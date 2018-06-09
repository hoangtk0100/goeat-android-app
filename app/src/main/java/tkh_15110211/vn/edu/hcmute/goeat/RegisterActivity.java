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

public class RegisterActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{
    Button btnSignUp;
    EditText edEmailDK, edPasswordDK,edRePasswordDK;
    private FirebaseAuth firebaseAuthAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

        firebaseAuthAuth = FirebaseAuth.getInstance();

        Mapping();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edPasswordDK.getText().toString();
                String repass = edRePasswordDK.getText().toString();
                if(isValid(pass,repass)){
                    SignUp();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Failed!! - Password and Repassword is not the same", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isValid(String pass, String repass){
        if(!pass.equals(repass))
            return false;
        return true;
    }

    private void SignUp(){
        String email = edEmailDK.getText().toString();
        String pass = edPasswordDK.getText().toString();

        firebaseAuthAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,"Successfull", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this,"Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Mapping()
    {
        btnSignUp = findViewById(R.id.btnSignUp);
        edEmailDK = findViewById(R.id.edEmailDK);
        edPasswordDK = findViewById(R.id.edPasswordDK);
        edRePasswordDK = findViewById(R.id.edRePasswordDK);
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
            Log.d("check----------",user.getEmail());
        }
    }
}
