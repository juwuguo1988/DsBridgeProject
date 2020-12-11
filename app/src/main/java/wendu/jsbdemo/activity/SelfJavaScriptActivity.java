package wendu.jsbdemo.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;

import wendu.dsbridge.DWebView;
import wendu.dsbridge.OnReturnValue;
import wendu.jsbdemo.R;
import wendu.jsbdemo.api.JsApi;

public class SelfJavaScriptActivity extends AppCompatActivity {
    private static final String TAG = "SelfJavaScriptActivity";
    TextView addValue;
    DWebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_java_script);
        findViewById();
        initData();
        setListener();
    }

    private void findViewById() {
        addValue = findViewById(R.id.addValue);
        webView = findViewById(R.id.web_view);
        webView.addJavascriptObject(new JsApi(), null);
        //webView.loadUrl("file:///android_asset/js-native-transform.html");
    }

    private void initData() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");  //文本编码
        webSettings.setDomStorageEnabled(true);//设置DOM存储已启用（注释后文本显示变成js代码）
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        try {//本地HTML里面有跨域的请求 原生webview需要设置之后才能实现跨域请求
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = webView.getSettings().getClass();
                Method method = clazz.getMethod(
                        "setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(webView.getSettings(), true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sampleDir =
                Environment.getExternalStorageDirectory().toString() + "/" + "XZLFile" + "/web-mobile/";
        String url = "file://" + sampleDir + "index.html";
        webView.loadUrl(url);
    }


    private void setListener() {
        addValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.callHandler("addValue", new Object[]{3, 4}, new OnReturnValue<Integer>() {
                    @Override
                    public void onValue(Integer retValue) {
                        Toast.makeText(SelfJavaScriptActivity.this, "答案等于" + retValue, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
