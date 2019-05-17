package com.example.hakim.e_comerceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLogTags;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {
    private String CategoryName,Description,Price,Pname, SaveCurrentDate,SaveCurrentTime;
    private ImageView InputProductImage;
    private Button AddNewProductButton;
    private EditText InputProductName,InputProductDescription,InputProductPrice;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private String ProductRandomKey,downloadImageuRL;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin_add_new_product);

        CategoryName=getIntent ().getExtras ().get ("category").toString ();
        ProductImageRef= FirebaseStorage.getInstance ().getReference ().child ("Product Image");
        ProductRef=FirebaseDatabase.getInstance ().getReference ().child ("Products");
        AddNewProductButton=findViewById (R.id.add_new_product);
        InputProductImage=findViewById (R.id.select_product_image);
        InputProductName=findViewById (R.id.product_name);
        InputProductDescription=findViewById (R.id.product_description);
        InputProductPrice=findViewById (R.id.product_price);
        loadingBar=new ProgressDialog (this);

        InputProductImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        AddNewProductButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });

    }

    private void OpenGallery() {
        Intent galleryIntent=new Intent ();
        galleryIntent.setAction (Intent.ACTION_GET_CONTENT);
        galleryIntent.setType ("image/*");
        startActivityForResult (galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData ();
            InputProductImage.setImageURI (ImageUri);
        }
    }
    private void ValidateProductData()
    {
        Description=InputProductDescription.getText ().toString ();
        Price=InputProductPrice.getText ().toString ();
        Pname=InputProductName.getText ().toString ();
        if (ImageUri==null)
        {
            Toast.makeText (getApplicationContext (),"Product Image is mandatory...",Toast.LENGTH_SHORT).show ();
        }
        else if (TextUtils.isEmpty (Description))
        {
            Toast.makeText (getApplicationContext (),"Product write product description...",Toast.LENGTH_SHORT).show ();
        }
        else if (TextUtils.isEmpty (Price))
        {
            Toast.makeText (getApplicationContext (),"Product write product price...",Toast.LENGTH_SHORT).show ();
        }
        else if (TextUtils.isEmpty (Pname))
        {
            Toast.makeText (getApplicationContext (),"Product write product name...",Toast.LENGTH_SHORT).show ();
        }
        else
        {
            StorageProductInformation();
        }
    }

    private void StorageProductInformation()
    {
        loadingBar.setTitle ("Adding New Product");
        loadingBar.setMessage ("Dear Admin,Please Wait,while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside (false);
        loadingBar.show ();
        Calendar calendar=Calendar.getInstance ();
        SimpleDateFormat currentDate=new SimpleDateFormat ("MMM dd,yyy");
        SaveCurrentDate=currentDate.format (calendar.getTime ());

        SimpleDateFormat currentTime=new SimpleDateFormat ("HH:mm:ss a");
        SaveCurrentTime=currentTime.format (calendar.getTime ());
        ProductRandomKey=SaveCurrentDate+SaveCurrentTime;
        final StorageReference filepath=ProductImageRef.child (ImageUri.getLastPathSegment ()+ProductRandomKey+".jpg");
        final UploadTask uploadTask=filepath.putFile (ImageUri);
        uploadTask.addOnFailureListener (new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e)
            {
              String message= e.toString ();
                Toast.makeText (getApplicationContext (),"Error:" + message,Toast.LENGTH_SHORT).show ();
                loadingBar.dismiss ();

            }
        }).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText (getApplicationContext (),"Product Image Uploaded Successfully...",Toast.LENGTH_SHORT).show ();
                Task<Uri> uriTask=uploadTask.continueWithTask (new Continuation<UploadTask.TaskSnapshot, Task<Uri>> () {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful ())
                        {
                            throw task.getException ();

                        }
                        downloadImageuRL=filepath.getDownloadUrl ().toString ();
                        return filepath.getDownloadUrl ();
                    }
                }).addOnCompleteListener (new OnCompleteListener<Uri> () {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                    if (task.isSuccessful ())
                    {
                        downloadImageuRL =task.getResult ().toString ();
                        Toast.makeText (getApplicationContext ()," got the Product image Url Successfully...",Toast.LENGTH_SHORT).show ();
                        SaveProductInfoToDatabase();
                    }
                    }
                });

            }
        });

    }

    private void SaveProductInfoToDatabase()
    {
        HashMap<String,Object> productMap= new HashMap<> ();
        productMap.put ("pid",ProductRandomKey);
        productMap.put ("date",SaveCurrentDate);
        productMap.put ("time",SaveCurrentTime);
        productMap.put ("description",Description);
        productMap.put ("image",downloadImageuRL);
        productMap.put ("category",CategoryName);
        productMap.put ("price",Price);
        productMap.put ("pname",Pname);

        ProductRef.child (ProductRandomKey).updateChildren (productMap)
                .addOnCompleteListener (new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                     if (task.isSuccessful ())
                     {
                         Intent intent=new Intent (AdminAddNewProductActivity.this,AdminCategoryActivity.class);
                         startActivity (intent);
                         loadingBar.dismiss ();
                         Toast.makeText (getApplicationContext (),"Product is added to Successfully...",Toast.LENGTH_SHORT).show ();
                     }
                     else
                     {
                         loadingBar.dismiss ();
                          String message=task.getException ().toString ();
                         Toast.makeText (getApplicationContext (),"Error:"+message,Toast.LENGTH_SHORT).show ();
                     }
                    }
                });


    }
}

