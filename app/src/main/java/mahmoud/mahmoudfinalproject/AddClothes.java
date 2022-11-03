package mahmoud.mahmoudfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class AddClothes extends AppCompatActivity
{
    private ImageButton ImTShirt;
    private ImageButton ImJeans;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);
    }
}