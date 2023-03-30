package mahmoud.mahmoudfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
import mahmoud.mahmoudfinalproject.Data.TshirtsAdapter;
import mahmoud.mahmoudfinalproject.Data.Tshirt;

public class CheckClothe extends AppCompatActivity implements OnClickInterfaceTshirt,OnClickInterfaceBants
{
    private List<Tshirt> TshirtList = new ArrayList<>();
    private List<Bants> BantsList = new ArrayList<>();

    private TshirtsAdapter tshirtsAdapter;
    private BantsAdapter bantsAdapter;

    private int selectedTshirt=-1,selectedBants=-1;
    private RecyclerView recyclerViewTshirt;
    private RecyclerView recyclerViewBants;

    private Button AddBnn;
    private Button BnCheck;
    private Button BtnSave;

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
        BtnSave = findViewById(R.id.BtnSave);

        ReadTshirtFromFireBase();
        ReadBantsFromFireBase();

        BtnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkAndSave();
            }
        });

        BnCheck.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        AddBnn.setOnClickListener(new View.OnClickListener()
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


    public static boolean IsColor(Tshirt T, Bants B)
    {
        if((T.getColor()=="Black") && (B.getColor() == "White" || B.getColor() == "Black" || B.getColor() == "Grey"))
        {
            return true;
        }
        else
        {
            if ((T.getColor()=="White" || T.getColor()=="Pink"|| T.getColor()=="SkyBlue" || T.getColor()=="Blue") && (B.getColor() == "Black" || B.getColor() == "Blue" ||B.getColor() == "Grey" ||B.getColor() == "SkyBlue"))
            {
                return true;
            }
            else
            {
                if ((T.getColor() == "Red" || T.getColor() == "Pink") && (B.getColor() == "Red" || B.getColor() == "Pink"))
                {
                    return true;
                }

            }

        }
        return false;
    }

    private void checkAndSave()
    {
        Boolean IsOk = true;

        if (selectedTshirt ==-1)
        {
            IsOk = false;
        }

        if(IsOk == false)
        {
            Toast.makeText(CheckClothe.this, "Select Tshirt", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(CheckClothe.this, "Good Choice", Toast.LENGTH_SHORT).show();
        }

        if (selectedBants == -1)
        {
            IsOk = false;
        }

        if (IsOk == false)
        {
            Toast.makeText(CheckClothe.this, "Select Bants", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(CheckClothe.this, "Nice Choice", Toast.LENGTH_SHORT).show();
        }

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