package rpro.com.bbs_app;

/**
 * Created by Richie on 10/01/2017.
 */
final public class DatabaseParams {
    static public String SERVER_URL = "http://bbspc.esy.es"; //TODO : ubah ke URL hosting
    static public String RETRIEVE_DATA_KARYAWAN_URL = SERVER_URL+"/bbs_app_retrieve_data_karyawan.php";
    static public String SEND_BBS_REPORT_URL = SERVER_URL+"/bbs_app_send_bbs_report.php";
    static public String ACTIVATE_KARYAWAN_URL = SERVER_URL+"/bbs_app_aktifasi_idPass.php";
    static public String VERIFY_KARYAWAN_URL = SERVER_URL+"/bbs_app_verify_idPass.php";
}
