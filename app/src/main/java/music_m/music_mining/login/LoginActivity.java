package music_m.music_mining.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import music_m.music_mining.R;
import music_m.music_mining.application.ApplicationController;
import music_m.music_mining.kakao.KakaoSignupActivity;
import music_m.music_mining.make_id.MuicMining_Join;
import music_m.music_mining.make_id.MusicMining_FindPassword;
import music_m.music_mining.model.MainListData;
import music_m.music_mining.model.MainResult;
import music_m.music_mining.movie.MainActivity;
import music_m.music_mining.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    ArrayList<MainListData> mDatas;
    NetworkService service;
    private CallbackManager callbackManager;
    private ImageView CustomloginButton;
    private ImageButton test;
    private EditText Username;
    private EditText Password;
    private String user;
    private String pass;
    private String F_user;
    private String F_id;
    View focusView = null;
    private ImageButton new_in;
    private ImageButton find_pass;

    private SessionCallback callback;

    //private TextView failedLoginMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);


        mDatas = new ArrayList<>();
        service = ApplicationController.getInstance().getNetworkService();

        Username = (EditText)findViewById(R.id.Username);
        Password = (EditText)findViewById(R.id.Password);
        //failedLoginMessage = (TextView)findViewById(R.id.failed_login);


        new_in = (ImageButton) findViewById(R.id.new_in);
        new_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(LoginActivity.this, MuicMining_Join.class);
                startActivity(newIntent);
            }
        });

        find_pass = (ImageButton) findViewById(R.id.find_pass);
        find_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent findIntent = new Intent(LoginActivity.this, MusicMining_FindPassword.class);
                startActivity(findIntent);
            }
        });


        test = (ImageButton) findViewById(R.id.Btn_in);
        test = (ImageButton) findViewById(R.id.Btn_in);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //failedLoginMessage.setText("");
                attemptLogin();

            }
        });







/*
기본 레트피트 코드

        Call<MainResult> getListData = service.getMainData();

        getListData.enqueue(new Callback<MainResult>() {
            @Override
            public void onResponse(Call<MainResult> call, Response<MainResult> response) {

                if(response.isSuccessful()){

                    System.out.println("cleadasdasd!!!dsadasd");
                }
            }
            @Override
            public void onFailure(Call<MainResult> call, Throwable t) {

                return;
            }
        });
*/
// 여기부터 페이스북..
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        callbackManager = CallbackManager.Factory.create();
/*
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());  // 사용자 정보 출력.

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr",error.toString());
            }
        });
*/

        CustomloginButton = (ImageView) findViewById(R.id.facebook_login);
        CustomloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginManager - 요청된 읽기 또는 게시 권한으로 로그인 절차를 시작합니다.
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile", "user_friends"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.e("onSuccess", "onSuccess");




                                Log.e("토큰",loginResult.getAccessToken().getToken());
                                Log.e("유저아이디",loginResult.getAccessToken().getUserId());
                                Log.e("퍼미션 리스트",loginResult.getAccessToken().getPermissions()+"");


                                GraphRequest request =GraphRequest.newMeRequest(loginResult.getAccessToken() ,
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {
                                                try {

                                                    Log.v("result",object.toString());



                                                    Log.v("result",object.get("name").toString());
                                                    Log.v("result",object.get("id").toString());

                                                    F_user = object.get("name").toString();
                                                    F_id = object.get("id").toString();

                                                    NetworkService service = getInterfaceService();

                                                    Call<MainResult> mService = service.getMainData(F_id,F_user, 0);

                                                    mService.enqueue(new Callback<MainResult>() {
                                                        @Override
                                                        public void onResponse(Call<MainResult> call, Response<MainResult> response) {
                                                            if(response.isSuccessful()) {
                                                                //Toast.makeText(LoginActivity.this, F_user+" 로그인 성공 " , Toast.LENGTH_LONG).show();
                                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                intent.putExtra("EMAIL", F_user);

                                                                startActivity(intent);

                                                                /*Intent intent = new Intent(LoginActivity.this, MainList.class);
                                                                startActivity(intent);*/
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<MainResult> call, Throwable t) {

                                                        }
                                                    });


                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel() {
                                Log.e("onCancel", "onCancel");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.e("onError", "onError " + exception.getLocalizedMessage());
                            }
                        });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //test2.redirectLoginActivity();
            }
        });
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();  // 세션 연결성공 시 redirectSignupActivity() 호출
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            Log.v("실패","");
            setContentView(R.layout.activity_main); // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴

    }

    protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void attemptLogin(){
        boolean mCancel = this.loginValidation();
        if (mCancel) {
            focusView.requestFocus();
        } else {
            loginProcessWithRetrofit(user, pass);
        }
    }
    private boolean loginValidation() {
        // Reset errors.
        Username.setError(null);
        Password.setError(null);
        // Store values at the time of the login attempt.
        user = Username.getText().toString();
        pass = Password.getText().toString();
        boolean cancel = false;
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(pass) && !isPasswordValid(pass)) {
            Password.setError(getString(R.string.error_invalid_password));
            focusView = Password;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(user)) {
            Username.setError(getString(R.string.error_field_required));
            focusView = Username;
            cancel = true;
        } else if (!isEmailValid(user)) {
            Username.setError(getString(R.string.error_invalid_email));
            focusView = Username;
            cancel = true;
        }
        return cancel;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private NetworkService getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.78.156.235:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final NetworkService mInterfaceService = retrofit.create(NetworkService.class);
        return mInterfaceService;
    }


    private void loginProcessWithRetrofit(final String user, String pass){

        NetworkService service = this.getInterfaceService();

        Call<MainResult> mService = service.getMainData(user, pass, 1);

        mService.enqueue(new Callback<MainResult>() {
            @Override
            public void onResponse(Call<MainResult> call, Response<MainResult> response) {

                if(response.isSuccessful()){

                    MainResult mLoginObject = response.body();

                    int returnedResponse = mLoginObject.err;

                    //Toast.makeText(LoginActivity.this, user+" 로그인 성공 " , Toast.LENGTH_LONG).show();

                    //showProgress(false);
                    if(returnedResponse == 0){
                        // redirect to Main Activity page
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("EMAIL", user);
                        startActivity(intent);

                        /*Intent intent = new Intent(LoginActivity.this, MainList.class);
                        intent.putExtra("EMAIL", user);
                        startActivity(intent);*/
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호가 틀렸습니다." , Toast.LENGTH_LONG).show();
                    }

                    if(returnedResponse == 1){
                        // use the registration button to register
                        Toast.makeText(LoginActivity.this,"에러",Toast.LENGTH_SHORT).show();
                        //failedLoginMessage.setText(getResources().getString(R.string.registration_message));
                        Password.requestFocus();
                    }
                }

            }

            @Override
            public void onFailure(Call<MainResult> call, Throwable t) {
                Log.i("myTag",t.toString());
                Toast.makeText(LoginActivity.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();

            }
        });
    }



}

