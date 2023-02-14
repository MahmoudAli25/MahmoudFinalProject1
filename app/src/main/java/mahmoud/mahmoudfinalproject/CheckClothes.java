package mahmoud.mahmoudfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import mahmoud.mahmoudfinalproject.Data.Bants;
import mahmoud.mahmoudfinalproject.Data.Tshirt;

public class CheckClothes extends AppCompatActivity
{
    private ImageSlider TshirtSlider;
    private ImageSlider BantsSlider;
    ArrayList<SlideModel> TslideModels =new ArrayList<>();
    ArrayList<SlideModel> BslideModels =new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_clothes);

        TshirtSlider = findViewById(R.id.TshirtSlider);
        BantsSlider = findViewById(R.id.BantsSlider);

        //craet a list for images
       ReadClothesItemtFromFireBase();

//        TslideModels.add(new SlideModel("@drawable/logomahmod",ScaleTypes.FIT));
//        TslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
//        TslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
//        TslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
//        TslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
//
//        TshirtSlider.setImageList(TslideModels, ScaleTypes.FIT);
//
//
//        BslideModels.add(new SlideModel("@drawable/logomahmod",ScaleTypes.FIT));
//        BslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
//        BslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
//        BslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
//        BslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
//
//        BantsSlider.setImageList(BslideModels, ScaleTypes.FIT);

    }
    private void ReadClothesItemtFromFireBase()
    {
        //اشر لجزر قاعدة البيانات التابعه للمشروع يتخزن تحتها المهمات
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //ليسينر لمراقبة اي تغيير يحدث تحت الجزر المحدد
        //اي تغيير بقيمة صفه او حذف او اضافة كائن يتم اعلام اليسينير
        //عندها يتم تنزيل كل المعطيات الموجوده تحت الجزر
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //عنوان جذر قاعدة البيانات
        FirebaseDatabase.getInstance().getReference().
                child("ClothesItem").
                child("Tshirt").
                child(owner).addValueEventListener(new ValueEventListener() {

                    /**
                     * دالة معالجة الحدث عند تغيير اي قيمه
                     *
                     * @param snapshot يحوي نسخه عن كل المعطيات تحت العنوان المراقب
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TslideModels.clear();
                        //BslideModels.clear();
//                bantsAdapter.clear();

                        //يمحا كل اشي بداخله
                        for (DataSnapshot d : snapshot.getChildren())//d يمر على جميع قيم مبنى المعطيات
                        {
                            Tshirt m = d.getValue(Tshirt.class);//استخراج الكاىن المحفوظ
                            downloadImageToLocalFile(m.getImage());
                            Bants b =d.getValue(Bants.class);
                        }
                        TshirtSlider.setImageList(TslideModels, ScaleTypes.FIT);
                        //BantsSlider.setImageList(BslideModels, ScaleTypes.FIT);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }

                });
    }
    /**
     * دالة تقوم بتحميل الصوره الى المكان المخصص لها
     */
    private void downloadImageToLocalFile(String fileURL)
    {
        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileURL);
        final File localFile;
        try {
            localFile = File.createTempFile("images", "jpg");

            httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //تم إنشاء ملف مؤقت محلي
                    Toast.makeText(getApplicationContext(), "downloaded Image To Local File", Toast.LENGTH_SHORT).show();
                   // toView.setImageURI(Uri.fromFile(localFile));
                    TslideModels.add(new SlideModel(Uri.fromFile(localFile).getPath(),ScaleTypes.FIT));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // تعامل مع أي أخطاء
                    Toast.makeText(getApplicationContext(), "onFailure downloaded Image To Local File "+exception.getMessage(), Toast.LENGTH_SHORT).show();
                    exception.printStackTrace();
                }
            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}