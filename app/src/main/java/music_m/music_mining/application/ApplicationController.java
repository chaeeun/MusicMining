package music_m.music_mining.application;

import android.app.Activity;
import android.app.Application;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.kakao.auth.KakaoSDK;

import java.io.IOException;
import java.util.ArrayList;

import music_m.music_mining.kakao.KakaoSDKAdapter;
import music_m.music_mining.list.MyPlayList;
import music_m.music_mining.model.PlayListResult;
import music_m.music_mining.network.NetworkService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jongjookim on 2016. 12. 29..
 */

public class ApplicationController extends Application {

    private static ApplicationController instance;
    private static volatile Activity currentActivity = null;

    private static String baseUrl = "http://52.78.156.235:3000/";

    //희원 전역변수
    private int mp_position;
    MediaPlayer mp_state;
    private int mp_playChecker;
    //ArrayList<PlayListData> mp_List;
    ArrayList<PlayListResult> mp_List;
    private int createCheck;;
    Activity activty;

    public static String getBaseUrl() {
        return baseUrl;
    }

    private NetworkService networkService;

    public static ApplicationController getInstance() {
        return instance;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

    public void setActivtiy(Activity activtiy) {
        this.activty = activtiy;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
        buildService();

        //희원 전역변수 초기화
        mp_position = 0;
        mp_playChecker = 0;
        mp_state = new MediaPlayer();
        mp_state.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp_List = new ArrayList<>();
        createCheck=0;
        //setMp_state(0);
    }

    /*------------------------------------------------------------희원 소스------------------------------------------*/
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setMp_position(int sumval){
        this.mp_position += sumval;
    }
    public int getMp_position(){
        return mp_position;
    }
    public int getMax_position(){
        return mp_List.size()-1;
    }
    public void setMp_playChecker(){
        mp_playChecker = 0;
    }
    public int getMp_playChecker(){
        return mp_playChecker;
    }
    public void setMp_state(int position){
        try {
            mp_state = new MediaPlayer();
            mp_state.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp_position = position;
            mp_state.setDataSource(mp_List.get(position).getMusicUrl());
            mp_state.prepare();
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }
    public void setMp_Next(){
        mp_state.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mp_position == getMax_position()){
                    setMp_state(0);
                }
                else{
                    setMp_state(getMp_position()+1);
                }
                //activity.setItem();
                ((MyPlayList)activty).setControlBar();
                mp_state.start();
            }
        });
    }
    public MediaPlayer getMp_state(){
        return mp_state;
    }
    public void playMp_state(){
        if(mp_playChecker == 0){
            mp_state.start();
            mp_playChecker = 1;
        }
        else{
            mp_state.pause();
            mp_playChecker = 0;
        }
    }
    public void setMp_List(ArrayList<PlayListResult> playList){
        mp_List = playList;
    }
    public  ArrayList<PlayListResult> getMp_List(){
        return mp_List;
    }


//----------------------------------------------------------------------------------------------------
    public static ApplicationController getApplicationControllerContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }
    public static void setCurrentActivity(Activity currentActivity) {
        ApplicationController.currentActivity = currentActivity;
    }

    public void buildService() {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkService = retrofit.create(NetworkService.class);
    }


}
