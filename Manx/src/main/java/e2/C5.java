package e2;

import android.app.Application;
import android.content.Context;
import android.webkit.WebView;

import com.cat.manx.start.CenterStart;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Date：2025/7/2
 * Describe:
 * e2.C5
 */
public class C5 {
    // 主进程
    public static void a0(Context context) {
        CenterStart cs = new CenterStart();
        cs.initCache(context);
        List<String> lis = cs.register(context);
        Class<?> c = cs.getClassCenter(lis.get(0));
        if (c != null) {
            try {
                c.getMethod(lis.get(1)).invoke(null);
                c.getMethod(lis.get(2), Context.class).invoke(null, context);
            } catch (Exception e) {

            }
        }
    }

    public static void b0(Context context) {
        String processName = Application.getProcessName();
        if (!processName.isEmpty()) {
            WebView.setDataDirectorySuffix(processName);
        }
    }


}
