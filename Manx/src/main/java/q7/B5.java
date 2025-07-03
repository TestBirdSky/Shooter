package q7;

import android.content.Context;

import com.cat.manx.start.CatAppCenter;

import java.lang.reflect.InvocationTargetException;

/**
 * Date：2025/7/2
 * Describe:
 * q7.B5
 */
public class B5 {

    // App 入口
    public static void a2(Context context) {
        CatAppCenter catAppCenter = new CatAppCenter();
        String s = catAppCenter.initCenter(context);
        if (!s.isEmpty()) {
            try {
                catAppCenter.getClassNameCat().getMethod(s, Context.class).invoke(null, context);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
