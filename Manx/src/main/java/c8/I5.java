package c8;

/**
 * Date：2025/7/3
 * Describe:
 * c8.I5
 */
public class I5 {

    static {
        try {
            System.loadLibrary("Nose");
        } catch (Exception e) {

        }
    }

    //注意:用你们自己提供的对应功能的开关参数->比如:num包含"Ear"隐藏图标,num包含"Tail"恢复隐藏.num包含"Fur"外弹(外弹在主进程主线程调用).
    public static native long q9(int index, String num);

}
