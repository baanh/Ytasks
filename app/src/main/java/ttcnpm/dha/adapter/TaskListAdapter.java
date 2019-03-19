/**
 * 
 */
package ttcnpm.dha.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ttcnpm.dha.constants.Constants;
import ttcnpm.dha.dao.DatabaseConstruction;
import ttcnpm.dha.dao.TaskDAO;
import ttcnpm.dha.vo.Task;
import ttcnpm.dha.ytasks.R;
import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * @author PHAN DUONG
 *
 */
public class TaskListAdapter extends ArrayAdapter<Task>{
	
	private ArrayList<Task> taskList;
	
	private class ViewHolder {
		   TextView taskName;
		   TextView taskDueday;
		   CheckBox taskBox;
		   
		  }

	public TaskListAdapter(Context context, int textViewResourceId,
			List<Task> objects) {
		super(context, textViewResourceId, objects);
		this.taskList = new ArrayList<Task>();
		this.taskList.addAll(objects);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		 ViewHolder holder = null;		 
		   if (convertView == null) {
			   LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			   convertView = vi.inflate(R.layout.task_list_item, null);
		 
			   holder = new ViewHolder();
			   holder.taskName = (TextView) convertView.findViewById(R.id.TaskOfCheckListTittle);
			   holder.taskDueday = (TextView) convertView.findViewById(R.id.TaskOfCheckListDueDay);
			   holder.taskBox = (CheckBox) convertView.findViewById(R.id.CheckListBox);
			   holder.taskBox.setOnCheckedChangeListener( new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					TaskDAO dataHelper = new TaskDAO(getContext());
					dataHelper.open();
					String newValue;
					if (isChecked){						
						newValue="Resolved";
					} else {
						Task temp =  dataHelper.getTask((int)getItemId(position), dataHelper.getDatabase());
						if (temp.getProgress().equals("0")){
							newValue="new";
						} else {
							newValue="In Progress";
						}
					}
					dataHelper.updateTask(DatabaseConstruction.COLUMN_TASK_STATUS, (int) getItemId(position), newValue);
					dataHelper.close();
					
				}
			});
			   convertView.setTag(holder);
		 
			   holder.taskBox.setOnClickListener( new View.OnClickListener() {  
				   public void onClick(View v) {  
					   //TODO change task status to completed
				   }  
			   });  
		   } else {
		    holder = (ViewHolder) convertView.getTag();
		   }
		 
		   Task task = taskList.get(position);
		   holder.taskName.setText(task.getTitle());
		   Date tempDueday = task.getDueDate();          
	        String str = String.format("%s %tB %<te, %<tY", 
	                "", tempDueday);	    
		   holder.taskDueday.setText(str);
		   String status = task.getStatus();
		   if (status.equals("Completed")||status.equals("Resolved")){
			   holder.taskBox.setChecked(true);	
		   } else {
			   holder.taskBox.setChecked(false);	
		   }		  
		 
		   return convertView;
		 
}

	@Override
	public long getItemId(int position) {
		long id;
		id = taskList.get(position).getId();
		return id;
	}
	
}
	
	
	


