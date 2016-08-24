package util;

import android.os.AsyncTask;
import com.planner.dgu.dguplan.ClassInfo;
import com.planner.dgu.dguplan.URLS;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import weekview.WeekViewEvent;

public class userDTO extends AsyncTask<Void, Void, String> {

    private SFCallback preprocessCallback;
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

    public String content ="";
    public Document doc;

    @Override
    protected String doInBackground(Void... params) {
        if (preprocessCallback != null){
            preprocessCallback.callback();
        }
        try {
            Connection.Response res = Jsoup.connect(URLS.URL_LOGIN)
                    .followRedirects(true)
                    .data("userDTO.userId", "2014112021")
                    .data("userDTO.password", "gpswpf12!")
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
        }catch(IOException e){ e.printStackTrace(); }
        return content;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null){
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