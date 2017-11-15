package mb.com.googlesignin.UserRelatedClasses;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import mb.com.googlesignin.R;

/**
 * Created by Anshul on 08-11-17.
 */

public class ImageFragment extends Fragment
{
     public ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

         return inflater.inflate(R.layout.imagefragmentxml, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = (ImageView) getActivity().findViewById(R.id.seeImage);
    }
    public void showImage(String s) {

        Glide.with(this)
                .load(s)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }


}
