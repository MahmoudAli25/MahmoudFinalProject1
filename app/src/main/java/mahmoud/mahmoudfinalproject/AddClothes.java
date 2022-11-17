package mahmoud.mahmoudfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AddClothes extends AppCompatActivity
{
    private ImageButton IbShirt;
    private ImageButton IbJeans;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        IbShirt=findViewById(R.id.IbShirt);
        IbJeans=findViewById(R.id.IbJeans);

        IbShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(AddClothes.this,AddItem.class);
                startActivity(i);
            }
        });

        IbJeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent a = new Intent(AddClothes.this,AddItem.class);
                startActivity(a);
            }
        });



    }
}