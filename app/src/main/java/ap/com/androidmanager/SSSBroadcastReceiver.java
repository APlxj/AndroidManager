package ap.com.androidmanager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * 类描述：发送状态返回处理
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class SSSBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                Toast.makeText(context, "短信发送成功", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE://普通错误
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF://无线广播被明确地关闭
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU://没有提供pdu
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE://服务当前不可用
                break;
        }
    }
}
