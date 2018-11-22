package mcgrady.example.com.test_24;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SecondsFragment extends Fragment implements View.OnClickListener {

    private TextView seconds_tv_colon1;
    private TextView seconds_tv_colon2;
    private TextView seconds_tv_colon3;
    private TextView seconds_tv_hour;
    private TextView seconds_tv_minute;
    private TextView seconds_tv_seconds;
    private TextView seconds_tv_mesc;
    private Button seconds_btn_start;
    private Button seconds_btn_pause;
    private Button seconds_btn_restart;

    private double mesc = 0.0;
    private int seconds = 0;
    private int minute = 0;
    private int hour = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seconds,null);

        initView(view);
        return view;
    }

    private void initView(View view) {

        seconds_tv_colon1 = view.findViewById(R.id.seconds_tv_colon1);
        seconds_tv_colon2 = view.findViewById(R.id.seconds_tv_colon2);
        seconds_tv_colon3 = view.findViewById(R.id.seconds_tv_colon3);
        seconds_tv_hour = view.findViewById(R.id.seconds_tv_hour);
        seconds_tv_minute = view.findViewById(R.id.seconds_tv_minute);
        seconds_tv_seconds = view.findViewById(R.id.seconds_tv_seconds);
        seconds_tv_mesc = view.findViewById(R.id.seconds_tv_msec);
        seconds_btn_start = view.findViewById(R.id.seconds_btn_start);
        seconds_btn_start.setOnClickListener(this);
        seconds_btn_pause = view.findViewById(R.id.seconds_btn_pause);
        seconds_btn_pause.setOnClickListener(this);
        seconds_btn_restart = view.findViewById(R.id.seconds_btn_restart);
        seconds_btn_restart.setOnClickListener(this);

        seconds_tv_hour.setVisibility(View.INVISIBLE);
        seconds_tv_colon3.setVisibility(View.INVISIBLE);

        setFont();


    }

    private  void setFont(){
        Typeface fontType = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quartz.ttf");
        seconds_tv_colon1.setTypeface(fontType);
        seconds_tv_colon2.setTypeface(fontType);
        seconds_tv_colon3.setTypeface(fontType);
        seconds_tv_hour.setTypeface(fontType);
        seconds_tv_minute.setTypeface(fontType);
        seconds_tv_seconds.setTypeface(fontType);
        seconds_tv_mesc.setTypeface(fontType);
    }

    private void setMesc(){

        double i = (5.0 / 3.0);
        Log.d("有",""+i);
        mesc = mesc+i;
        Log.d("有2",""+Math.round(mesc));

        if (mesc < 10){
            seconds_tv_mesc.setText("0"+Math.round(mesc));
        }else if (mesc >10&&mesc<100){
            seconds_tv_mesc.setText(""+Math.round(mesc));
        }
        if (mesc >= 100){
            mesc = 0.0;
            seconds = seconds + 1;
            seconds_tv_mesc.setText("0"+Math.round(mesc));
        }


        //mesc = 0;
        if (seconds < 10){
            seconds_tv_seconds.setText("0"+seconds);
        }else {
            seconds_tv_seconds.setText(""+seconds);
        }

        if (seconds == 60){
            minute = minute + 1;
            seconds = 0;
            if (minute < 10){
                seconds_tv_minute.setText("0"+minute);
            }else {
                seconds_tv_minute.setText(""+minute);
            }
            seconds_tv_seconds.setText("0"+seconds);
        }
        if (minute == 60){
            hour = hour + 1;
            minute = 0;

            seconds_tv_hour.setText(""+hour);

            seconds_tv_minute.setText("0"+minute);
        }
        if (hour >= 1){
            seconds_tv_hour.setVisibility(View.VISIBLE);
            seconds_tv_colon3.setVisibility(View.VISIBLE);
        }


    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1003:

                    setMesc();
                    handler.sendEmptyMessageDelayed(1003, 10);

                    break;

            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.seconds_btn_start:

                handler.sendEmptyMessageDelayed(1003,0);
                seconds_btn_start.setBackgroundResource(R.drawable.button_enabled);
                seconds_btn_start.setEnabled(false);

                break;
            case R.id.seconds_btn_pause:

                seconds_btn_start.setEnabled(true);
                seconds_btn_start.setBackgroundResource(R.drawable.button_enabled_true);
                handler.removeMessages(1003);
                break;
            case R.id.seconds_btn_restart:

                seconds_btn_start.setBackgroundResource(R.drawable.button_enabled_true);
                handler.removeMessages(1003);
                seconds_btn_start.setEnabled(true);

                mesc = 0.0;
                seconds_tv_mesc.setText("0"+Math.round(mesc));
                seconds = 0;
                seconds_tv_seconds.setText("0"+seconds);
                minute = 0;
                seconds_tv_minute.setText("0"+minute);
                hour = 0;
                seconds_tv_hour.setText(""+hour);

                break;
        }
    }
}
