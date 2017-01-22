package music_m.music_mining.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.android.tedcoder.wkvideoplayer.dlna.service.DLNAService;

import java.util.ArrayList;

import music_m.music_mining.R;
import music_m.music_mining.application.ApplicationController;
import music_m.music_mining.list.MyPlayList;
import music_m.music_mining.model.MovieListData;
import music_m.music_mining.model.MovieResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    public String user_id;
    private View mPlayBtnView;
    private ImageButton btnList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        btnList = (ImageButton)findViewById(R.id.btnList);
        Intent intent = getIntent();
        user_id = intent.getExtras().getString("EMAIL");

        Call<MovieResult> movieLists = ApplicationController.getInstance().getNetworkService().getMovieData();
        movieLists.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().err == 0) {
                        //Toast.makeText(MainActivity.this, "서버연동성공", Toast.LENGTH_SHORT).show();
                        MovieResult movieResult = response.body();
                        ArrayList<MovieListData> movieLists = movieResult.getData();
                        mCardAdapter = new CardPagerAdapter(user_id);
                        mCardAdapter.addCardItem(movieLists);
                        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

                        mViewPager.setAdapter(mCardAdapter);
                        mViewPager.setPageTransformer(false, mCardShadowTransformer);
                        mViewPager.setOffscreenPageLimit(3);
                        mCardShadowTransformer.enableScaling(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });


        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyPlayList.class);
                intent.putExtra("EMAIL",user_id);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDLNAService();
    }

    private void stopDLNAService() {
        Intent intent = new Intent(getApplicationContext(), DLNAService.class);
        stopService(intent);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


}