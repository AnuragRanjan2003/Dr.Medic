package Models;

public class User {
    String UserName, Age, Gender, profileUrl;

    public User() {
    }

    public User(String UserName, String Age, String Gender, String profileUrl) {
        this.UserName = UserName;
        this.Age = Age;
        this.Gender = Gender;
        this.profileUrl = profileUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
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
