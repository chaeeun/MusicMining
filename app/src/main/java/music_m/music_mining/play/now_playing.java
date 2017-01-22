package music_m.music_mining.play;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import music_m.music_mining.R;

/**
 * Created by jongjookim on 2017. 1. 4..
 */

public class now_playing extends Activity {

    ImageButton move;
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing);

        move = (ImageButton)findViewById(R.id.btnClose);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(now_playing.this,Play.class);
                startActivity(intent);
            }
        });

    }
}
