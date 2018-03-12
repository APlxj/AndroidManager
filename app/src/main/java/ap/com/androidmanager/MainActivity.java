package ap.com.androidmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toActivity(View view) {
        Class<?> aClass = null;
        switch (view.getId()) {
            case R.id.phone:
                aClass = PhoneActivity.class;
                break;
            case R.id.sms:
                aClass = SmsActivity.class;
                break;
            case R.id.audio:
                aClass = AudioActivity.class;
                break;
        }
        startActivity(aClass);
    }
}
