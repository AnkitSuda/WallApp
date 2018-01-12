package example.androidgrid.simplewallpapersapp.util;

import android.app.Activity;
import android.content.Intent;

import example.androidgrid.simplewallpapersapp.ui.AddCategoryActivity;

/**
 * Created by ankit on 30/12/17.
 */

public class NavigationUtil {

    public static void goToAddCat(Activity activity) {
        Intent intent = new Intent(activity, AddCategoryActivity.class);
        activity.startActivity(intent);
    }
}
