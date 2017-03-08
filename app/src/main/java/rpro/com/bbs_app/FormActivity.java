package rpro.com.bbs_app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class FormActivity extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    public FormDataset dataset;

    public FormFragment0 frag0;
    public FormFragment1 frag1;
    public FormFragment2 frag2;
    public FormFragment3 frag3;
    public FormFragment4 frag4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Intent i = getIntent();
        String jsonStr = i.getStringExtra("retrievedJSON");

        dataset = new FormDataset();

        try {
            JSONArray jsonarr = new JSONArray(jsonStr);
            JSONObject jsonobj = jsonarr.getJSONObject(0);
            Log.d("FormActivity JSON",jsonobj.toString());
            dataset.setNama(jsonobj.getString("nama"));
            dataset.setEm_number(jsonobj.getString("employee_number"));
            dataset.setDepartemen(jsonobj.getString("departemen"));
        } catch (Exception e){
            e.printStackTrace();
        }

        frag0 = new FormFragment0();
        frag1 = new FormFragment1();
        frag2 = new FormFragment2();
        frag3 = new FormFragment3();
        frag4 = new FormFragment4();


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.form_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setEnabled(false);

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0 || mPager.getCurrentItem() == 4) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0 :
                    return frag0;
                case 1 :
                    return frag1;
                case 2 :
                    return frag2;
                case 3 :
                    return frag3;
                case 4 :
                    return frag4;
                default:
                    return frag0;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }


}
