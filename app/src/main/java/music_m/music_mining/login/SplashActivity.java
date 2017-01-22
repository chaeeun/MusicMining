package music_m.music_mining.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import music_m.music_mining.R;

/**
 * Created by kki53 on 2017-01-04.
 */

public class SplashActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);// 4 초

    }

}
