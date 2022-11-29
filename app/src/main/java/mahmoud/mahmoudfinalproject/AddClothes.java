package mahmoud.mahmoudfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AddClothes extends AppCompatActivity
{
    private ImageButton IbShirt;
    private ImageButton IbJeans;
    private Button BCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        IbShirt=findViewById(R.id.IbShirt);
        IbJeans=findViewById(R.id.IbJeans);
        BCancel=findViewById(R.id.BCancel);


        IbShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(AddClothes.this, AddTshirt.class);
                startActivity(i);
            }
        });

        IbJeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent a = new Intent(AddClothes.this, AddBants.class);
                startActivity(a);
            }
        });

        BCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent b=new Intent(AddClothes.this,MainActivity.class);
                startActivity(b);

            }
        });



    }
}