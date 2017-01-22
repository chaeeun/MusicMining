package music_m.music_mining.make_id;

/**
 * Created by kki53 on 2016-12-31.
 */

public class LoginRequestObject {
    String user_id;
    String passwd;
    String name;
    boolean gender;
    String birth;

    public LoginRequestObject(String user_id, String password, String name, boolean gender, String birth) {
        this.user_id = user_id;
        this.passwd = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }
}
