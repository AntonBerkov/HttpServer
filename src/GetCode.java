import java.io.InputStream;

/**
 * Created by Anton on 15.05.2018.
 */
public class GetCode {
    public int getCode(int code, InputStream strm, String a, String url){

        code = (strm != null) ? 200 : 404;

        if(url.length()== 0){code = 411;}
        else {
            if (url.contains("forbidden")) {
                code = 403;
            } else {
                if (url.contains("unathorized")) {
                    code = 401;
                }
                else {
                    if(url.contains("illegal")){code = 451;}
                    else {
                        if (a.contains("PUT")) {code = 405;}
                        else {
                            if (a.contains("COPY")) {code = 501;}
                            else {
                                if (url.length() != 16) {
                                    code = 400;
                                }
                            }
                        }
                    }

                }
            }
        }
        return code;
    }
}
