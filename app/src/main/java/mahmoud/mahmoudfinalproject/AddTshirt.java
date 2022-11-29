package mahmoud.mahmoudfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import mahmoud.mahmoudfinalproject.Data.Tshirt;

public class AddTshirt extends AppCompatActivity {
    private TextInputEditText EdEvent;//عنوان
    private TextView TDate;
    private EditText EdDate;//التاريخ
    private ImageButton IbClothes;//رفع صوره
    private Button BnAdd;
    private Button BnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tshirt);

        EdEvent = findViewById(R.id.TeEvent);
        TDate = findViewById(R.id.TvDate);
        EdDate = findViewById(R.id.TeDate);
        IbClothes = findViewById(R.id.IbBants);
        BnAdd = findViewById(R.id.BAdd);
        BnCancel = findViewById(R.id.BaCancel);

        //Add Image
        IbClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(m, 3);
            }
        });

        BnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSave();

            }
        });
        BnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddTshirt.this, MainActivity.class);
                startActivity(i);

            }
        });
    }

    private void checkAndSave() {

        String event = EdEvent.getText().toString();
        String date = EdDate.getText().toString();
        ImageView image = IbClothes;

        Tshirt item = new Tshirt();
        item.setEvent(event);
        item.setDate(date);
        item.setImage(image);

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
                setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            finish();
                            Toast.makeText(AddTshirt.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddTshirt.this, "Add Failled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.IVtshirt);
            imageView.setImageURI(selectedImage);

        }
    }
}
