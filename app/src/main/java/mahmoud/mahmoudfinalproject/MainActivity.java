package mahmoud.mahmoudfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.ITMset)//يفحص هل عند الضغط عزر تطابق الارقام
        {
            Intent i=new Intent(MainActivity.this,Settings.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.ITMout)
        {
            //تسجيل الخروج
            //1
            // تجهيز البناء للدايلوج
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Signing Out");
            builder.setMessage("are you sure?");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    //اخفاء الديالوج
                    dialogInterface.dismiss();
                    //الخروج من الحساب
                    FirebaseAuth.getInstance().signOut();
                    //لخروج من الشاشه
                    finish();
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    //الغاء الدايالوج
                    dialogInterface.cancel();
                }
            });
            //البناء
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        if (item.getItemId()==R.id.ITMhist)
        {
            Intent d=new Intent(MainActivity.this,History.class);
        }
        return true;
    }

}
