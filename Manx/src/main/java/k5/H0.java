package k5;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.cat.manx.treat.TreatNManager;

/**
 * Date：2025/7/3
 * Describe:
 * k5.H0
 */
public class H0 extends WebChromeClient {
    private final TreatNManager treatNManager = new TreatNManager();

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        treatNManager.pic(newProgress);
    }
}
