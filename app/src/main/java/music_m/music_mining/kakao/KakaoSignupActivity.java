package music_m.music_mining.kakao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import music_m.music_mining.login.LoginActivity;
import music_m.music_mining.model.MainResult;
import music_m.music_mining.movie.MainActivity;
import music_m.music_mining.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jongjookim on 2017. 1. 6..
 */

public class KakaoSignupActivity extends Activity {

    String kakaoID;
    String kakaoNickname;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {} // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
                Logger.d("UserProfile : " + userProfile);
                Log.e("id,name",""+kakaoID+kakaoNickname);
                redirectMainActivity(); // 로그인 성공시 MainActivity로
            }
        });
    }

    private void redirectMainActivity() {
//        Intent intent = new Intent(this,MainActivity.class);
//        intent.putExtra("EMAIL", kakaoID);
//        startActivity(intent);
//        startActivity(new Intent(this, MainActivity.class));
        kakaoCallService(kakaoID,kakaoNickname);

        finish();
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private NetworkService getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.78.156.235:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final NetworkService mInterfaceService = retrofit.create(NetworkService.class);
        return mInterfaceService;
    }


    private void kakaoCallService(final String id, String nick){
        NetworkService service = getInterfaceService();

        Call<MainResult> aService = service.getMainData(id,nick, 0);

        aService.enqueue(new Callback<MainResult>() {
            @Override
            public void onResponse(Call<MainResult> call, Response<MainResult> response) {
                if(response.isSuccessful()) {
                    //Toast.makeText(LoginActivity.this, F_user+" 로그인 성공 " , Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("EMAIL", F_user);
                    Intent intent = new Intent(KakaoSignupActivity.this,MainActivity.class);
                    intent.putExtra("EMAIL", id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                                                                /*Intent intent = new Intent(LoginActivity.this, MainList.class);
                                                                startActivity(intent);*/
                }
            }

            @Override
            public void onFailure(Call<MainResult> call, Throwable t) {

            }
        });
    }

}