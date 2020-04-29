package sahil.mittal.mypayroll;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.List;

public class ArraySalary<Salary> extends ArrayAdapter<Salary> {
    private Activity context;
    private List<Salary> arraysalary;

    public  ArraySalary(Activity context,List<Salary> arraysalary){
        super(context,R.layout.salary_list,arraysalary);
        this.context=context;
        this.arraysalary=arraysalary;

    }
}
