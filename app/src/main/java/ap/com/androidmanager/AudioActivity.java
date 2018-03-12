package ap.com.androidmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class AudioActivity extends BaseActivity {

    private AudioManager audiomanage;
    private TextView audio_type;
    private int streamType = AudioManager.STREAM_SYSTEM;
    private int flags = AudioManager.FLAG_SHOW_UI;
    private EditText et_audio_size;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_audio);
        init();
    }

    private void init() {
        audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio_type = (TextView) findViewById(R.id.audio_type);
        et_audio_size = (EditText) findViewById(R.id.et_audio_size);
        RadioButton rb1 = (RadioButton) findViewById(R.id.rb1);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rb2);
        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) flags = AudioManager.FLAG_SHOW_UI;
            }
        });
        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) flags = AudioManager.FLAG_PLAY_SOUND;
            }
        });
    }

    public void chooseAudioType(View view) {
        final String[] items = {"手机系统", "闹铃", "音乐", "电话铃声", "音调", "系统提示", "语音电话"};
        final int[] streamTypes = {AudioManager.STREAM_SYSTEM, AudioManager.STREAM_ALARM,
                AudioManager.STREAM_MUSIC, AudioManager.STREAM_RING, AudioManager.STREAM_DTMF,
                AudioManager.STREAM_NOTIFICATION, AudioManager.STREAM_VOICE_CALL};
        AlertDialog.Builder builder = new AlertDialog.Builder(AudioActivity.this);
        builder.setTitle("音频类型");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                audio_type.setText(items[which]);
                streamType = streamTypes[which];
            }
        });
        builder.show();
    }

    public void addOrsub(View view) {
        int direction = AudioManager.ADJUST_RAISE;
        switch (view.getId()) {
            case R.id.add:
                direction = AudioManager.ADJUST_RAISE;
                break;
            case R.id.sub:
                direction = AudioManager.ADJUST_LOWER;
                break;
        }
        audiomanage.adjustStreamVolume(streamType, direction, flags);
    }

    public void setStreamVolume(View view) {
        String str = et_audio_size.getText().toString();
        if ("".equals(str)) {
            prompt(et_audio_size, "请输入音频大小");
            return;
        }
        int k = Integer.parseInt(str) / 10;
        if (k < 0 || k > 100) {
            prompt(et_audio_size, "输入参数错误");
            return;
        }
        audiomanage.setStreamVolume(streamType, k, flags);
    }

    public void setRingerMode(View view) {
        int ringerMode = AudioManager.RINGER_MODE_NORMAL;
        switch (view.getId()) {
            case R.id.normal:
                break;
            case R.id.silent:
                ringerMode = AudioManager.RINGER_MODE_SILENT;
                break;
            case R.id.vibrate:
                ringerMode = AudioManager.RINGER_MODE_VIBRATE;
                break;
        }
        audiomanage.setRingerMode(ringerMode);
    }
}
