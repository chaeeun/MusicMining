package music_m.music_mining.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import music_m.music_mining.R;
import music_m.music_mining.Song_Detail.Song_Infomation;
import music_m.music_mining.application.ApplicationController;
import music_m.music_mining.model.PlayListData;
import music_m.music_mining.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jongjookim on 2017. 1. 1..
 */

public class MainList extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    Adapter adapter;
    ArrayList<PlayListData> PlayD;

    //희원추가
    ImageButton btnFastLeft;
    ImageButton btnFastPlay;
    ImageButton btnFastRight;
    ApplicationController myApp;
    //여기까지

    boolean listCheck;
    //ImageView Play_Btn;

    NetworkService service;
    Activity activity;
    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.play_list);
        setContentView(R.layout.play_list);
        //adapter = new Adapter(PlayList);

        PlayD = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        activity = this;
        listCheck = false;

        service = ApplicationController.getInstance().getNetworkService();
        //저기에 user id 담아야해여 jjongju8790
        Call<Playlist> getListData = service.getAllList();
        getListData.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if(response.isSuccessful()){ // 응답코드 200

                    Playlist pl = response.body();
                    Log.e("dd",""+pl.getMusics().size());


                    // layoutManager 설정
                    if(pl.getMusics() == null){
                        listCheck = true;
                        adapter = new Adapter(pl.getMusics(), clickEvent, activity);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        // Adapter adapter = new adapter(pl.getMusic())
                        //adapter.setAdapter(pl.getMusics());
                        Log.i("myTag","1");
                        myApp.setMp_state(myApp.getMp_position());
                    }

                    //Toast.makeText(MainList.this, "성공", Toast.LENGTH_SHORT).show();
                }
                else
                    Log.i("myTag","2 들어가고싶다..");
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Log.i("myTag", "failure : "+ t.toString());
            }
        });

        if(listCheck) {
            //희원추가
            btnFastLeft = (ImageButton) findViewById(R.id.btnFastLeft);
            btnFastPlay = (ImageButton) findViewById(R.id.btnFastPlay);
            btnFastRight = (ImageButton) findViewById(R.id.btnFastRight);
            myApp = (ApplicationController) getApplicationContext();
            //myApp.setMp_state(0);
            btnFastPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myApp.playMp_state();
                }
            });
            btnFastLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myApp.getMp_position() != 0) {
                        myApp.getMp_state().pause();
                        myApp.setMp_playChecker();
                        myApp.setMp_state(myApp.getMp_position() - 1);
                        myApp.playMp_state();
                    }
                }
            });
            btnFastRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myApp.getMp_state().pause();
                    myApp.setMp_playChecker();
                    if (myApp.getMp_position() == myApp.getMax_position()) {
                        myApp.setMp_state(0);
                    } else {
                        myApp.setMp_state(myApp.getMp_position() + 1);
                    }
                    myApp.setMp_Next();
                    myApp.playMp_state();
                }
            });
        }
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildAdapterPosition(v);
            Intent intent = new Intent(MainList.this, Song_Infomation.class);
            intent.putExtra("musicid", myApp.getMp_List().get(itemPosition).getMusicId());
            startActivity(intent);
        }
    };
/*
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            //int itemPosition = recyclerView.getChildPosition(v);
            //int tempId = adapter.get(itemPosition).id;
            Intent intent = new Intent(getApplicationContext(),Play.class);
            intent.putExtra("id",String.valueOf(tempId));
            startActivity(intent);

        }
    };*/

    @Override
    public void onBackPressed() {
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;

//            super.onBackPressed();
        /**
         * Back키 두번 연속 클릭 시 앱 종료
         */
        if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
            super.onBackPressed();
        }
        else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(),"뒤로 가기 키을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }

    }

}
