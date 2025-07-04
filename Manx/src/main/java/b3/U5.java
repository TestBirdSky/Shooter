package b3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cat.manx.cache.BHelper;

/**
 * Date：2025/7/3
 * Describe:
 * b3.U5
 */
public class U5 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        BHelper bHelper = new BHelper();
        Intent i = bHelper.getI(intent);
        if (i != null) {
            context.startActivity(i);
        }
    }
}
