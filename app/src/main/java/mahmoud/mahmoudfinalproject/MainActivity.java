package mahmoud.mahmoudfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mahmoud.mahmoudfinalproject.Data.Bants;
import mahmoud.mahmoudfinalproject.Data.TshirtAdapter;
import mahmoud.mahmoudfinalproject.Data.BantsAdapter;
import mahmoud.mahmoudfinalproject.Data.Tshirt;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity
{
    SearchView SItem;//للبحث عن احد المهام
    ImageButton IAItem;//لاضافة مهمه جديده الى القائمه
    ListView ListItem;//قائمة عرض المهم
    //3.1 تجهيز الوسيط
    TshirtAdapter tshirtAdapter;
    BantsAdapter bantsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//نوع الشاشه افقي او عامودي
        //3.2 بناء الوسيط
        tshirtAdapter= new TshirtAdapter(getApplicationContext());
        bantsAdapter= new BantsAdapter(getApplicationContext());


        SItem = findViewById(R.id.SItem);
        IAItem = findViewById(R.id.IAItem);
        //تجهيز مؤشر لقائمة العرض
        ListItem = findViewById(R.id.ListItem);

        ListItem.setAdapter(tshirtAdapter);
        ListItem.setAdapter(bantsAdapter);
        //تشغيل مراقب لاي تغيير على قاعدة البيانات
        //ويقوم بتنظيف المعطيات الموجوده وتنزيل المعلومات الجديده

        ReadClothesItemtFromFireBase();

        IAItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this,AddClothes.class);
                startActivity(i);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
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
            dialog.show();//عرض الدايلوج
        }

        if (item.getItemId()==R.id.ITMhist)
        {
            Intent d=new Intent(MainActivity.this,History.class);
        }
        return true;
    }
    private void ReadClothesItemtFromFireBase()
    {
        //اشر لجزر قاعدة البيانات التابعه للمشروع يتخزن تحتها المهمات
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //ليسينر لمراقبة اي تغيير يحدث تحت الجزر المحدد
        //اي تغيير بقيمة صفه او حذف او اضافة كائن يتم اعلام اليسينير
        //عندها يتم تنزيل كل المعطيات الموجوده تحت الجزر
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().
                child("ClothesItem").
                child(owner).addValueEventListener(new ValueEventListener() {

            /**
             * دالة معالجة الحدث عند تغيير اي قيمه
             *
             * @param snapshot يحوي نسخه عن كل المعطيات تحت العنوان المراقب
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tshirtAdapter.clear();
                bantsAdapter.clear();

                //يمحا كل اشي بداخله
                for (DataSnapshot d : snapshot.getChildren())//d يمر على جميع قيم مبنى المعطيات
                {
                    Tshirt m = d.getValue(Tshirt.class);//استخراج الكاىن المحفوظ
                    Bants b =d.getValue(Bants.class);
                    tshirtAdapter.add(m);//اضافة الكائن للوسيط
                    bantsAdapter.add(b);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

}
