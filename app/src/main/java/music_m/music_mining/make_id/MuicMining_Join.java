package music_m.music_mining.make_id;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Pattern;

import music_m.music_mining.R;
import music_m.music_mining.application.ApplicationController;
import music_m.music_mining.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MuicMining_Join extends AppCompatActivity {

    //public static  String SERVER_ADRESS = "http://52.78.156.235:3000";

        private EditText emailInput;
        private EditText passwordInput;
        private EditText passwordCheck;
        private EditText nameInput;
        private DatePicker birthInput;
        private RadioGroup sexInput;
        private RadioButton sex1;
        private RadioButton sex2;
        private ImageButton btnJoin;
        private boolean gender_check;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muic_mining__join);

        emailInput = (EditText)findViewById(R.id.emailInput);
        passwordInput = (EditText)findViewById(R.id.passwordInput);
        passwordCheck = (EditText)findViewById(R.id.passwordCheck);
        nameInput = (EditText)findViewById(R.id.nameInput);
        birthInput = (DatePicker)findViewById(R.id.birthInput);
        sexInput = (RadioGroup)findViewById(R.id.sexInput);
            sex1 = (RadioButton)findViewById(R.id.sex1);
            sex2 = (RadioButton)findViewById(R.id.sex2);
        btnJoin = (ImageButton)findViewById(R.id.btnJoin);
        gender_check = true;
        //비밀번호일치검사
        passwordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = passwordInput.getText().toString();
                String p_check = passwordCheck.getText().toString();

                if(password.equals(p_check)
                        && Pattern.matches("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])", passwordInput.getText().toString().trim())
                        && passwordInput.getText().toString().length() > 5 && passwordInput.getText().toString().length() < 17){
                    passwordInput.setTextColor(Color.GREEN);
                    passwordCheck.setTextColor(Color.GREEN);
                    //passwordInput.setBackgroundColor(Color.GREEN);
                    //passwordCheck.setBackgroundColor(Color.GREEN);
                } else{
                    passwordInput.setTextColor(Color.RED);
                    passwordCheck.setTextColor(Color.RED);
                    //passwordInput.setBackgroundColor(Color.RED);
                    //passwordCheck.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 이메일 입력 확인
                if(emailInput.getText().toString().length() == 0) {
                    Toast.makeText(MuicMining_Join.this, "Email을 입력하세요!", Toast.LENGTH_SHORT).show();
                    emailInput.requestFocus();
                    return;
                }

                //이메일 유효성 검사
                if(!Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", emailInput.getText().toString().trim())){
                    Toast.makeText(MuicMining_Join.this, "잘못된 Email 형식 입니다. 다시 입력해주세요!", Toast.LENGTH_SHORT).show();
                    emailInput.setText("");
                    emailInput.requestFocus();
                    return;
                }

                // 비밀번호 입력 확인
                if(passwordInput.getText().toString().length() == 0){
                    Toast.makeText(MuicMining_Join.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    passwordInput.requestFocus();
                    return;
                }

                //비밀번호 유효성 검사
                if(!Pattern.matches("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])", passwordInput.getText().toString().trim())
                        || passwordInput.getText().toString().length() < 6 || passwordInput.getText().toString().length() > 16){
                    Toast.makeText(MuicMining_Join.this, "잘못된 Password 형식 입니다. 다시 입력해주세요!", Toast.LENGTH_SHORT).show();
                    passwordInput.setText("");
                    passwordCheck.setText("");
                    passwordInput.requestFocus();
                    return;
                }
                // 비밀번호 확인 입력 확인
                if(passwordCheck.getText().toString().length() == 0){
                    Toast.makeText(MuicMining_Join.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    passwordCheck.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if(!passwordInput.getText().toString().equals(passwordCheck.getText().toString())){
                    Toast.makeText(MuicMining_Join.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    passwordInput.setText("");
                    passwordCheck.setText("");
                    passwordInput.requestFocus();
                    return;
                }

                // 이름 입력 확인
                if(nameInput.getText().toString().length() == 0) {
                    Toast.makeText(MuicMining_Join.this, "Name을 입력하세요!", Toast.LENGTH_SHORT).show();
                    nameInput.requestFocus();
                    return;
                }

                // 생일 확인

                // 성별 확인
                if(sex1.isChecked() == false && sex2.isChecked() == false){
                    Toast.makeText(MuicMining_Join.this, "성별을 선택하세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(sex1.isChecked() == true){
                    gender_check = true;
                }
                else{
                    gender_check = false;
                }

                // 이 부분에 디비로 값을 보내고 확인하는 연동 추가.

                String birthString =String.format("%d%d%d", birthInput.getYear(), birthInput.getMonth()+1, birthInput.getDayOfMonth());
                LoginRequestObject object = new LoginRequestObject(emailInput.getText().toString(),passwordInput.getText().toString(),nameInput.getText().toString(),gender_check, birthString);

                Call<LoginResponseObject> requestLogin = ApplicationController.getInstance().getNetworkService().requestLogin(object);
                requestLogin.enqueue(new Callback<LoginResponseObject>() {
                    @Override
                    public void onResponse(Call<LoginResponseObject> call, Response<LoginResponseObject> response) {
                        if(response.isSuccessful()){
                            //Toast.makeText(getApplicationContext(),String.valueOf(response.body().err),Toast.LENGTH_SHORT).show();
                            if(response.body().err == 1){
                                Toast.makeText(MuicMining_Join.this, "중복된 Email입니다!", Toast.LENGTH_SHORT).show();
                                emailInput.setText("");
                                emailInput.requestFocus();
                                return;
                            }
                            else { //회원가입성공
                                Toast.makeText(MuicMining_Join.this, "이메일 인증코드 전송!", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(MuicMining_Join.this, LoginActivity.class);
                                startActivity(intent);
                                return;
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponseObject> call, Throwable t) {
                        Toast.makeText(MuicMining_Join.this, "실패", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}
