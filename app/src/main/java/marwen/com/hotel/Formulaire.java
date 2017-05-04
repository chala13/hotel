package marwen.com.hotel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


public class Formulaire extends AppCompatActivity {
    private ImageButton ib, ib2;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int mButtonClicked;
    private Spinner spinner1;
    private EditText et,and;
    private Button btn;
    EditText nom, prenom,email,tel,cin,checkin,checkout;
    String n,p,e,t,c,r,in,out,sp,cap;
    RadioGroup rd;
    RadioButton  radioSexButton;
    InputStream is = null;
    String line = null;
    String result = null;
    JSONObject jobj = null;
    int code;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire1);
        // mDateButton = (Button) findViewById(R.id.date_button);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ib = (ImageButton) findViewById(R.id.imageButton1);
        ib2 = (ImageButton) findViewById(R.id.imageButton2);
        btn =(Button)findViewById(R.id.register_action_id);
        nom =(EditText)findViewById(R.id.name_edt);
        prenom =(EditText)findViewById(R.id.last_name_edt);
        email=(EditText)findViewById(R.id.email_edt);
        tel =(EditText)findViewById(R.id.tele_edt);
        cin =(EditText)findViewById(R.id.cin_edt);
        rd =(RadioGroup)findViewById(R.id.radioSex);
checkin =(EditText)findViewById(R.id.checkIn);
        checkout=(EditText)findViewById(R.id.checkOut);


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Petite (10-50)");
        list.add("Moyenne(50-300)");
        list.add("Grande(300-800)");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner,list);

        spinner1.setAdapter(adapter);




        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText) findViewById(R.id.checkIn);
        and = (EditText) findViewById(R.id.checkOut);
       ib.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v) {
                showDialog(0);
               mButtonClicked =0;

            }
        });
        ib2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog(0);
                mButtonClicked=1;
            }
        });
        addListenerOnButton();
    }



    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener1, year, month, day);

    }




    public void addListenerOnSpinnerItemSelection(){

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            if(mButtonClicked == 0)
                et.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                        + selectedYear);
            else if(mButtonClicked == 1)
                and.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                        + selectedYear);}


    };
    public void addListenerOnButton() {



        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup


                Toast.makeText(Formulaire.this,
                        "On Button Click : " +
                                "\n" + String.valueOf(spinner1.getSelectedItem()),
                        Toast.LENGTH_LONG).show();

                n = nom.getText().toString();
                p = prenom.getText().toString();
                e = email.getText().toString();
                t = tel.getText().toString();
                c = cin.getText().toString();
                if ((n.equals("")) || (p.equals("")) || e.equals("") || t.equals("") || c.equals("")) {
                    Toast.makeText(Formulaire.this,
                            "Remplir toutes les champs" ,
                            Toast.LENGTH_LONG).show();
                } else {
                    int selectedId = rd.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) findViewById(selectedId);
                    r = radioSexButton.getText().toString();



                    in = checkin.getText().toString();
                    out = checkout.getText().toString();
                    sp = String.valueOf(spinner1.getSelectedItem());
                    if (sp == "Petite (10-50)") {
                        cap = "50";
                    } else if (sp == "Moyenne(50-300)") {
                        cap = "300";

                    } else {
                        cap = "800";

                    }
                    insert();
                    select();

                    Intent i = new Intent(Formulaire.this, ImageListView.class);
                    i.putExtra("id", cap);

                    startActivity(i);
                }
            }
        });

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
            String nomnom=(json_data.getString("nom"));
            Toast.makeText(getBaseContext(), "Name : "+nomnom,
                    Toast.LENGTH_SHORT).show();
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }
    public void insert()
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("nom",n));
        nameValuePairs.add(new BasicNameValuePair("prenom",p));
        nameValuePairs.add(new BasicNameValuePair("genre",r));
        nameValuePairs.add(new BasicNameValuePair("email",e));
        nameValuePairs.add(new BasicNameValuePair("tel",t));
        nameValuePairs.add(new BasicNameValuePair("cin",c));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.246/xampp/hotel/insertclient.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            System.out.println("connection success");
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
            code=(json_data.getInt("code"));

            if(code==1)
            {
                Toast.makeText(getBaseContext(), "Inserted Successfully",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext(), "Sorry, Try Again",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
    }

}
