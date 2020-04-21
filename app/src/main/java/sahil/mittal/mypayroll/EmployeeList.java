package sahil.mittal.mypayroll;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.animation.Positioning;

import java.util.List;

public class EmployeeList extends ArrayAdapter<Employees> {
    private Activity context;
    private List<Employees> employeesList;

    public EmployeeList(Activity context,List<Employees>employeesList){
        super(context,R.layout.list_layout,employeesList);
        this.context=context;
        this.employeesList=employeesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewName=(TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewId=(TextView) listViewItem.findViewById(R.id.textViewId);

         Employees employees=employeesList.get(position);

         textViewName.setText(employees.getName());
         textViewId.setText(employees.getId());

         return listViewItem;
    }
}
