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
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mahmoud.mahmoudfinalproject.Data.Bants;
import mahmoud.mahmoudfinalproject.Data.BantsAdapter;
import mahmoud.mahmoudfinalproject.Data.CloTheSet;
import mahmoud.mahmoudfinalproject.Data.TshirtsAdapter;
import mahmoud.mahmoudfinalproject.Data.Tshirt;

public class CheckClothe extends AppCompatActivity implements OnClickInterfaceTshirt,OnClickInterfaceBants
{
    private List<Tshirt> TshirtList = new ArrayList<>();
    private List<Bants> BantsList = new ArrayList<>();

    private TshirtsAdapter tshirtsAdapter;
    private BantsAdapter bantsAdapter;

    private int selectedTshirt=-1,selectedBants=-1;
    private RecyclerView recyclerViewTshirt;//قائمة البلائز
    private RecyclerView recyclerViewBants;//قائمة البناطلين

    private Button AddBnn;//لاضافه
    private Button BnCheck;//فحص الاختيار
    private Button BtnSave;//حفظ الاختيار

    private String D;

    private Tshirt T;
    private Bants B;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_clothe);

        recyclerViewTshirt = findViewById(R.id.recyclerViewTshirt);
        recyclerViewBants = findViewById(R.id.recyclerViewBants);


        AddBnn = findViewById(R.id.AddBnn);
        BnCheck = findViewById(R.id.BnCheck);

        ReadTshirtFromFireBase();
        ReadBantsFromFireBase();

        BnCheck.setOnClickListener(new View.OnClickListener()//زر الفحص
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(CheckClothe.this);
            @Override
            public void onClick(View view) {

                if (isTake())
                {
                    if (IsColor())
                    {
                        // تجهيز البناء للدايلوج
                        builder.setTitle("Check Clothes");
                        builder.setMessage("Very Good Choice");
                        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //اخفاء الديالوج
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                    else
                    {
                        // تجهيز البناء للدايلوج
                        builder.setTitle("Check Clothes");
                        builder.setMessage("Not a Good Choice");
                        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //اخفاء الديالوج
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }

            }
        });

        AddBnn.setOnClickListener(new View.OnClickListener()//زر الاظافه
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(CheckClothe.this, MainActivity.class);
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
            Intent i=new Intent(CheckClothe.this,Settings.class);
            startActivity(i);
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(CheckClothe.this);
        if (item.getItemId()==R.id.ITMout)
        {
            //تسجيل الخروج
            //1
            // تجهيز البناء للدايلوج
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
            Intent d=new Intent(CheckClothe.this,History.class);
        }
        return true;
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        ReadTshirtFromFireBase();
        ReadBantsFromFireBase();
    }


    private void ReadTshirtFromFireBase()
    {
        //اشر لجزر قاعدة البيانات التابعه للمشروع يتخزن تحتها المهمات
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //ليسينر لمراقبة اي تغيير يحدث تحت الجزر المحدد
        //اي تغيير بقيمة صفه او حذف او اضافة كائن يتم اعلام اليسينير
        //عندها يتم تنزيل كل المعطيات الموجوده تحت الجزر
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
                        TshirtList.clear();

                        //يمحا كل اشي بداخله
                        for (DataSnapshot d : snapshot.getChildren())//d يمر على جميع قيم مبنى المعطيات
                        {
                            Tshirt m = d.getValue(Tshirt.class);//استخراج الكاىن المحفوظ
                            TshirtList.add(m);//اضافة الكائن للوسيط
                        }
                        tshirtsAdapter = new TshirtsAdapter(TshirtList);
                        tshirtsAdapter.setOnClickInterfaceTshirt(CheckClothe.this);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerViewTshirt.setLayoutManager(mLayoutManager);
                        recyclerViewTshirt.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewTshirt.setAdapter(tshirtsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        error.getMessage();
                    }

                });
    }


    private void ReadBantsFromFireBase()
    {
        //اشر لجزر قاعدة البيانات التابعه للمشروع يتخزن تحتها المهمات
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //ليسينر لمراقبة اي تغيير يحدث تحت الجزر المحدد
        //اي تغيير بقيمة صفه او حذف او اضافة كائن يتم اعلام اليسينير
        //عندها يتم تنزيل كل المعطيات الموجوده تحت الجزر
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().
                child("ClothesItem").
                child("Bants").
                child(owner).addValueEventListener(new ValueEventListener() {

                    /**
                     * دالة معالجة الحدث عند تغيير اي قيمه
                     *
                     * @param snapshot يحوي نسخه عن كل المعطيات تحت العنوان المراقب
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        BantsList.clear();

                        //يمحا كل اشي بداخله
                        for (DataSnapshot d : snapshot.getChildren())//d يمر على جميع قيم مبنى المعطيات
                        {
                            //استخراج الكاىن المحفوظ
                            Bants b = d.getValue(Bants.class);
                            //اضافة الكائن للوسيط
                            BantsList.add(b);
                        }
                        bantsAdapter = new BantsAdapter(BantsList);
                        bantsAdapter.setOnClickInterfaceBants(CheckClothe.this);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerViewBants.setLayoutManager(mLayoutManager);
                        recyclerViewBants.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewBants.setAdapter(bantsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }

                });
    }


    public  boolean IsColor() {

            Tshirt T = TshirtList.get(selectedTshirt);
            Bants B =  BantsList.get(selectedBants);

            if ((T.getColor() == "Black") && (B.getColor() == "White" || B.getColor() == "Black" || B.getColor() == "Grey")) {
                return true;
            } else {
                if ((T.getColor() == "White" || T.getColor() == "Pink" || T.getColor() == "SkyBlue" || T.getColor() == "Blue") && (B.getColor() == "Black" || B.getColor() == "Blue" || B.getColor() == "Grey" || B.getColor() == "SkyBlue")) {
                    return true;
                } else {
                    if ((T.getColor() == "Red" || T.getColor() == "Pink") && (B.getColor() == "Red" || B.getColor() == "Pink")) {
                        return true;
                    }

                }

            }
        return false;
    }

    public Boolean isTake()
    {
        if (selectedTshirt != -1 && selectedBants != -1)
        {
            return true;
        }
        else
        {
            //No CLothes Selected
            Toast.makeText(this,"Select Tshirt And Bants..!)",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void checkAndSave()
    {
        Boolean IsOk = true;

        //Tshirt
        if (selectedTshirt ==-1)
        {
            Toast.makeText(CheckClothe.this, "Select Tshirt", Toast.LENGTH_SHORT).show();
            IsOk = false;
        }

        //Bants
        if (selectedBants == -1)
        {
            Toast.makeText(CheckClothe.this, "Select Bants", Toast.LENGTH_SHORT).show();
            IsOk = false;
        }

        if (IsOk )
        {
            Tshirt tshirt = tshirtsAdapter.getTshirtList().get(selectedTshirt);
            Bants bants = bantsAdapter.getBantsList().get(selectedBants);
            CloTheSet c = new CloTheSet();

            c.setT(tshirt);
            c.setB(bants);
        }


/**        //استخراج الرقم المميز للمستخدم UID
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
                setValue(t).addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            finish();
                            Toast.makeText(CheckClothe.this, "Saved Succesfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(CheckClothe.this, "Saved Failled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
 */
    }



    @Override
    public void setClickTshirt(int abc)
    {
        selectedTshirt=abc;
    }

    @Override
    public void setClickBants(int abc)
    {
        selectedBants=abc;
    }
}