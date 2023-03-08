package mahmoud.mahmoudfinalproject.Data;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import mahmoud.mahmoudfinalproject.R;

public class TshirtsAdapter extends RecyclerView.Adapter<TshirtsAdapter.MyViewHolder> {

    private List<Tshirt> TshirtList;
    ImageView imageView;
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        MyViewHolder(View view) {
            super(view);
            imageView =view.findViewById(R.id.IVtshirt);
        }
    }
    public TshirtsAdapter(List<Tshirt> TshirtList) {
        this.TshirtList = TshirtList;
    }
    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clothes_item, parent, false);
                    return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Tshirt tt = TshirtList.get(position);
        downloadImageToLocalFile(tt.getImage(),imageView);
    }

    @Override
    public int getItemCount() {
        return TshirtList.size();
    }
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
              //      Toast.makeText(get, "downloaded Image To Local File", Toast.LENGTH_SHORT).show();
                    toView.setImageURI(Uri.fromFile(localFile));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // تعامل مع أي أخطاء
                   // Toast.makeText(getContext(), "onFailure downloaded Image To Local File "+exception.getMessage(), Toast.LENGTH_SHORT).show();
                    exception.printStackTrace();
                }
            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}



