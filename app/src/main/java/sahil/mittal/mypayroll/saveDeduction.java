package sahil.mittal.mypayroll;

public class saveDeduction {

    String id,name,email,deductionDate,deduction,leaves;

    public saveDeduction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeaves() {
        return leaves;
    }

    public void setLeaves(String leaves) {
        this.leaves = leaves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeductionDate() {
        return deductionDate;
    }

    public void setDeductionDate(String deductionDate) {
        this.deductionDate = deductionDate;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }

    public saveDeduction(String id, String name, String email, String leaves,String deductionDate, String deduction) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deductionDate = deductionDate;
        this.deduction = deduction;
        this.leaves=leaves;

    }
}
