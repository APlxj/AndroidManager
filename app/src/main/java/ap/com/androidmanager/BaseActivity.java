package ap.com.androidmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    public void startActivity(Class<?> aClass) {
        startActivity(new Intent(BaseActivity.this, aClass));
    }

    public void prompt(EditText editText, String s) {
        toast(s);
        editText.setFocusable(true);
        editText.requestFocus();
        editText.selectAll();
    }

    public void toast(String s) {
        Toast.makeText(BaseActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
