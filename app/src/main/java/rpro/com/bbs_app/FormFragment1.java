package rpro.com.bbs_app;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.util.DebugUtils;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FormFragment1 extends Fragment {

    FormActivity formActivity;

    //Not Used Since Data already set from Login
    //ArrayList<String> arrNama;
    //ArrayList<EmployeeData> arrEmployee;

    ArrayList<String> arrObsArea;
    ArrayList<String> arrObsCategory;

    String nama;
    String emNumber;
    String dept;
    String observedArea;
    String observedFullDate;
    String observedDate;
    String observedMonth;
    String observationCategory;

    TextView namaTextView;
    TextView deptTextView;
    FormCustomEditText obsAreaTextView;
    FormCustomEditText obsCategoryTextView;
    TextView dateTextView;
    Button nextButton;
    Button prevButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_form1, container, false);

        formActivity = (FormActivity) getActivity();
        //arrNama = new ArrayList<>();
        //arrEmployee = new ArrayList<>();
        arrObsArea = new ArrayList<>();
        arrObsCategory = new ArrayList<>();

        List<String> arrListObsArea = Arrays.asList(getResources().getStringArray(R.array.area_observed));
        arrObsArea = new ArrayList<String>(arrListObsArea);
        List<String> arrListObsCategory = Arrays.asList(getResources().getStringArray(R.array.obs_category));
        arrObsCategory = new ArrayList<String>(arrListObsCategory);

        namaTextView = (TextView) rootView.findViewById(R.id.form_edit_nama);
        deptTextView = (TextView) rootView.findViewById(R.id.form_edit_dept);
        obsAreaTextView = (FormCustomEditText) rootView.findViewById(R.id.form_edit_obsArea);
        obsCategoryTextView = (FormCustomEditText) rootView.findViewById(R.id.form_edit_obsCategory);
        dateTextView = (TextView) rootView.findViewById(R.id.form_edit_obsDate);

        String emNama = formActivity.dataset.getEm_number() + " - " + formActivity.dataset.getNama();
        namaTextView.setText(emNama);
        deptTextView.setText(formActivity.dataset.getDepartemen());
        obsAreaTextView.setText(formActivity.dataset.getObs_area());
        obsCategoryTextView.setText(formActivity.dataset.getObs_category());
        dateTextView.setText(formActivity.dataset.getFullDate());

        //dbOperation dbOp = new dbOperation();
        //dbOp.execute();

        //Not Used, Since The Field Can't be Changed Anyways
        /*
        namaTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(arrNama.contains(namaTextView.getText().toString())){
                    int i = arrNama.indexOf(namaTextView.getText().toString());
                    deptTextView.setText(arrEmployee.get(i).dept);
                    nama = arrEmployee.get(i).name;
                    dept = arrEmployee.get(i).dept;
                    emNumber = arrEmployee.get(i).emNumber;
                } else {
                    deptTextView.setText("");
                    nama = "";
                    dept = "";
                    emNumber = "";
                }
            }
        });
        */

        ArrayAdapter<String> obsAreaAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,arrObsArea);
        obsAreaTextView.setAdapter(obsAreaAdapter);

        ArrayAdapter<String> obsCategoryAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,arrObsCategory);
        obsCategoryTextView.setAdapter(obsCategoryAdapter);

        FormDateDialog dateDialog = new FormDateDialog();
        dateDialog.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar setCalendar = Calendar.getInstance();
                setCalendar.set(year,month,dayOfMonth);

                Calendar nowCalendar = Calendar.getInstance();
                if(setCalendar.getTimeInMillis() <= nowCalendar.getTimeInMillis()) {
                    observedFullDate = String.valueOf(dayOfMonth) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(year);
                    observedDate = String.valueOf(dayOfMonth);
                    observedMonth = String.valueOf(month + 1);
                    dateTextView.setText(observedFullDate);
                } else {
                    observedFullDate = "";
                    observedDate = "";
                    observedMonth = "";
                    dateTextView.setText(observedFullDate);
                    Toast.makeText(getContext(),"Tanggal Tidak Valid",Toast.LENGTH_SHORT).show();
                }
            }
        });

        final DialogFragment dateDialogFragment = dateDialog;

        dateTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    dateDialogFragment.show(getFragmentManager(),"DatePicker");
                }
            }
        });

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialogFragment.show(getFragmentManager(),"DatePicker");
            }
        });

        nextButton = (Button) rootView.findViewById(R.id.form_button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                observedArea = obsAreaTextView.getText().toString();
                observationCategory = obsCategoryTextView.getText().toString();

                if(namaTextView.getText().toString().compareTo("") == 0 ||
                        deptTextView.getText().toString().compareTo("") == 0 ||
                        obsAreaTextView.getText().toString().compareTo("") == 0 ||
                        obsCategoryTextView.getText().toString().compareTo("") == 0 ||
                        observedFullDate.compareTo("") == 0){
                    Toast.makeText(getContext(),getResources().getString(R.string.form_notFilled),Toast.LENGTH_SHORT).show();
                } else {
                    //Not Used Since the data already set anyways
                    //formActivity.dataset.setNama(nama);
                    //formActivity.dataset.setEm_number(emNumber);
                    //formActivity.dataset.setDepartemen(dept);
                    formActivity.dataset.setObs_area(observedArea);
                    formActivity.dataset.setIndex_obs_area(arrObsArea.indexOf(observedArea)+1);
                    formActivity.dataset.setObs_category(observationCategory);
                    formActivity.dataset.setIndex_obs_category(arrObsCategory.indexOf(observationCategory)+1);
                    formActivity.dataset.setTanggal(observedDate);
                    formActivity.dataset.setBulan(observedMonth);
                    formActivity.dataset.setFullDate(observedFullDate);
                    if(formActivity.dataset.getIndex_obs_category() == 3){
                        formActivity.frag2.disable_checkbox(true);
                    } else {
                        formActivity.frag2.disable_checkbox(false);
                    }
                    ViewPager pager = (ViewPager) v.getRootView().findViewById(R.id.form_pager);
                    pager.setCurrentItem(2);
                }
            }
        });

        prevButton = (Button) rootView.findViewById(R.id.form_button_prev);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                ViewPager pager = (ViewPager) v.getRootView().findViewById(R.id.form_pager);
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        });

        return rootView;
    }

    //Not Used Since Data Already Set From Login
    /*
    private class dbOperation extends AsyncTask<Void, Void, Boolean> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getContext(), "Please Wait...",null,true,true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String retrievedJSONString;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(DatabaseParams.RETRIEVE_DATA_KARYAWAN_URL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String json;
                while((json = bufferedReader.readLine())!= null){
                    sb.append(json+"\n");
                }

                retrievedJSONString = sb.toString().trim();

            }catch(Exception e){
                e.printStackTrace();
                return false;
            }

            Log.d("Retrieved :", retrievedJSONString);

            try {
                JSONObject retrievedObj = new JSONObject(retrievedJSONString);
                JSONArray karyawan = retrievedObj.getJSONArray("result");
                for (int i = 0; i < karyawan.length(); i++) {
                    JSONObject o = karyawan.getJSONObject(i);
                    Log.d("Object : ", o.toString());
                    arrEmployee.add(new EmployeeData(o.getString("nama"),o.getString("employee_number"),o.getString("departemen")));
                    arrNama.add(o.getString("employee_number") + " - " + o.getString("nama"));
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            if(b) {
                ArrayAdapter<String> namaAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrNama);
                namaTextView.setAdapter(namaAdapter);
            } else {
                Toast.makeText(getContext(),"Terjadi Kesalahan, Periksa Kembali Koneksi Internet Anda",Toast.LENGTH_SHORT).show();
                getActivity().finish();
                Log.d("Error","Error No Connection");
            }

            namaTextView.setText(formActivity.dataset.getNama());
            deptTextView.setText(formActivity.dataset.getDepartemen());
            obsAreaTextView.setText(formActivity.dataset.getObs_area());
            obsCategoryTextView.setText(formActivity.dataset.getObs_category());
            dateTextView.setText(formActivity.dataset.getFullDate());

            loading.dismiss();
        }

    }
    */

}
