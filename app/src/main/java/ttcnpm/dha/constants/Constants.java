package ttcnpm.dha.constants;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;

/**
 * 
 * @author Snor
 * 
 */

@SuppressLint("SimpleDateFormat")
public class Constants {

	// Date formatter
	public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"dd/MM/yyyy");

	// Alarm
	public static final String keystring = "ALARMkey";
	public static final String keytime = "ALARMkeytime";
	public static final String keycreate = "ALARMkeycreate";
	public static final String keyid = "ALARMkeyid";

	// Task
	public static final String TASK_ID_STRING = "taskid";

	// Fragment
	public static final String FRAGMENT_CATELOGY = "Catelogy fragment tag";
	public static final String FRAGMENT_NEW_CATELOGY = "new Catelogy fragment tag";
	
	// SMS Schedule
	public final static String PHONE_NUMBER = "PHONE_NUMBER";
	public final static String MESSAGE = "MESSAGE";

	//Category
	public static final String CATEGORY_ID = "Category id";

	public final static String NAME_CATEGORY_ID = "cateID";
	
	//Checklist
	public static final int NEW_CHECK_LIST_REQUEST = 101;
	public static final String CheckListID = "checklistID";
	

	//Code
//	public static final int DIALOG_CATEGORY_REQUEST_CODE = 1;

}
