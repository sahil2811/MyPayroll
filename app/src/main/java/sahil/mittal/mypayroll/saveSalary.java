package sahil.mittal.mypayroll;

public class saveSalary {
    String id,name,post,email,salary,da,hra,bonus,medical,allowance,leaves,deduction;

    public saveSalary() {
    }

    public saveSalary(String id, String name, String post, String email, String salary, String da, String hra, String bonus, String medical, String allowance, String leaves, String deduction) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.email = email;
        this.salary = salary;
        this.da = da;
        this.hra = hra;
        this.bonus = bonus;
        this.medical = medical;
        this.allowance = allowance;
        this.leaves = leaves;
        this.deduction = deduction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public String getHra() {
        return hra;
    }

    public void setHra(String hra) {
        this.hra = hra;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    public String getLeaves() {
        return leaves;
    }

    public void setLeaves(String leaves) {
        this.leaves = leaves;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }
}
