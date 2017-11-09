package mb.com.googlesignin.filemanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import mb.com.googlesignin.MainActivity.GooglePlusSignIn;
import mb.com.googlesignin.R;

import static com.facebook.FacebookSdk.getApplicationContext;
import static mb.com.googlesignin.R.id.seeImage;

/**
 * Created by Anshul on 08-11-17.
 */

public class FileManagerActivity extends Fragment
{
    ImageView imageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView.findViewById(R.id.seeImage);
        FileManagerActivity filemanager = new FileManagerActivity();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.mainXMlFile, filemanager, "GoToFile");
        transaction.commit();

    }
    public  void onAttach(GooglePlusSignIn obj)
    {
        super.onAttach(obj);
    }
    public void onActivity(int requestCode, int resultCode, Intent data) {


        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1) {
            Intent FileManagerClass = new Intent();
            startActivity(FileManagerClass);
            //Intent imagePicker = new Intent(Intent.ACTION_OPEN_DOCUMENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //imagePicker.addCategory(Intent.CATEGORY_OPENABLE);
            //imagePicker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            //imagePicker.setAction(Intent.ACTION_GET_CONTENT);
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator);
            String imagepath = file.getPath();
            //Uri imageUri= Uri.parse(imagepath);
            // imagePicker.setDataAndType(imageUri,"image/*");
            try {
                Uri image = data.getData();
                //InputStream inputStream= getContentResolver().openInputStream(image);
                Bitmap finalimage = BitmapFactory.decodeFile(imagepath);
                imageView.setImageBitmap(finalimage);
            } catch (Exception ex) {
                ex.printStackTrace();
                //Toast.makeText(this,"Picture not available",Toast.LENGTH_SHORT).show();

            }
        }


    }






    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filemanagerlayout,container,false);
    }



}
