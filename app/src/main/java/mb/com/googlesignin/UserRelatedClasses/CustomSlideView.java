package mb.com.googlesignin.UserRelatedClasses;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import mb.com.googlesignin.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Anshul on 14-11-17.
 */

public class CustomSlideView extends PagerAdapter {

    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    int pos;
    ArrayList<String> images=new ArrayList<String>();

    public CustomSlideView(Context context,ArrayList<String> images)

    {
        this.images=images;
        this.context=context;
    }



    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View image_item=inflater.inflate(R.layout.imagefragmentxml,container,false);
        Glide.with(getApplicationContext()).load(images.get(position).toString())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView)image_item.findViewById(R.id.seeImage));
        container.addView(image_item);
        return image_item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);

    }
}
