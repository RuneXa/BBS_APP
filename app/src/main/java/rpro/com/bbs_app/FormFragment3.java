package rpro.com.bbs_app;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FormFragment3 extends Fragment {

    FormActivity formActivity;
    FormDataset formDataset;
    TextView namaTextView;
    TextView deptTextView;
    TextView obsAreaTextView;
    TextView obsDateTextView;
    TextView obsCategoryTextView;
    TextView observationTextView;
    TextView riskCategoryTextView;
    Button sendButton, prevButton;
    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_form3, container, false);

        formActivity = (FormActivity) getActivity();
        formDataset = formActivity.dataset;

        namaTextView = (TextView) rootView.findViewById(R.id.form_confirm_nama);
        deptTextView = (TextView) rootView.findViewById(R.id.form_confirm_dept);
        obsAreaTextView = (TextView) rootView.findViewById(R.id.form_confirm_obsArea);
        obsDateTextView = (TextView) rootView.findViewById(R.id.form_confirm_obsDate);
        obsCategoryTextView = (TextView) rootView.findViewById(R.id.form_confirm_obsCategory);
        observationTextView = (TextView) rootView.findViewById(R.id.form_confirm_observation);
        riskCategoryTextView = (TextView) rootView.findViewById(R.id.form_confirm_risk);
        sendButton = (Button) rootView.findViewById(R.id.form_button_send);
        prevButton = (Button) rootView.findViewById(R.id.form_button_prev);

        pager = (ViewPager) container.findViewById(R.id.form_pager);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonDataToSend = new JSONObject();
                try{
                    jsonDataToSend.put("tanggal",formActivity.dataset.getTanggal());
                    jsonDataToSend.put("bulan",formActivity.dataset.getBulan());
                    jsonDataToSend.put("employee_number",formActivity.dataset.getEm_number());
                    jsonDataToSend.put("nama",formActivity.dataset.getNama());
                    jsonDataToSend.put("departemen",formActivity.dataset.getDepartemen());
                    jsonDataToSend.put("area_observed",formActivity.dataset.getIndex_obs_area());
                    jsonDataToSend.put("observation",formActivity.dataset.getObservation());
                    jsonDataToSend.put("observation_category",formActivity.dataset.getIndex_obs_category());
                    jsonDataToSend.put("risk_category",formActivity.dataset.getIndex_risk_category());
                    new dbOperation().execute(jsonDataToSend);
                } catch (Exception e){
                    e.printStackTrace();
                }

                Toast.makeText(getContext(), "Mengirim Data...", Toast.LENGTH_SHORT).show();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        });

        return rootView;
    }

    public void populateFields(){
        String nodannama = formDataset.getEm_number() + " - " + formDataset.getNama();
        namaTextView.setText(nodannama);
        deptTextView.setText(formDataset.getDepartemen());
        obsAreaTextView.setText(formDataset.getObs_area());
        obsDateTextView.setText(formDataset.getFullDate());
        obsCategoryTextView.setText(formDataset.getObs_category());
        observationTextView.setText(formDataset.getObservation());
        riskCategoryTextView.setText(formDataset.getRisk_category());
    }

    private class dbOperation extends AsyncTask<JSONObject, JSONObject, Boolean> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getContext(), "Please Wait...",null,true,true);
        }

        @Override
        protected Boolean doInBackground(JSONObject... params) {

            JSONObject json = params[0];
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            String response_string ="";
            try {
                URL url = new URL(DatabaseParams.SEND_BBS_REPORT_URL);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(json.toString().getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(json.toString().getBytes());
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
                return false;
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
            return true;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            if(b){
                pager.setCurrentItem(pager.getCurrentItem()+1);
            } else {
                Toast.makeText(getContext(),"Terjadi Kesalahan, Periksa Kembali Koneksi Internet Anda",Toast.LENGTH_SHORT).show();
                Log.d("Error :","Can't Connect");
            }
            loading.dismiss();
        }

    }

}
