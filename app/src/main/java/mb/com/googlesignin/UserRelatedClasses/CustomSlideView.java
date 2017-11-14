package mb.com.googlesignin.UserRelatedClasses;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import mb.com.googlesignin.R;

/**
 * Created by Anshul on 14-11-17.
 */

public class CustomSlideView extends PagerAdapter {

    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;

    public CustomSlideView(Context context)

    {
        this.context=context;
    }



    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View image_item=inflater.inflate(R.layout.imagefragmentxml,container,false);
        return image_item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);

    }
}
