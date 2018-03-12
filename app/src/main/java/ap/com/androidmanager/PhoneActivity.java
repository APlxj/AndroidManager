package ap.com.androidmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class PhoneActivity extends BaseActivity {
    private EditText et_phone;
    private TelephonyManager tManager;
    private String[] phoneType = {"未知", "2G", "3G", "4G"};
    private String[] simState = {"状态未知", "无SIM卡", "被PIN加锁", "被PUK加锁",
            "被NetWork PIN加锁", "已准备好"};
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        init();
    }

    private void init() {
        tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        et_phone = (EditText) findViewById(R.id.et_phone);
        TextView msg = (TextView) findViewById(R.id.msg);
        TextView tv_rssi = (TextView) findViewById(R.id.tv_rssi);
        tManager.listen(new MyPhoneStateListener(tv_rssi), 290);
        tManager.listen(new MyPhoneStateListener(tv_rssi), PhoneStateListener.LISTEN_CALL_STATE);

        String message = "设备编号：" + tManager.getDeviceId() + "\n"
                + "软件版本：" + (tManager.getDeviceSoftwareVersion() != null ? tManager.getDeviceSoftwareVersion() : "未知") + "\n"
                + "运营商代号：" + tManager.getNetworkOperator() + "\n"
                + "运营商名称：" + tManager.getNetworkOperatorName() + "\n"
                + "网络类型：" + phoneType[tManager.getPhoneType()] + "\n"
                + "设备当前位置：" + (tManager.getCellLocation() != null ? tManager.getCellLocation().toString() : "未知位置") + "\n"
                + "SIM卡的国别：" + tManager.getSimCountryIso() + "\n"
                + "SIM卡序列号：" + tManager.getSimSerialNumber() + "\n"
                + "SIM卡状态：" + simState[tManager.getSimState()];

        msg.setText(message);
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        private int asu = 0, lastSignal = 0;
        private TextView tv_rssi;

        public MyPhoneStateListener(TextView tv_rssi) {
            this.tv_rssi = tv_rssi;
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            asu = signalStrength.getGsmSignalStrength();
            lastSignal = -113 + 2 * asu;
            tv_rssi.setText("当前手机的信号强度：" + lastSignal + " dBm");
            super.onSignalStrengthsChanged(signalStrength);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                // 无任何状态
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                // 来电铃响时
                case TelephonyManager.CALL_STATE_RINGING:
                    OutputStream os = null;
                    try {
                        os = openFileOutput("phoneList", MODE_APPEND);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    PrintStream ps = new PrintStream(os);
                    // 将来电号码记录到文件中
                    ps.println(new Date() + " 来电：" + incomingNumber);
                    ps.close();
                    break;
                default:
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    public void to(View view) {
        if (!check()) return;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void _to(View view) {
        if (!check()) return;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    private boolean check() {
        phone = et_phone.getText().toString();
        if ("".equals(phone)) {
            Toast.makeText(PhoneActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
            et_phone.selectAll();
            et_phone.setFocusable(true);
            et_phone.requestFocus();
            return false;
        }
        if (phone.length() < 11 || !ValidationUtils.isMobileNO(phone)) {
            Toast.makeText(PhoneActivity.this, "手机号格式错误", Toast.LENGTH_SHORT).show();
            et_phone.selectAll();
            et_phone.setFocusable(true);
            et_phone.requestFocus();
            return false;
        }
        return true;
    }
}
