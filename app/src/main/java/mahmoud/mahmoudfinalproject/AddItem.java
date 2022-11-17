package mahmoud.mahmoudfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import mahmoud.mahmoudfinalproject.Data.ClothesItem;

public class AddItem extends AppCompatActivity
{
    private TextInputEditText EdTitle;//عنوان
    private TextView TDate;
    private EditText EdDate;//التاريخ
    private TextView TType;
    private TextInputEditText EdType;//النوع
    private ImageButton IbClothes;
    private Button BnAdd;
    private Button BnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        EdTitle=findViewById(R.id.EdTile);
        TDate=findViewById(R.id.TDate);
        EdDate=findViewById(R.id.EdDate);
        TType=findViewById(R.id.TType);
        EdType=findViewById(R.id.EdType);
        IbClothes=findViewById(R.id.IbClothe);
        BnAdd=findViewById(R.id.BnAdd);
        BnCancel=findViewById(R.id.BnCancel);

        BnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                checkAndSave();

            }
        });
        BnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i =new Intent(AddItem.this,MainActivity.class);
                startActivity(i);

            }
        });
    }

    private void checkAndSave()
    {

        String title=EdTitle.getText().toString();
        String date=EdDate.getText().toString();
        String type=EdType.getText().toString();

        ClothesItem item=new ClothesItem();
        item.setTitle(title);
        item.setDate(date);
        item.setType(type);

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
                            Toast.makeText(AddItem.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(AddItem.this, "Add Failled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}