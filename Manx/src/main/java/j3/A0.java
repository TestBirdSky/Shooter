package j3;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * Date：2025/7/3
 * Describe:
 * j3.A0
 */
public class A0 extends Handler {
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        x2.P1.b0(msg.what);
    }
}
