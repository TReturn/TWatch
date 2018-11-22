package mcgrady.example.com.test_24;

import android.annotation.SuppressLint;
import android.app.Service;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private TextView home_tv_hour;
    private TextView home_tv_minute;
    private TextView home_tv_seconds;
    private TextView home_tv_colon;
    private TextView home_tv_week;
    private TextView home_tv_day;
    private Boolean start = true;
    private ImageView home_iv_seconds1;
    private ImageView home_iv_seconds2;
    private ImageView home_iv_seconds3;
    private ImageView home_iv_seconds4;
    private ImageView home_iv_seconds5;
    private ImageView home_iv_seconds6;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        home_tv_hour = view.findViewById(R.id.home_tv_hour);
        home_tv_minute = view.findViewById(R.id.home_tv_minute);
        home_tv_seconds = view.findViewById(R.id.home_tv_seconds);
        home_tv_colon = view.findViewById(R.id.home_tv_colon);
        home_tv_week = view.findViewById(R.id.home_tv_week);
        home_tv_day = view.findViewById(R.id.home_tv_day);

        home_iv_seconds1 = view.findViewById(R.id.home_iv_seconds1);
        home_iv_seconds2 = view.findViewById(R.id.home_iv_seconds2);
        home_iv_seconds3 = view.findViewById(R.id.home_iv_seconds3);
        home_iv_seconds4 = view.findViewById(R.id.home_iv_seconds4);
        home_iv_seconds5 = view.findViewById(R.id.home_iv_seconds5);
        home_iv_seconds6 = view.findViewById(R.id.home_iv_seconds6);

        setFont();
        handler.sendEmptyMessageDelayed(1002,0);

    }
    private  void setFont(){
        Typeface fontType = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quartz.ttf");
        home_tv_hour.setTypeface(fontType);
        home_tv_minute.setTypeface(fontType);
        home_tv_seconds.setTypeface(fontType);
        home_tv_colon.setTypeface(fontType);
        home_tv_week.setTypeface(fontType);
        home_tv_day.setTypeface(fontType);
    }

    private void getTime(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter=new SimpleDateFormat("HH");
        Date curDate = new Date(System.currentTimeMillis());
        String  hour  = formatter.format(curDate);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2=new SimpleDateFormat("mm");
        Date curDate2 = new Date(System.currentTimeMillis());
        String  minute  = formatter2.format(curDate2);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter3=new SimpleDateFormat("ss");
        Date curDate3 = new Date(System.currentTimeMillis());
        String  seconds  = formatter3.format(curDate3);
        int intSeconds = Integer.parseInt(seconds);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter4=new SimpleDateFormat("MM-dd");
        Date curDate4 = new Date(System.currentTimeMillis());
        String  day  = formatter4.format(curDate4);

        if (intSeconds>=1&&intSeconds<10){
            secondsColor(false,false,false,false,false,false);
        }else if (intSeconds>=10&&intSeconds<20){
            secondsColor(true,false,false,false,false,false);
        }else if (intSeconds>=20&&intSeconds<30){
            secondsColor(true,true,false,false,false,false);
        }else if (intSeconds>=30&&intSeconds<40){
            secondsColor(true,true,true,false,false,false);
        }else if (intSeconds>=40&&intSeconds<50){
            secondsColor(true,true,true,true,false,false);
        }else if (intSeconds>=50&&intSeconds<60){
            secondsColor(true,true,true,true,true,false);
        }else {
            secondsColor(true,true,true,true,true,true);
        }

        //获取星期
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                home_tv_week.setText("SUN");
                break;
            case 2:
                home_tv_week.setText("MON");
                break;
            case 3:
                home_tv_week.setText("TUE");
                break;
            case 4:
                home_tv_week.setText("WED");
                break;
            case 5:
                home_tv_week.setText("THU");
                break;
            case 6:
                home_tv_week.setText("FRI");
                break;
            case 7:
                home_tv_week.setText("SAT");
                break;
        }

        home_tv_hour.setText(hour);
        home_tv_minute.setText(minute);
        home_tv_seconds.setText(seconds);
        home_tv_day.setText(day);
    }

    private void secondsColor(Boolean a,Boolean b,Boolean c,Boolean d,Boolean e,Boolean f){
        if (a){
            home_iv_seconds1.setBackgroundResource(R.drawable.bg_seconds_black);
        }else {
            home_iv_seconds1.setBackgroundResource(R.drawable.bg_seconds_null);
        }
        if (b){
            home_iv_seconds2.setBackgroundResource(R.drawable.bg_seconds_black);
        }else {
            home_iv_seconds2.setBackgroundResource(R.drawable.bg_seconds_null);
        }
        if (c){
            home_iv_seconds3.setBackgroundResource(R.drawable.bg_seconds_black);
        }else {
            home_iv_seconds3.setBackgroundResource(R.drawable.bg_seconds_null);
        }
        if (d){
            home_iv_seconds4.setBackgroundResource(R.drawable.bg_seconds_black);
        }else {
            home_iv_seconds4.setBackgroundResource(R.drawable.bg_seconds_null);
        }
        if (e){
            home_iv_seconds5.setBackgroundResource(R.drawable.bg_seconds_black);
        }else {
            home_iv_seconds5.setBackgroundResource(R.drawable.bg_seconds_null);
        }
        if (f){
            home_iv_seconds6.setBackgroundResource(R.drawable.bg_seconds_black);
        }else {
            home_iv_seconds6.setBackgroundResource(R.drawable.bg_seconds_null);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1002:
                    if (start){
                        getTime();
                        handler.sendEmptyMessageDelayed(1002,1000);
                    }
                    break;
            }

        }
    };
}
