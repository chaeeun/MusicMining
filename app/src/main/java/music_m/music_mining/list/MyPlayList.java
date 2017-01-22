package music_m.music_mining.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import music_m.music_mining.R;
import music_m.music_mining.Song_Detail.Song_Infomation;
import music_m.music_mining.application.ApplicationController;
import music_m.music_mining.model.MainResult;
import music_m.music_mining.model.PlayListData;
import music_m.music_mining.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jongjookim on 2017. 1. 1..
 */

public class MyPlayList extends AppCompatActivity {

    Playlist pl;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    Adapter adapter;
    ArrayList<PlayListData> PlayD;
    private String user_id;
    private Paint p = new Paint();

    //희원추가
    ImageButton btnFastLeft;
    ImageButton btnFastPlay;
    ImageButton btnFastRight;
    ApplicationController myApp;

    ImageView album_image;
    TextView barTitle;
    TextView barMusician;
    //여기까지
    boolean listCheck;

    ImageView addBtn;
    ImageView addBtn2;
    //ImageView Play_Btn;

    NetworkService service;
    Activity activity;
    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_list);
        //adapter = new Adapter(PlayList);
        Intent intent = getIntent();
        user_id = intent.getExtras().getString("EMAIL");
        listCheck = false;

        PlayD = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        album_image = (ImageView)findViewById(R.id.album_image);
        barTitle = (TextView)findViewById(R.id.barTitle);
        barMusician = (TextView)findViewById(R.id.barMusician);
        activity = this;
        service = ApplicationController.getInstance().getNetworkService();
        //저기에 user id 담아야해여 jjongju8790
        Call<Playlist> getListData = service.getList(user_id);
        getListData.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if(response.isSuccessful()){ // 응답코드 200

                    pl = response.body();
                    Log.e("dd",""+pl.getMusics().size());


                    if(pl.getMusics().size() != 0){
                        listCheck = true;
                        adapter = new Adapter(pl.getMusics(), clickEvent, activity);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        // Adapter adapter = new adapter(pl.getMusic())
                        //adapter.setAdapter(pl.getMusics());
                        Log.i("myTag","1");

                        myApp.setMp_state(myApp.getMp_position());
                        initSwipe();
                        setControlBar();
                    }
                    else{
                        Toast.makeText(MyPlayList.this, "음악을 추가해 주세요.", Toast.LENGTH_SHORT).show();
                    }


                    // layoutManager 설정


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
        //희원추가
        btnFastLeft = (ImageButton) findViewById(R.id.btnFastLeft);
        btnFastPlay = (ImageButton) findViewById(R.id.btnFastPlay);
        btnFastRight = (ImageButton) findViewById(R.id.btnFastRight);
        myApp = (ApplicationController) getApplicationContext();
        //myApp.setMp_state(0);
        btnFastPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listCheck){
                    myApp.playMp_state();
                }
                else{
                    Toast.makeText(MyPlayList.this, "음악을 추가해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnFastLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listCheck) {
                    if (myApp.getMp_position() != 0) {
                        myApp.getMp_state().pause();
                        myApp.setMp_playChecker();
                        myApp.setMp_state(myApp.getMp_position() - 1);
                        myApp.playMp_state();
                        setControlBar();
                    }
                }
                else{
                    Toast.makeText(MyPlayList.this, "음악을 추가해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnFastRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listCheck) {
                    myApp.getMp_state().pause();
                    myApp.setMp_playChecker();
                    if (myApp.getMp_position() == myApp.getMax_position()) {
                        myApp.setMp_state(0);
                    } else {
                        myApp.setMp_state(myApp.getMp_position() + 1);
                    }
                    myApp.setMp_Next();
                    myApp.playMp_state();
                    setControlBar();
                }
                else{
                    Toast.makeText(MyPlayList.this, "음악을 추가해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addBtn = (ImageView) findViewById(R.id.addBtn);
        addBtn2 = (ImageView) findViewById(R.id.addBtn2);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyPlayList.this, "서비스 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        addBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    Call<MainResult> mService = ApplicationController.getInstance().getNetworkService().deleteMusic(user_id, pl.getMusics().get(position).getMusicId());
                    mService.enqueue(new Callback<MainResult>() {
                        @Override
                        public void onResponse(Call<MainResult> call, Response<MainResult> response) {
                            if(response.isSuccessful()) {
                                if(response.body().err == 0) {
                                    adapter.removeItem(position);
                                }else if(response.body().err == 1) {
                                }else if (response.body().err == 2) {
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MainResult> call, Throwable t) {
                        }
                    });
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX <= 0) {
                        p.setColor(Color.parseColor("#80D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_sweep_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    public View.OnClickListener clickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildAdapterPosition(v);
            Intent intent = new Intent(MyPlayList.this, Song_Infomation.class);
            intent.putExtra("musicid", myApp.getMp_List().get(itemPosition).getMusicId());
            startActivity(intent);
        }
    };

    public void setControlBar(){
        barTitle.setText(myApp.getMp_List().get(myApp.getMp_position()).getTitle());
        barMusician.setText(myApp.getMp_List().get(myApp.getMp_position()).getMusicianName());
        Glide.with(getApplicationContext()).load(myApp.getMp_List().get(myApp.getMp_position()).getAlbumImageUrl()).into(album_image);
    }
/*

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            int tempId = adapter.get(itemPosition).id;
            Intent intent = new Intent(getApplicationContext(),Play.class);
            intent.putExtra("id",String.valueOf(tempId));
            startActivity(intent);

        }
    };*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myApp.getMp_state().stop();
        finish();
    }
}