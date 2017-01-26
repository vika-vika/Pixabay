package net.vnnz.app.pixabay.utils;


import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class WindowUtils {

    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
