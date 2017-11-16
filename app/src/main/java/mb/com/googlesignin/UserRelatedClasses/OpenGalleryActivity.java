package mb.com.googlesignin.UserRelatedClasses;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

import mb.com.googlesignin.R;
import mb.com.googlesignin.UserRelatedClasses.ImageFragment;

import static android.R.attr.data;
import static mb.com.googlesignin.UserRelatedClasses.CustomDialogFragment.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
import static mb.com.googlesignin.UserRelatedClasses.CustomDialogFragment.REQUEST_CAMERA;

/**
 * Created by Anshul on 10-11-17.
 */

public class OpenGalleryActivity extends AppCompatActivity implements  View.OnClickListener {

    android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
    public static final int PICK_MULTIPLE_IMAGE = 4;
    CustomSlideView customSlideView;
    ViewPager viewpager;
    ImageFragment imageFragment = new ImageFragment();
    Button menuButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opengallerylayout);
        menuButton = (Button) findViewById(R.id.ChooseOption);
        viewpager = (ViewPager) findViewById(R.id.viewPager);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, imageFragment).commit();
        transaction.show(imageFragment);
        menuButton.setOnClickListener(this);
    }


    public void openGallery() {

        Intent imagePicker = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//Intent.ACTION_OPEN_DOCUMENT
        imagePicker.addCategory(Intent.CATEGORY_OPENABLE);
        imagePicker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imagePicker.setAction(Intent.ACTION_GET_CONTENT);
        imagePicker.setType("image/*");
        startActivityForResult(Intent.createChooser(imagePicker, "Select Picture"), PICK_MULTIPLE_IMAGE);

    }

    ArrayList<String> imagearray = new ArrayList<>();
    Uri uri;
    ClipData clipData;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_MULTIPLE_IMAGE) {


            int i;
            clipData = data.getClipData();
            if(clipData==null)
            {
                uri = data.getData();
                imagearray.add(0, uri.toString());
                customSlideView = new CustomSlideView(OpenGalleryActivity.this, imagearray);
                viewpager.setAdapter(customSlideView);
            }
            if (clipData != null) {
                for (i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    imagearray.add(i, uri.toString());
                    customSlideView = new CustomSlideView(OpenGalleryActivity.this, imagearray);
                    viewpager.setAdapter(customSlideView);
                }

            }
        }
    }

    public boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

   String userChoosenTask = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.ChooseOption:
                final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(OpenGalleryActivity.this);
        builder.setTitle("Add Photo!");
                        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= checkPermission(OpenGalleryActivity.this);
                if (items[item].equals("Take Photo"))
                {
                    userChoosenTask="Take Photo";
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if(result)
                    {
                        startActivityForResult(camera, REQUEST_CAMERA);

                    }
                }
                else if (items[item].equals("Choose from Library"))
                {
                    userChoosenTask="Choose from Library";
                    if(result)
                    {
                        openGallery();
                    }
                }
                else if (items[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        }

        }
    }
