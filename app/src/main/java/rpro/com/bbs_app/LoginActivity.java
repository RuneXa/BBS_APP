package rpro.com.bbs_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText id_editText;
    EditText pass_editText;
    Button login_button;
    Button prev_button;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id_editText = (EditText) findViewById(R.id.login_edit_id);
        pass_editText = (EditText) findViewById(R.id.login_edit_pass);
        login_button = (Button) findViewById(R.id.login_button_login);
        prev_button = (Button) findViewById(R.id.login_button_prev);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = id_editText.getText().toString();
                String pass = pass_editText.getText().toString();
                if(id.compareTo("") == 0){
                    id_editText.setError("Wajib di Isi");
                } else if(pass.compareTo("") == 0){
                    pass_editText.setError("Wajib di Isi");
                } else {
                    verifyIDPass verif = new verifyIDPass();
                    verif.execute(id,pass);
                }
            }
        });

        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        c = this;
    }

    private class verifyIDPass extends AsyncTask<String, Void, String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(c, "Please Wait...",null,true,true);
        }

        @Override
        protected String doInBackground(String... params) {

            JSONObject jsonToPost = new JSONObject();
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            String response_string ="";
            try {
                jsonToPost.put("id",params[0]);
                jsonToPost.put("pass",params[1]);

                URL url = new URL(DatabaseParams.VERIFY_KARYAWAN_URL);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(jsonToPost.toString().getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(jsonToPost.toString().getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();

                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = null;
                bufferedReader = new BufferedReader(new InputStreamReader(is));

                String is_string;
                while((is_string = bufferedReader.readLine())!= null){
                    sb.append(is_string+"\n");
                }

                response_string = sb.toString().trim();
                Log.d("Response : ", response_string);
            }
            catch(Exception e){
                e.printStackTrace();
                return "Error";
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response_string;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            loading.dismiss();

            JSONObject returnVal;
            String code = "-1";
            String codeVal = "";
            try {
                returnVal = new JSONObject(s);
                code = returnVal.getString("returnCode");
                codeVal = returnVal.getString("returnValue");
            } catch (Exception e){
                e.printStackTrace();
            }

            switch(code){
                case "0" :
                    Toast.makeText(getBaseContext(),"Employee Number atau Password Salah",Toast.LENGTH_LONG).show();
                    Log.d("Error","Pass Salah");
                    break;
                case "1" :
                    Intent i = new Intent(c,FormActivity.class);
                    i.putExtra("retrievedJSON",codeVal);
                    startActivity(i);
                    finish();
                    break;
                case "2" :
                    Toast.makeText(getBaseContext(),"Akun Belum Pernah Diaktifasi",Toast.LENGTH_LONG).show();
                    Log.d("Error","Akun Belum Aktif");
                    break;
                case "3" :
                    Toast.makeText(getBaseContext(),"Data Employee Tidak Ditemukan",Toast.LENGTH_LONG).show();
                    Log.d("Error","Not Found");
                    break;
                case "Error" :
                    Toast.makeText(getBaseContext(),"Terjadi Kesalahan, Periksa Kembali Koneksi Internet Anda",Toast.LENGTH_SHORT).show();
                    Log.d("Error","No Connection");
                    break;
                default :
                    Toast.makeText(getBaseContext(),"Terjadi Kesalahan",Toast.LENGTH_SHORT).show();
                    Log.d("Error","Error");
                    break;
            }
        }

    }
}
