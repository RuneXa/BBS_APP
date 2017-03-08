package rpro.com.bbs_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormFragment2 extends Fragment{

    FormActivity formActivity;
    ArrayList<String> arrRiskCategory;
    LinearLayout lll;
    LinearLayout llr;

    EditText editObs;
    String risk_category;
    String observation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_form2, container, false);

        formActivity = (FormActivity) getActivity();
        String[] resArrRiskCategory = getResources().getStringArray(R.array.risk_category);
        List<String> arrListRiskCategory = Arrays.asList(resArrRiskCategory);
        arrRiskCategory = new ArrayList<String>(arrListRiskCategory);
        lll = (LinearLayout) rootView.findViewById(R.id.form2_leftSideCheckBox);
        llr = (LinearLayout) rootView.findViewById(R.id.form2_rightSideCheckBox);
        editObs = (EditText) rootView.findViewById(R.id.form_edit_observation);


        int midPoint = arrRiskCategory.size() / 2;
        for (String s: arrRiskCategory) {
            CheckBox cb = new CheckBox(getContext());
            cb.setText(s);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for(int i = 0;i<lll.getChildCount();i++){
                        CompoundButton c = (CompoundButton) lll.getChildAt(i);
                        if(!c.isChecked()) {
                            c.setEnabled(!c.isEnabled());
                        }
                    }

                    for(int i = 0;i<llr.getChildCount();i++){
                        CompoundButton c = (CompoundButton) llr.getChildAt(i);
                        if(!c.isChecked()) {
                            c.setEnabled(!c.isEnabled());
                        }
                    }

                    buttonView.setEnabled(true);
                }
            });
            if(midPoint > 0){
                lll.addView(cb,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                llr.addView(cb,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            midPoint--;
        }

        rootView.findViewById(R.id.form_button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                ViewPager pager = (ViewPager) v.getRootView().findViewById(R.id.form_pager);
                risk_category = get_checkedString();
                observation = editObs.getText().toString();
                formActivity.dataset.setRisk_category(risk_category);
                if(arrRiskCategory.contains(risk_category)){
                    formActivity.dataset.setIndex_risk_category(arrRiskCategory.indexOf(risk_category)+1);
                } else {
                    formActivity.dataset.setIndex_risk_category(arrRiskCategory.size()+1);
                }
                formActivity.dataset.setObservation(observation);

                if(!formActivity.dataset.isAnyEmpty() && formActivity.dataset.getIndex_obs_category() != 2){
                    formActivity.frag3.populateFields();
                    pager.setCurrentItem(3);
                } else if(risk_category.compareTo("-") == 0 && formActivity.dataset.getIndex_obs_category() != 2){
                    Toast.makeText(getContext(),"Data Belum Lengkap",Toast.LENGTH_SHORT).show();
                }
            }
        });

        rootView.findViewById(R.id.form_button_prev).setOnClickListener(new View.OnClickListener() {
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

    public void disable_checkbox(boolean b){
        for(int i = 0;i<lll.getChildCount();i++){
            CompoundButton c = (CompoundButton) lll.getChildAt(i);
            c.setEnabled(!b);
        }

        for(int i = 0;i<llr.getChildCount();i++){
            CompoundButton c = (CompoundButton) llr.getChildAt(i);
            c.setEnabled(!b);
        }
    }

    String get_checkedString(){
        for(int i = 0;i<lll.getChildCount();i++){
            CompoundButton c = (CompoundButton) lll.getChildAt(i);
            if(c.isChecked()){
                return c.getText().toString();
            }
        }

        for(int i = 0;i<llr.getChildCount();i++){
            CompoundButton c = (CompoundButton) llr.getChildAt(i);
            if(c.isChecked()){
                return c.getText().toString();
            }
        }

        return "-";
    }

}
