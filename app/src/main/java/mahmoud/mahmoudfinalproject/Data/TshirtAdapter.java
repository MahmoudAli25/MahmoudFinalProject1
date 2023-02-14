package mahmoud.mahmoudfinalproject.Data;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import mahmoud.mahmoudfinalproject.R;

//                                   وسيط من نوع ملابس فقط
public class TshirtAdapter extends ArrayAdapter<Tshirt>
{
    public TshirtAdapter(@NonNull Context context)
    {
        super(context, R.layout.clothes_item);

    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //بناء واجهه لعرض الملابس
        View vitem= LayoutInflater.from(getContext()).inflate(R.layout.clothes_item,parent,false);

        TextView EtDate=vitem.findViewById(R.id.EtDate);
        TextView TvEvent=vitem.findViewById(R.id.TvEvent);
        CheckBox CbWore=vitem.findViewById(R.id.CbWore);
        RatingBar RbCl=vitem.findViewById(R.id.RbCl);
        ImageView imageView =vitem.findViewById(R.id.IVtshirt);



        //getting data source
        final Tshirt tshirt =getItem(position);
        downloadImageToLocalFile(tshirt.getImage(),imageView);   //connect item view to data source


        //استخراج القيم من الحقول
        EtDate.setText(tshirt.getDate());
        TvEvent.setText(tshirt.getEvent());
        RbCl.setRating(tshirt.getImportant());
        CbWore.setChecked(false);

        return vitem;
    }

    /**
     * دالة تقوم بتحميل الصوره الى المكان المخصص لها
     */
    private void downloadImageToLocalFile(String fileURL, final ImageView toView)
    {
        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileURL);
        final File localFile;
        try {
            localFile = File.createTempFile("images", "jpg");

            httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //تم إنشاء ملف مؤقت محلي
                    Toast.makeText(getContext(), "downloaded Image To Local File", Toast.LENGTH_SHORT).show();
                    toView.setImageURI(Uri.fromFile(localFile));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // تعامل مع أي أخطاء
                    Toast.makeText(getContext(), "onFailure downloaded Image To Local File "+exception.getMessage(), Toast.LENGTH_SHORT).show();
                    exception.printStackTrace();
                }
            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
