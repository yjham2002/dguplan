package util;

import android.os.AsyncTask;
import android.util.Log;

import com.planner.dgu.dguplan.ClassInfo;
import com.planner.dgu.dguplan.URLS;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import weekview.WeekViewEvent;

public class userDTO extends AsyncTask<Void, Void, String> {

    public static String conn = "시간표", userId = "", userPw = "", userName = "";

    public String content ="";
    public Document doc;
    public boolean isConnected = false;

    private SFCallback preprocessCallback;
    private SFCallback connCallback;
    private SFCallback successCallback;
    private SFCallback failCallback;

    public List<List<ClassInfo>> days = new ArrayList<>();

    private static final int TIMEOUT = 10000;

    public userDTO(SFCallback preprocessCallback, SFCallback successCallback, SFCallback failCallback){
        this.preprocessCallback = preprocessCallback;
        this.successCallback = successCallback;
        this.failCallback = failCallback;
    }
    public userDTO(SFCallback successCallback){
        this.successCallback = successCallback;
    }

    public userDTO(SFCallback successCallback, SFCallback failCallback, SFCallback connCallback, String id, String pw){
        this.successCallback = successCallback;
        this.failCallback = failCallback;
        this.connCallback = connCallback;
        this.userId = id;
        this.userPw = pw;
    }

    @Override
    protected String doInBackground(Void... params) {
        isConnected = true;
        if (preprocessCallback != null){
            preprocessCallback.callback();
        }
        try {
            Connection.Response res = Jsoup.connect(URLS.URL_LOGIN)
                    .followRedirects(true)
                    .data("userDTO.userId", userId)
                    .data("userDTO.password", userPw)
                    .method(Connection.Method.POST)
                    .timeout(TIMEOUT)
                    .execute();
            doc = Jsoup.connect(URLS.URL_TABLE_TEST)
                    .followRedirects(true)
                    .cookies(res.cookies())
                    .method(Connection.Method.POST)
                    .timeout(TIMEOUT)
                    .post();
            content = doc.toString();
        }catch(IOException e){ isConnected = false; }
        return content;
    }

    @Override
    protected void onPostExecute(String result) {
        if (isConnected){
            Element userConnection = doc.select("SPAN[class = selected]").first();
            if(userConnection == null) {
                connCallback.callback();
                return;
            }
            conn = userConnection.text();
            Element table = doc.select("TABLE[class = bbs-table01]").first();
            int trCnt = 0;
            for(Element row : table.select("tr")) {
                List<ClassInfo> temp = new ArrayList<>();
                days.add(temp);
                int tdCnt = 0;
                String rawtime = "";
                for (Element td : row.children()) {
                    if(trCnt == 0) temp.add(new ClassInfo(td.text(), ClassInfo.NULLCLASS, ClassInfo.NULLCLASS, ClassInfo.NULLPTR));
                    else{
                        if(tdCnt == 0) {
                            rawtime = td.text();
                            temp.add(new ClassInfo(td.text(), ClassInfo.NULLCLASS, td.text(), ClassInfo.NULLPTR));
                        }else{
                            if(td.text().trim().length() <= 3) temp.add(new ClassInfo(ClassInfo.NULLCLASS, ClassInfo.NULLCLASS, rawtime, ClassInfo.NULLPTR));
                            else{
                                temp.add(new ClassInfo(td.text().substring(0, td.text().indexOf('(') - 1),
                                        td.text().substring(td.text().indexOf('('), td.text().length() - 1), rawtime, tdCnt, true));
                            }
                        }
                    }
                    tdCnt++;
                }
                trCnt++;
            }
            successCallback.callback();
        } else {
            failCallback.callback();
        }
    }
}