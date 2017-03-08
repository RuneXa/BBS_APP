package rpro.com.bbs_app;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Richie on 05/01/2017.
 */
public class FormDataset {
    String tanggal;
    String bulan;
    String em_number;
    String nama;
    String departemen;
    String obs_area;
    int index_obs_area;
    String obs_category;
    int index_obs_category;
    String observation;
    String risk_category;
    int index_risk_category;
    String fullDate;


    public FormDataset() {
        tanggal = "";
        bulan = "";
        em_number = "";
        nama = "";
        departemen = "";
        obs_area = "";
        obs_category = "";
        observation = "";
        risk_category = "";
        fullDate = "";
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getEm_number() {
        return em_number;
    }

    public void setEm_number(String em_number) {
        this.em_number = em_number;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDepartemen() {
        return departemen;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }

    public String getObs_area() {
        return obs_area;
    }

    public void setObs_area(String obs_area) {
        this.obs_area = obs_area;
    }

    public String getObs_category() {
        return obs_category;
    }

    public void setObs_category(String obs_category) {
        this.obs_category = obs_category;
    }

    public String getRisk_category() {
        return risk_category;
    }

    public void setRisk_category(String risk_category) {
        this.risk_category = risk_category;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public int getIndex_obs_area() {
        return index_obs_area;
    }

    public void setIndex_obs_area(int index_obs_area) {
        this.index_obs_area = index_obs_area;
    }

    public int getIndex_obs_category() {
        return index_obs_category;
    }

    public void setIndex_obs_category(int index_obs_category) {
        this.index_obs_category = index_obs_category;
    }

    public int getIndex_risk_category() {
        return index_risk_category;
    }

    public void setIndex_risk_category(int index_risk_category) {
        this.index_risk_category = index_risk_category;
    }

    public boolean isAnyEmpty(){
        Log.d("Form Data Set",getTanggal());
        Log.d("Form Data Set",getBulan());
        Log.d("Form Data Set",getEm_number());
        Log.d("Form Data Set",getNama());
        Log.d("Form Data Set",getDepartemen());
        Log.d("Form Data Set",getObs_area());
        Log.d("Form Data Set",getObs_category());
        Log.d("Form Data Set",getObservation());
        Log.d("Form Data Set",getRisk_category());
        Log.d("Form Data Set",getFullDate());
        if(tanggal.compareTo("") == 0 || bulan.compareTo("") == 0 || em_number.compareTo("") == 0 || nama.compareTo("") == 0 ||
                departemen.compareTo("") == 0 || obs_area.compareTo("") == 0 || obs_category.compareTo("") == 0 ||
                observation.compareTo("") == 0 || risk_category.compareTo("") == 0 || fullDate.compareTo("") == 0){
            return true;
        } else {
            return false;
        }
    }
}
