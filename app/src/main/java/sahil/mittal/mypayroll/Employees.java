package sahil.mittal.mypayroll;

public class Employees {
    String id,name,email,dob,department,post,address,age,contact,salary;

    public Employees(){

    }

    public Employees(String id,String name, String email, String dob, String department, String post, String address, String age, String contact, String salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.department = department;
        this.post = post;
        this.address = address;
        this.age = age;
        this.contact = contact;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getDepartment() {
        return department;
    }

    public String getPost() {
        return post;
    }

    public String getAddress() {
        return address;
    }

    public String getAge() {
        return age;
    }

    public String getContact() {
        return contact;
    }

    public String getSalary() {
        return salary;
    }
}
