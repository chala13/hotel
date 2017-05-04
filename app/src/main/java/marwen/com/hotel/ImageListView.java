package marwen.com.hotel;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taher on 24/04/2016.
 */
public class ImageListView extends AppCompatActivity  {



ListView listView;

    public static final String BITMAP_ID = "BITMAP_ID";


    InputStream is = null;
    String line = null;
    String result = null;
    JSONObject jobj = null;
    List<SalleReunion> salles;
    JSONArray recherches = null;
        SallaAdapter  adapter;
    String cap;
    ListView listview;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.listsalle);
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            Intent intent = getIntent();
             cap= intent.getStringExtra("id");
        System.out.println(cap+"yosra");
        listview = (ListView)findViewById(R.id.list);
        salles = new ArrayList<SalleReunion>();
           select();



        }









    public void select()
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("capacite",cap));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.246/xampp/hotel/select_salle.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

        try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("pass 2", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }

        try
        {
            JSONObject json_data = new JSONObject(result);

            // String id = (json_data.getString("current"));
            // System.out.println("id"+id);
            SalleReunion list=null;



            recherches = json_data.getJSONArray("salles");





            for (int i = 0; i < recherches.length(); i++) {

                JSONObject c = recherches.getJSONObject(i);

                list=  new SalleReunion(c.optString("image"));
                System.out.println("yosrafrom");

                salles.add(list);

            }


            listview.setAdapter(new SallaAdapter(this,
                    R.layout.image_list_view, salles));
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }

}
