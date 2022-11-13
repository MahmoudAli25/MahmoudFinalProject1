package mahmoud.mahmoudfinalproject.Data;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import mahmoud.mahmoudfinalproject.R;

//                                   وسيط من نوع ملابس فقط
public class ClothesAdapter extends ArrayAdapter<ClothesItem>
{
    public ClothesAdapter(@NonNull Context context)
    {
        super(context, R.layout.clothes_item);

    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //بناء واجهه لعرض الملابس
        View vitem= LayoutInflater.from(getContext()).inflate(R.layout.clothes_item,parent,false);

        EditText EdDate=vitem.findViewById(R.id.EdDate);
        TextView TvEvent=vitem.findViewById(R.id.TvEvent);
        CheckBox CbWore=vitem.findViewById(R.id.CbWore);
        RatingBar RbCl=vitem.findViewById(R.id.RbCl);

        //getting data source
        final ClothesItem clothesItem=getItem(position);

        //استخراج القيم من الحقول
        EdDate.setText(clothesItem.getDate());
        TvEvent.setText(clothesItem.getEvent());
        RbCl.setRating(clothesItem.getImportant());
        CbWore.setChecked(false);

        return vitem;
    }



}
