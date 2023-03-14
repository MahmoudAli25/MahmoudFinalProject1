package mahmoud.mahmoudfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;

import mahmoud.mahmoudfinalproject.Data.Tshirt;

public class AddTshirt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PERMISSION_CODE = 101;
    private static final int IMAGE_PICK_CODE = 100;



    private TextInputEditText TeEvent;//عنوان
    private RatingBar RbImportant;
    private ImageView IbTshirt;//رفع صوره
    private Button BnAdd;
    private Button BnCancel;
    private Uri filePath;
    private Uri toUploadimageUri;
    private Uri downladuri;
    StorageTask UploadTshirt;
    private Tshirt t= new Tshirt();

    private Spinner ColorSpinner;


    public AddTshirt()
    {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tshirt);

        ColorSpinner = findViewById(R.id.BColorSp);
        TeEvent = findViewById(R.id.TeEvent);

        RbImportant=findViewById(R.id.RbImportant);
        BnAdd = findViewById(R.id.BAdd);
        BnCancel = findViewById(R.id.BaCancel);
        //upload: 3
        IbTshirt = findViewById(R.id.IbBants);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ColorSpinner.setAdapter(adapter);
        ColorSpinner.setOnItemSelectedListener(this);

        SharedPreferences preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        String key = preferences.getString("key", "");
        if (key.length() == 0)
        {
            Toast.makeText(this, "No key found", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "key:" + key, Toast.LENGTH_SHORT).show();
        }

        //upload: 4
        IbTshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                Toast.makeText(getApplicationContext(), "image", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        pickImageFromGallery();
                    }

                }
            }
        });

       //upload: 6
       //Add
        BnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                datapick();
            }
        });
        //Cancel
        BnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddTshirt.this, MainActivity.class);
                startActivity(i);

            }
        });
    }
        //upload: 5
        private void uploadImage(Uri filePath)
        {

            if(filePath != null)
            {
                final ProgressDialog progressDialog = new ProgressDialog(this);//بناء دايلوج من نوع اخر
                progressDialog.setTitle("Uploading...");//عنوان الدايلوج
                progressDialog.show();//اظهار الدايلوج
                FirebaseStorage storage= FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                UploadTshirt=ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();//رفض او الغاء الدايلوج
                                ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task)
                                    {
                                        downladuri = task.getResult();
                                        t.setImage(downladuri.toString());
                                        checkAndSave(t);

                                    }
                                });

                                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                //اظهار دايلوج مكتوب فيه انه لم يعمل
                                Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })

                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                //اظهار دايلوج انه يعمل
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }else
            {
                t.setImage("");
                checkAndSave(t);
            }
        }

    private void datapick()
    {
        boolean isok=true;
        String event=TeEvent.getText().toString();
        int important=RbImportant.getProgress();
        String color=ColorSpinner.getSelectedItem().toString();

        if(event.length()==0)
        {
            TeEvent.setError("event cant be empyt");
            isok=false;
        }



        if(isok)
        {
            t.setTimes(Calendar.getInstance().getTimeInMillis());//current time
            t.setEvent(event);
            t.setImportant(important);
            t.setColor(color);

            if(UploadTshirt!=null || (UploadTshirt!=null && UploadTshirt.isInProgress()))
            {
            Toast.makeText(this, " UploadTshirt.isInProgress(", Toast.LENGTH_SHORT).show();
            }
           uploadImage(toUploadimageUri);
    }
}


    private void checkAndSave(Tshirt t)
    {

        //استخراج الرقم المميز للمستخدم UID
        //                                          مستخدم مسبق
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        t.setOwner(owner);
        //استخراج الرقم المميز للمهمه
        String key = FirebaseDatabase.getInstance().getReference().
                child("ClothesItem").
                child("Tshirt").
                //اضافة قيمه جديده
                        child(owner).push().getKey();
        t.setKey(key);
        //عنوان جذر قاعدة البيانات
        FirebaseDatabase.getInstance().getReference().
                child("ClothesItem").
                child("Tshirt").
                child(owner).
                child(key).
                setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
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


    private void pickImageFromGallery(){
        //intent to pick image
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission was granted
                    pickImageFromGallery();
                }
                else
                {
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //handle result of picked images
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode==RESULT_OK && requestCode== IMAGE_PICK_CODE)
        {
            //set image to image view
            toUploadimageUri = data.getData();
            IbTshirt.setImageURI(toUploadimageUri);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


