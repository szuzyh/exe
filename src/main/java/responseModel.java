/**
 * Created by Leo on 2016/12/21.
 */
public class responseModel {



    private String personName;
    private String sex;
    private String nation;
    private String birthday;
    private String address;
    private String personId;
    private String department;
    private String startDate;
    private String endDate;
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String toString() {
        return "{\"personName\":\"" +personName+"\""+
                ",\"sex\":\"" +sex+"\""+",\"nation\":\"" +nation+"\""+
                ",\"birthday\":\"" +birthday+"\""+",\"address\":\"" +address+"\""+
                ",\"personId\":\"" +personId+"\""+",\"department\":\"" +department+"\""+
                ",\"startDate\":\"" +startDate+"\""+",\"endDate\":\"" +endDate+"\""+",\"imgPath\":\"" +imgPath+"\"}";
    }
}
