package wendu.jsbdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        setListener();
    }

    private void findViewById() {
        addValue = findViewById(R.id.addValue);
        webView = findViewById(R.id.web_view);
        webView.addJavascriptObject(new JsApi(), null);
        webView.loadUrl("file:///android_asset/js-native-transform.html");
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
