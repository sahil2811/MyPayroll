package sahil.mittal.mypayroll;

public class saveAllowance {
    String id,AllowanceId,name,post,salary,allowance,allowanceDate;


    public saveAllowance() {
    }

    public saveAllowance(String id,String allowanceDate,String AllowanceId, String name, String post, String salary, String allowance) {
        this.id = id;
        this.AllowanceId=AllowanceId;
        this.name = name;
        this.post = post;
        this.salary = salary;
        this.allowance = allowance;
        this.allowanceDate = allowanceDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAllowanceId() {
        return AllowanceId;
    }

    public void setAllowanceId(String allowanceId) {
        AllowanceId = allowanceId;
    }

    public String getAllowanceDate() {
        return allowanceDate;
    }

    public void setAllowanceDate(String allowanceDate) {
        this.allowanceDate = allowanceDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }
}
