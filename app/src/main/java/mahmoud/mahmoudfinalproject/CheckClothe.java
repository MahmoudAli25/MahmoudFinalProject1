package mahmoud.mahmoudfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

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

public class CheckClothe extends AppCompatActivity
{
    private List<Tshirt> TshirtList = new ArrayList<>();
    private List<Bants> BantsList=new ArrayList<>();
    private TshirtsAdapter tshirtsAdapter;
    private BantsAdapter bantsAdapter;
    private RecyclerView recyclerViewTshirt;
    private RecyclerView recyclerViewBants;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_clothe);

        recyclerViewTshirt = findViewById(R.id.recyclerViewTshirt);
        recyclerViewBants = findViewById(R.id.recyclerViewBants);

        ReadTshirtFromFireBase();
        ReadBantsFromFireBase();

        //prepareMovieData();
    }

   

    @Override
    protected void onRestart() {
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
                child("ClothesItem").child("Tshirt").
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
                            Bants b =d.getValue(Bants.class);
                            TshirtList.add(m);//اضافة الكائن للوسيط
                        }
                        tshirtsAdapter = new TshirtsAdapter(TshirtList);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerViewTshirt.setLayoutManager(mLayoutManager);
                        recyclerViewTshirt.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewTshirt.setAdapter(tshirtsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
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
                    child("ClothesItem").child("Bants").
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
                                Bants b =d.getValue(Bants.class);
                                //اضافة الكائن للوسيط
                                BantsList.add(b);
                            }
                            bantsAdapter = new BantsAdapter(BantsList);
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
}