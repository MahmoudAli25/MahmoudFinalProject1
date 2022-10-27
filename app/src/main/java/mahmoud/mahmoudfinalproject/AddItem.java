package mahmoud.mahmoudfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class AddItem extends AppCompatActivity
{
    private TextInputEditText EdTitle;//عنوان
    private TextView TDate;
    private EditText EdDate;//التاريخ
    private TextView TType;
    private TextInputEditText EdType;//النوع
    private Button BnAdd;
    private Button BnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        EdTitle=findViewById(R.id.EdTile);
        TDate=findViewById(R.id.TDate);
        EdDate=findViewById(R.id.EdDate);
        TType=findViewById(R.id.TType);
        EdType=findViewById(R.id.EdType);
        BnAdd=findViewById(R.id.BnAdd);
        BnCancel=findViewById(R.id.BnCancel);

        BnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                checkAndSave();

            }
        });
        BnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i =new Intent(AddItem.this,MainActivity.class);
                startActivity(i);

            }
        });
    }
    private void CheckAndSave()
    {
        String title=EdTitle.getText().toString();
        String date=EdDate.getText().toString();
        String type=EdType.getText().toString();



    }
}