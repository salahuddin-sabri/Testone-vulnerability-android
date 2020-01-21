package com.standardyze.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.Employee;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class employees_adapter extends RecyclerView.Adapter<employees_adapter.viewHolder> {
    Context context;
    DatabaseHelper databaseHelper;
    private ArrayList<Employee> employeeArrayList;
    SharedPrefManager sharedPrefManager;
    private OnItemClickListener onItemClickListener;

    public employees_adapter(Context context, ArrayList<Employee> employeeArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.employeeArrayList = employeeArrayList;
        sharedPrefManager = new SharedPrefManager(context);
        this.onItemClickListener = onItemClickListener;


    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item_list, parent, false); //Inflating the layout

        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Employee addEmployee = employeeArrayList.get(holder.getAdapterPosition());
        holder.setIsRecyclable(false);
        if (addEmployee != null) {
            holder.employeeName.setText(addEmployee.getFname() + " " + addEmployee.getLname());

        }


        databaseHelper = new DatabaseHelper(context);
          if (sharedPrefManager.getStringByKey("submittedEmployees")!=null){
              holder.isChecked.setEnabled(false);
          }
          else {
              holder.isChecked.setEnabled(true);
          }
        if (addEmployee != null) {
            if (addEmployee.getIsChecked() == 1) {
             //   databaseHelper.updateEmployees(1, addEmployee.getId());
                holder.isChecked.setChecked(true);
                addEmployee.setIsChecked(1);
              //  onItemClickListener.onItemClicked(holder.getAdapterPosition(),addEmployee.getIsChecked());


            } else {

              //  databaseHelper.updateEmployees(0, addEmployee.getId());
                holder.isChecked.setChecked(false);
                addEmployee.setIsChecked(0);
              //  onItemClickListener.onItemClicked(holder.getAdapterPosition(),addEmployee.getIsChecked());
            }
        }

      /*if (databaseHelper.selectEmployees().get(position).isEmployeeAdded()==1 || databaseHelper.selectEmployees().get(position).getIsChecked()==1){
          holder.isChecked.setChecked(true);
      }
      else
      {
          holder.isChecked.setChecked(false);
      }*/

       // String SubmittedEmployees = sharedPrefManager.getStringByKey("submittedEmployees");



//        String employees = sharedPrefManager.getStringByKey("submittedEmployees");
//       if (employees != null  && employees.equalsIgnoreCase(sharedPrefManager.getStringByKey("submittedEmployees"))) {
//
//           String[] id = employees.split(",");
//           for (int i = 0; i < id.length; i++) {
//
//
//             int index =  employeeArrayList.indexOf(addEmployee);
//
//
//               if (id[i].equalsIgnoreCase(employeeArrayList.get(index).getFname() + " " + employeeArrayList.get(index).getLname())) {
//                   holder.isChecked.setChecked(true);
//                   assert addEmployee != null;
//                   addEmployee.setIsChecked(1);
//                   databaseHelper.updateEmployees(1, addEmployee.getId());
//                   Log.e("server selected ",""+id[i]);
//                   Log.e("db",""+addEmployee.getFname()+" "+addEmployee.getLname());
//
//                }else {
//                   holder.isChecked.setChecked(false);
//                   assert addEmployee != null;
//                   addEmployee.setIsChecked(0);
//                   databaseHelper.updateEmployees(0, addEmployee.getId());
//                   Log.e("Else server selected ",""+id[i]);
//                   Log.e("Else db",""+addEmployee.getFname()+" "+addEmployee.getLname());
//               }
//
//           }

//       }



      /*  } else {
            if (SubmittedEmployees != null) {
                String[] id = SubmittedEmployees.split(",");

                for (int i = 0; i < id.length; i++) {
                    assert addEmployee != null;
                    if (id[i].equalsIgnoreCase(addEmployee.getFname() + "" + addEmployee.getLname())) {
                        holder.isChecked.setChecked(true);
                        holder.isChecked.setEnabled(false);
                    } else {
                        holder.isChecked.setChecked(false);
                        holder.isChecked.setEnabled(false);
                    }
                }
            }
        }*/

        holder.isChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    assert addEmployee != null;
                    addEmployee.setIsChecked(1);
                    holder.isChecked.setChecked(true);
                 //   databaseHelper.updateEmployees(1, addEmployee.getId());
                    onItemClickListener.onItemClicked(holder.getAdapterPosition(), addEmployee.getIsChecked());
                } else {

                    assert addEmployee != null;
                    addEmployee.setIsChecked(0);
                    holder.isChecked.setChecked(false);
                  //  databaseHelper.updateEmployees(0, addEmployee.getId());
                    onItemClickListener.onItemClicked(holder.getAdapterPosition(), addEmployee.getIsChecked());

                }

            }

        });


    }

    public interface OnItemClickListener {
        void onItemClicked(int position, int isChecked);
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CheckBox isChecked;
        CustomTextViewBook employeeName;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            isChecked = (CheckBox) itemView.findViewById(R.id.checkbox);
            employeeName = (CustomTextViewBook) itemView.findViewById(R.id.employees_name);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*public void addItem(int pos) {
        Employee addEmployee = employeeArrayList.get(pos);
        if (employeeArrayList.contains(pos)) {
            employeeArrayList.add(addEmployee);
            notifyDataSetChanged();
        }

        //  notifyItemChanged(pos);
    }*/
}
