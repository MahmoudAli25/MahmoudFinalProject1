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

import mahmoud.mahmoudfinalproject.OnClickInterfaceBants;
import mahmoud.mahmoudfinalproject.R;

public class BantsAdapter extends RecyclerView.Adapter<BantsAdapter.MyViewHolder>
{

    private List<Bants> BantsList;
    ImageView imageView;

    private OnClickInterfaceBants onClickInterfaceBants;

    //  من خلاله يمكن الوصول الى العناصر وتحديثها
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        MyViewHolder(View view) {
            super(view);
            imageView =view.findViewById(R.id.IVtshirt);
        }
    }
    public BantsAdapter(List<Bants>BantsList)
    {
        this.BantsList = BantsList;
    }

    @NonNull
    @Override
    public BantsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.clothes_item, parent, false);
                return new BantsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BantsAdapter.MyViewHolder holder, int position)
    {
        Bants bb = BantsList.get(position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterfaceBants.setClickBants(holder.getAdapterPosition());
            }
        });

        downloadImageToLocalFile(bb.getImage(),imageView);
    }

    public void setOnClickInterfaceBants(OnClickInterfaceBants onClickInterfaceBants) {
        this.onClickInterfaceBants = onClickInterfaceBants;
    }



    @Override
    public int getItemCount()
    {
        return BantsList.size();
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

    public List<Bants> getBantsList() {
        return BantsList;
    }
}


