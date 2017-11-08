package mb.com.googlesignin.FlieManager;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mb.com.googlesignin.R;

/**
 * Created by Anshul on 08-11-17.
 */

public class FileManagerActivity extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filemanagerlayout,container,false);
    }
}
