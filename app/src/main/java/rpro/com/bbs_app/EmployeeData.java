package rpro.com.bbs_app;

/**
 * Created by Richie on 05/01/2017.
 */
public class EmployeeData {
    String name;
    String emNumber;
    String dept;

    public EmployeeData(String name, String emNumber, String dept) {
        this.name = name;
        this.emNumber = emNumber;
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmNumber() {
        return emNumber;
    }

    public void setEmNumber(String emNumber) {
        this.emNumber = emNumber;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
