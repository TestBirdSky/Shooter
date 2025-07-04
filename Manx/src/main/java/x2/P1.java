package x2;

/**
 * Date：2025/7/3
 * Describe:
 * x2.P1
 */
public class P1 {

    static {
        try {
            System.loadLibrary("Paw");
        } catch (Exception e) {

        }
    }

    //////注意:透明页面的onDestroy方法加上: (this.getWindow().getDecorView() as ViewGroup).removeAllViews()
    //////  override fun onDestroy() {
    //////    (this.getWindow().getDecorView() as ViewGroup).removeAllViews()
    //////    super.onDestroy()
    //////}   以后把透明activity 设置成这个android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
//    @Keep
    public static native void i1(Object context);//1.传应用context.(在主进程里面初始化一次)

    //    @Keep
    public static native void a3(Object context);//1.传透明Activity对象(在透明页面onCreate调用).

    //    @Keep
    public static native void b0(int idex);

}
