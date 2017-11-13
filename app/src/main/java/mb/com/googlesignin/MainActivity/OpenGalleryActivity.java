package mb.com.googlesignin.MainActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.InputStream;

import mb.com.googlesignin.R;
import mb.com.googlesignin.filemanager.ImageFragment;

/**
 * Created by Anshul on 10-11-17.
 */

public class OpenGalleryActivity extends AppCompatActivity {
    ImageFragment imageFragment = new ImageFragment();
    android.support.v4.app.FragmentManager manager = getSupportFragmentManager();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opengallerylayout);
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.opengallerylayout, imageFragment);
        transaction.commit();
        openGallery();
    }

    public void openGallery() {

        Intent imagePicker = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//Intent.ACTION_OPEN_DOCUMENT
        imagePicker.addCategory(Intent.CATEGORY_OPENABLE);
        imagePicker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imagePicker.setAction(Intent.ACTION_GET_CONTENT);
        imagePicker.setType("image/*");
        startActivityForResult(Intent.createChooser(imagePicker, "Select Picture"), 4);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator);
            String imagepath = file.getPath();
            Uri imageUri = Uri.parse(imagepath);

            try {
                Uri image = data.getData();
                //InputStream inputStream = this.getContentResolver().openInputStream(imageUri);
                ImageFragment f1 =(ImageFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
                f1.showImage(imageUri.toString());


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
    }
}


