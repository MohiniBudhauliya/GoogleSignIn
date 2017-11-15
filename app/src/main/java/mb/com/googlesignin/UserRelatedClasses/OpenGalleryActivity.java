package mb.com.googlesignin.UserRelatedClasses;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

import mb.com.googlesignin.R;
import mb.com.googlesignin.UserRelatedClasses.ImageFragment;

/**
 * Created by Anshul on 10-11-17.
 */

public class OpenGalleryActivity extends AppCompatActivity implements  View.OnClickListener{

    android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
    public static final int PICK_MULTIPLE_IMAGE=4;
    CustomSlideView customSlideView;
    ViewPager viewpager;
    ImageFragment imageFragment=new ImageFragment();
    Button menuButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuButton=(Button)findViewById(R.id.ChooseOption);
        setContentView(R.layout.opengallerylayout);
        viewpager=(ViewPager)findViewById(R.id.viewPager);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, imageFragment).commit();
        transaction.show(imageFragment);
    }


    public void openGallery() {

        Intent imagePicker = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//Intent.ACTION_OPEN_DOCUMENT
        imagePicker.addCategory(Intent.CATEGORY_OPENABLE);
        imagePicker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imagePicker.setAction(Intent.ACTION_GET_CONTENT);
        imagePicker.setType("image/*");
        startActivityForResult(Intent.createChooser(imagePicker, "Select Picture"), PICK_MULTIPLE_IMAGE);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_MULTIPLE_IMAGE) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +File.separator);
            ArrayList<String> imagearray = new ArrayList<>();
            int i;
                        ClipData clipData = data.getClipData();
                       if (clipData != null) {
                               for (i = 0; i < clipData.getItemCount(); i++) {
                                   ClipData.Item item = clipData.getItemAt(i);
                                   Uri uri = item.getUri();
                                   imagearray.add(i, uri.toString());

                               }
                           ImageFragment f1 = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                           f1.showImage(imagearray.get(1).toString());
                           customSlideView=new CustomSlideView(OpenGalleryActivity.this,imagearray);
                           viewpager.setAdapter(customSlideView);




                }




        }
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id)
        {
            case R.id.ChooseOption:
                Intent intent=new Intent(OpenGalleryActivity.this,CustomDialogFragment.class);
                startActivity(intent);


        }

    }
}


