package mahmoud.mahmoudfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler h=new Handler();
        Runnable r= new Runnable()
        {
            @Override
            public void run()
            {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                if (auth.getCurrentUser()==null)
                {
                    Intent i = new Intent(SplashScreen.this, SignIn.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }

        };
        h.postDelayed(r,5000);//نتهي بعد عدد من الثواني
    }

}