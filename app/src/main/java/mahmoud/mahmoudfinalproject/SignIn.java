package mahmoud.mahmoudfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * SignIn
 */
public class SignIn extends AppCompatActivity
{
    TextInputEditText ETEmail;
    TextInputEditText ETpas;
    private Button BNin;
    private Button BNup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ETEmail = findViewById(R.id.ETEmail);
        ETpas = findViewById(R.id.ETpas);
        BNin = findViewById(R.id.BNin);
        BNup = findViewById(R.id.BNup);

        BNup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(SignIn.this,SignUp.class);
                startActivity(i);
            }
        }
        );

        BNin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                        checkAndSave();
                    }
        }
        );
    }
    private void checkAndSave()
    {
        String email=ETEmail.getText().toString();
        String passw=ETpas.getText().toString();

        boolean isOK=true;
        if(email.length()==0)
        {
            ETEmail.setError("enter your email");
            isOK=false;
        }
        if(passw.length()==0)
        {
            ETpas.setError("enter your password");
            isOK=false;
        }
        if(email.indexOf("@")<=0)
        {
            ETEmail.setError("wrong email");
            isOK=false;
        }
        if(passw.length()<7)
        {
            ETpas.setError("password at least 7 character");
            isOK=false;
        }
        if(isOK)
        {
            FirebaseAuth auth= FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())//يفحص اذا المهم تمت بنجاح
                    {
                        Toast.makeText(SignIn.this, "Successful", Toast.LENGTH_SHORT).show();//تظهر رساله في الشاشه من الاسفل
                        Intent i=new Intent(SignIn.this,MainActivity.class);
                        startActivity(i);
                        finish();//يغلق الشاشه الحاليه
                    }
                    else
                    {
                        Toast.makeText(SignIn.this, "Not Successful"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

}