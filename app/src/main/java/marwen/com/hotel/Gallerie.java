package marwen.com.hotel;

/**
 * Created by Taher on 26/03/2016.
 */
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class Gallerie extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    InputStream is = null;
    String line = null;
    String result = null;
    JSONObject jobj = null;

    String nbre;
    AlertDialogManager alert = new AlertDialogManager();


    // TableLayout t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallerie);
        initInstances();
        //new RetreiveData().execute();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        select();

    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(Gallerie.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_1:
                        startActivity(new Intent(Gallerie.this, MainActivity.class));
                        break;
                    case R.id.navigation_item_3:
                        startActivity(new Intent(Gallerie.this, Chambres.class));
                        break;
                    case R.id.navigation_item_11:
                        startActivity(new Intent(Gallerie.this, Reunions.class));
                        break;
                    case R.id.navigation_item_4:
                        startActivity(new Intent(Gallerie.this, Gallerie.class));
                        break;
                    case R.id.navigation_item_5:
                        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?&daddr=%f,%f (%s)", 36.905378f, 10.312454f, "Where the party is at");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        try
                        {
                            startActivity(intent);
                        }
                        catch(ActivityNotFoundException ex)
                        {
                            try
                            {
                                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(unrestrictedIntent);
                            }
                            catch(ActivityNotFoundException innerEx)
                            {
                                Toast.makeText(Gallerie.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                            }
                        }
                        break;
                    case R.id.navigation_item_2:
                        startActivity(new Intent(Gallerie.this, Gallerie.class));
                        break;
                    case R.id.navigation_item_6:
                        startActivity(new Intent(Gallerie.this, Gallerie.class));
                        break;
                }
                return false;
            }
        });

    }

    public void select() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("id", "1"));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.1.7/xampp/hotel/select_salle.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("pass 2", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());
        }

        try {
            JSONObject json_data = new JSONObject(result);
            String nom = (json_data.getString("nom"));
            System.out.println(nom +"name");
            Toast.makeText(getBaseContext(), "Name : " + nom,
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Fail 3", e.toString());
        }
    }




    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }}
 /*class RetreiveData extends  AsyncTask<String, String, String>{

        @Override
        protected String  doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            jobj = clientServerInterface.makeHttpRequest("http://192.168.1.7/xampp/hotel/select_salle.php");
            System.out.println("sucess");
            try {
             nbre = jobj.getString("0");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return nbre;
        }
    protected void onPostExecute(String nbre){

        String n = nbre+"";
    }


 }
*/


