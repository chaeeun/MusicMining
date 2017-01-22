package music_m.music_mining.make_id;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.regex.Pattern;

import music_m.music_mining.R;
import music_m.music_mining.application.ApplicationController;
import music_m.music_mining.movie.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicMining_FindPassword extends AppCompatActivity {

    private EditText emailInput;
    private ImageButton btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_mining__find_password);

        emailInput = (EditText)findViewById(R.id.emailInput);
        btnSend = (ImageButton) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 이메일 입력 확인
                if(emailInput.getText().toString().length() == 0) {
                    Toast.makeText(MusicMining_FindPassword.this, "Email을 입력하세요!", Toast.LENGTH_SHORT).show();
                    emailInput.requestFocus();
                    return;
                }


                //이메일 유효성 검사
                if(!Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", emailInput.getText().toString().trim())) {
                    Toast.makeText(MusicMining_FindPassword.this, "잘못된 Email 형식 입니다. 다시 입력해주세요!", Toast.LENGTH_SHORT).show();
                    emailInput.setText("");
                    emailInput.requestFocus();
                    return;
                }

                // 이 부분에 디비로 값을 보내고 확인하는 연동 추가.

                FindPasswordRequestObject object = new FindPasswordRequestObject(emailInput.getText().toString());
                Call<FindPasswordResponseObject> requestFindPassword = ApplicationController.getInstance().getNetworkService().requestFindPassword(object);
                requestFindPassword.enqueue(new Callback<FindPasswordResponseObject>() {
                    @Override
                    public void onResponse(Call<FindPasswordResponseObject> call, Response<FindPasswordResponseObject> response) {
                        if(response.isSuccessful()){
                            //Toast.makeText(MusicMining_FindPassword.this, "", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(),String.valueOf(response.body().err),Toast.LENGTH_SHORT).show();

                            if(response.body().err == 1){
                                Toast.makeText(MusicMining_FindPassword.this, "존재하지 않는 Email입니다!", Toast.LENGTH_SHORT).show();
                                emailInput.setText("");
                                emailInput.requestFocus();
                                return;
                            }
                            else { //회원가입성공
                                Toast.makeText(MusicMining_FindPassword.this, "Email을 열어 비밀번호를 확인하세요!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MusicMining_FindPassword.this, MainActivity.class);
                                startActivity(intent);
                                return;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FindPasswordResponseObject> call, Throwable t) {
                        Toast.makeText(MusicMining_FindPassword.this, "실패", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}
