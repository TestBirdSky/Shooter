package a3;

import android.content.Context;

import com.cat.manx.GoogleHelper;
import com.cat.manx.treat.TreatHelper;

/**
 * Date：2025/7/2
 * Describe:
 * a3.W
 */
public class W {

    public static void b1() {
        GoogleHelper gh = new GoogleHelper();
        gh.start();
    }

    public static void c1(Context context) {
        TreatHelper th = new TreatHelper();
        th.first(context);
    }

}
