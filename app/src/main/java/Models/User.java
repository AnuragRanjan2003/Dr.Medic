package Models;

public class User {
    String UserName,Age,Gender;

    public User() {
    }
    public User(String UserName,String Age,String Gender){
        this.UserName=UserName;
        this.Age=Age;
        this.Gender=Gender;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
