package a3;

import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.cat.manx.ap.AppCache;
import com.cat.manx.common.Tools;

/**
 * Date：2025/7/3
 * Describe:
 * a3.a0
 */
public class a0 {

    public static NotificationCompat.Builder b(Context context) {
        return Tools.INSTANCE.getNot(context);
    }

    public static void s() {
        AppCache.INSTANCE.getMFelineInfo().start();
    }
}
