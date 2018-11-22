package mcgrady.example.com.test_24;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import static android.content.Context.NOTIFICATION_SERVICE;

public class CountdownFragment extends Fragment implements View.OnClickListener {

    private Button btn_start;
    private Button btn_pause;
    private Button btn_resetting;
    private Button btn_up_hour;
    private Button btn_down_hour;
    private Button btn_up_minute;
    private Button btn_down_minute;
    private Button btn_up_seconds;
    private Button btn_down_seconds;
    private CheckBox cb_playMusic;
    private CheckBox cb_vibrate;
    private TextView tv_hour;
    private TextView tv_minute;
    private TextView tv_seconds;
    private TextView tv_colon;
    private int seconds = 0;
    private int minute = 10;
    private int hour = 0;
    private Boolean start = false;
    private Boolean playMusic = true;
    private Boolean vibrate = true;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countdown,null);

        initView(view);
        return view;
    }



    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case 1001:

                    tv_seconds.setText(""+seconds);

                    if (start){
                        if (seconds > -1){
                            seconds = seconds-1;
                            handler.sendEmptyMessageDelayed(1001,1000);

                        }
                        if (seconds == -1){
                            if (minute != 0){
                                minute = minute-1;;
                                tv_minute.setText(""+minute);
                                seconds = 59;

                            }else if (minute == 0){
                                if (hour != 0){
                                    hour = hour-1;;
                                    tv_hour.setText(""+hour);
                                    minute = 59;
                                    tv_minute.setText(""+minute);
                                    seconds = 59;
                                }else if (hour == 0){
                                    //通知结束
                                    handler.removeMessages(1001);
                                    //通知栏
                                    sendNotice();
                                    //音频
                                    if (playMusic){
                                        initMediaPlayer();
                                    }
                                    //震动
                                    if (vibrate){
                                        Vibrator vib = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
                                        assert vib != null;
                                        vib.vibrate(3000);
                                    }

                                }
                            }

                        }}

                    break;
            }

        }
    };

    private void initView(View view) {

        btn_start = view.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        btn_pause = view.findViewById(R.id.btn_pause);
        btn_pause.setOnClickListener(this);
        btn_resetting = view.findViewById(R.id.btn_resetting);
        btn_resetting.setOnClickListener(this);
        btn_up_hour = view.findViewById(R.id.btn_up_hour);
        btn_up_hour.setOnClickListener(this);
        btn_down_hour = view.findViewById(R.id.btn_down_hour);
        btn_down_hour.setOnClickListener(this);
        btn_up_minute = view.findViewById(R.id.btn_up_minute);
        btn_up_minute.setOnClickListener(this);
        btn_down_minute = view.findViewById(R.id.btn_down_minute);
        btn_down_minute.setOnClickListener(this);
        btn_up_seconds = view.findViewById(R.id.btn_up_seconds);
        btn_up_seconds.setOnClickListener(this);
        btn_down_seconds = view.findViewById(R.id.btn_down_seconds);
        btn_down_seconds.setOnClickListener(this);

        cb_playMusic = view.findViewById(R.id.cb_playMusic);
        cb_vibrate = view.findViewById(R.id.cb_vibrate);

        tv_hour = view.findViewById(R.id.tv_hour);
        tv_minute = view.findViewById(R.id.tv_minute);
        tv_seconds = view.findViewById(R.id.tv_seconds);
        tv_colon = view.findViewById(R.id.tv_colon);
        setFont();

        cb_playMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    playMusic = true;
                    Log.d("有1",""+playMusic);
                }else {
                    playMusic = false;
                    Log.d("有2",""+playMusic);
                }
            }
        });
        cb_vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    vibrate = true;
                }else {
                    vibrate = false;
                }
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_start:

                if (seconds != -1){
                    inVisible();
                }

                break;

            case R.id.btn_pause:
                btn_start.setEnabled(true);
                btn_start.setBackgroundResource(R.drawable.button_enabled_true);
                handler.removeMessages(1001);

                break;

            case R.id.btn_resetting:

                visible();

            case R.id.btn_up_hour:
                if (hour < 23){
                    hour = hour+1;
                    tv_hour.setText(""+hour);
                }else if (hour == 23){
                    hour = 0;
                    tv_hour.setText(""+hour);
                }
                break;
            case R.id.btn_down_hour:
                if (hour > 0){
                    hour = hour-1;
                    tv_hour.setText(""+hour);
                }else if (hour == 0){
                    hour = 23;
                    tv_hour.setText(""+hour);
                }
                break;

            case R.id.btn_up_minute:
                if (minute < 59){
                    minute = minute+1;
                    tv_minute.setText(""+minute);
                }else if (minute == 59){
                    minute = 0;
                    tv_minute.setText(""+minute);
                }

                break;
            case R.id.btn_down_minute:
                if (minute > 0){
                    minute = minute-1;
                    tv_minute.setText(""+minute);
                }else if (minute == 0){
                    minute = 59;
                    tv_minute.setText(""+minute);
                }
                break;

            case R.id.btn_up_seconds:
                if (seconds < 59){
                    seconds = seconds+1;
                    tv_seconds.setText(""+seconds);
                }else if (seconds == 59){
                    seconds = 0;
                    tv_seconds.setText(""+seconds);
                }

                break;

            case R.id.btn_down_seconds:
                if (seconds > 0){
                    seconds = seconds-1;
                    tv_seconds.setText(""+seconds);
                }else if (seconds == 0){
                    seconds = 59;
                    tv_seconds.setText(""+seconds);
                }
                break;
        }
    }

    private void sendNotice(){

        NotificationManager manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(getActivity(),"default")
                .setContentTitle("倒计时结束！")
                .setContentText("")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        assert manager != null;
        manager.notify(1,notification);
    }

    private void visible(){
        btn_start.setEnabled(true);
        btn_up_hour.setVisibility(View.VISIBLE);
        btn_down_hour.setVisibility(View.VISIBLE);
        btn_up_minute.setVisibility(View.VISIBLE);
        btn_down_minute.setVisibility(View.VISIBLE);
        btn_up_seconds.setVisibility(View.VISIBLE);
        btn_down_seconds.setVisibility(View.VISIBLE);
        btn_start.setBackgroundResource(R.drawable.button_enabled_true);
        mediaPlayer.reset();
        handler.removeMessages(1001);
        seconds = 0;
        tv_seconds.setText(""+seconds);
        minute = 10;
        tv_minute.setText(""+minute);
        hour = -1;
        tv_hour.setText(""+hour);
    }

    private void inVisible(){
        btn_start.setEnabled(false);
        btn_up_hour.setVisibility(View.INVISIBLE);
        btn_down_hour.setVisibility(View.INVISIBLE);
        btn_up_minute.setVisibility(View.INVISIBLE);
        btn_down_minute.setVisibility(View.INVISIBLE);
        btn_up_seconds.setVisibility(View.INVISIBLE);
        btn_down_seconds.setVisibility(View.INVISIBLE);
        btn_start.setBackgroundResource(R.drawable.button_enabled);
        start = true;
        handler.sendEmptyMessageDelayed(1001,0);
    }

    private void initMediaPlayer() {
        try {
            AssetFileDescriptor file = this.getResources().openRawResourceFd(R.raw.music);
            mediaPlayer.setDataSource(file.getFileDescriptor(),file.getStartOffset(),file.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void setFont(){
        Typeface fontType = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quartz.ttf");
        tv_hour.setTypeface(fontType);
        tv_minute.setTypeface(fontType);
        tv_seconds.setTypeface(fontType);
        tv_colon.setTypeface(fontType);
    }

}
