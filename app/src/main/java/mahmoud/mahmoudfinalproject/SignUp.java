package mahmoud.mahmoudfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends AppCompatActivity {

    private TextInputEditText ETmup;
    private TextInputEditText ETpup;
    private TextInputEditText ETreup;
    private Button BNsave;
    private Button BNcan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ETmup = findViewById(R.id.ETmup);
        ETpup = findViewById(R.id.ETpup);
        ETreup = findViewById(R.id.ETreup);
        BNsave = findViewById(R.id.BNsave);
        BNcan = findViewById(R.id.BNcan);

        BNsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndSave();
            }
        });
        BNcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this, SignIn.class);
                startActivity(i);
            }
        });
    }

        private void checkAndSave()//داله تفحص اذا اليميل صحيح وكلمة السر تطابق الاخرى
        {
            String email=ETmup.getText().toString();
            String RePasw=ETreup.getText().toString();
            String passw=ETpup.getText().toString();
            boolean isok=true;

            if(email.length()*passw.length()*RePasw.length()==0)
            {
                ETmup.setError("One of files are eror");
                isok=false;
            }

            if(passw.equals(RePasw)==false)
            {
                ETreup.setError("enter the same password");
                isok=false;
            }
            if(passw.length()==0)
            {
                ETpup.setError("enter your password");
                isok=false;
            }
            if(email.indexOf("@")<=0)
            {
                ETmup.setError("wrong email");
                isok=false;
            }
            if(passw.length()<7)
            {
                ETpup.setError("password at least 7 character");
                isok=false;
            }

    }


}