package mahmoud.mahmoudfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CheckClothes extends AppCompatActivity
{
    private ImageSlider TshirtSlider;
    private ImageSlider BantsSlider;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_clothes);

        TshirtSlider = findViewById(R.id.TshirtSlider);
        BantsSlider = findViewById(R.id.BantsSlider);

        //craet a list for images

        ArrayList<SlideModel> TslideModels =new ArrayList<>();

        TslideModels.add(new SlideModel("@drawable/logomahmod",ScaleTypes.FIT));
        TslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
        TslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
        TslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
        TslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));

        TshirtSlider.setImageList(TslideModels, ScaleTypes.FIT);

        ArrayList<SlideModel> BslideModels =new ArrayList<>();

        BslideModels.add(new SlideModel("@drawable/logomahmod",ScaleTypes.FIT));
        BslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
        BslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
        BslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));
        BslideModels.add(new SlideModel("@drawable/logomahmod", ScaleTypes.FIT));

        BantsSlider.setImageList(BslideModels, ScaleTypes.FIT);

    }

}