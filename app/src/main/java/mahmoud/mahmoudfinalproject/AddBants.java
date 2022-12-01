package mahmoud.mahmoudfinalproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import mahmoud.mahmoudfinalproject.Data.Bants;

public class AddBants extends AppCompatActivity
{
    private TextInputEditText TeEvent;//عنوان
    private TextView TVevent;
    private TextView TvDate;
    private TextInputEditText TeDate;//التاريخ
    private ImageButton IbBants;//رفع صوره
    private Button BAdd;
    private Button BaCancel;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bants);

        TeEvent=findViewById(R.id.TeEvent);
        TvDate=findViewById(R.id.TvDate);
        TeDate=findViewById(R.id.TeDate);
        IbBants=findViewById(R.id.IbTshirt);
        BAdd=findViewById(R.id.BAdd);
        BaCancel=findViewById(R.id.BaCancel);

        //Add Image
        IbBants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(m,3);
            }
        });

        BAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                checkAndSave();

            }
        });
        BaCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i =new Intent(AddBants.this,MainActivity.class);
                startActivity(i);

            }
        });
    }

    private void checkAndSave()
    {

        String eventBants=TeEvent.getText().toString();
        String dateBants=TeDate.getText().toString();
        ImageView imageBants=IbBants;

        Bants item=new Bants();
        item.setEvent(eventBants);
        item.setDate(dateBants);
        item.setImage(imageBants);

        //استخراج الرقم المميز للمستخدم UID
        //                                          مستخدم مسبق
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        item.setOwner(owner);
        //استخراج الرقم المميز للمهمه
        String key = FirebaseDatabase.getInstance().getReference().
                child("ClothesItem").
                //اضافة قيمه جديده
                        child(owner).push().getKey();
        item.setKey(key);
        //عنوان جذر قاعدة البيانات
        FirebaseDatabase.getInstance().getReference().
                child("ClothesItem").
                child(owner).
                child(key).
                setValue(item).addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            finish();
                            Toast.makeText(AddBants.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(AddBants.this, "Add Failled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            ImageView imageView=findViewById(R.id.IVtshirt);
            imageView.setImageURI(selectedImage);

        }
    }
}