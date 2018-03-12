package ap.com.androidmanager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class SmsActivity extends BaseActivity {

    private EditText et_phone;
    private EditText et_sms;
    private String phoneNumber;
    private String message;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sms);
        init();
    }

    private void init() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_sms = (EditText) findViewById(R.id.et_sms);
    }

    public void to(View view) {
        if (!check()) return;
        try {
            //Uri.parse("smsto") 这里是转换为指定Uri,固定写法
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        } catch (Exception e) {

        }
    }

    public void _to(View view) {
        if (!check()) return;
        try {
            sendSMS(SmsActivity.this, phoneNumber, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //直接发送短信
    private void sendSMS(Context context, String phoneNumber, String message) throws Exception {
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent, 0);

        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        //创建接收返回的接收状态的Intent
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, deliverIntent, 0);

        //获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）,貌似长度限制为140个字符,就是
        //只能发送70个汉字,多了要拆分成多条短信发送
        //第四五个参数,如果没有需要监听发送状态与接收状态的话可以写null
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, sentPI, deliverPI);
        }
    }

    private boolean check() {
        phoneNumber = et_phone.getText().toString();
        message = et_sms.getText().toString();
        if ("".equals(phoneNumber) && phoneNumber.length() < 11) {
            Toast.makeText(SmsActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
            et_phone.selectAll();
            et_phone.setFocusable(true);
            et_phone.requestFocus();
            return false;
        }
        if ("".equals(message)) {
            Toast.makeText(SmsActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
            et_sms.selectAll();
            et_sms.setFocusable(true);
            et_sms.requestFocus();
            return false;
        }
        if (!ValidationUtils.isMobileNO(phoneNumber) || phoneNumber.length() < 11) {
            Toast.makeText(SmsActivity.this, "手机号格式错误", Toast.LENGTH_SHORT).show();
            et_phone.selectAll();
            et_phone.setFocusable(true);
            et_phone.requestFocus();
            return false;
        }
        return true;
    }
}
