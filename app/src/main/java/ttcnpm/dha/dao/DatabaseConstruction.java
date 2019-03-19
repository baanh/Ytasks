package ttcnpm.dha.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author TrongDiep
 *
 */
public class DatabaseConstruction extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ytasks.db";
	private static final int DATABASE_VERSION = 13;

	public DatabaseConstruction(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*--Table User--*/
	public static final String TABLE_USER = "user";
	public static final String COLUMN_USER_ID = "id";
	public static final String COLUMN_USER_PASSWORD = "password";
	public static final String COLUMN_USER_EMAIL = "email";
	public static final String COLUMN_USER_FIRST_NAME = "first_name";
	public static final String COLUMN_USER_LAST_NAME = "last_name";
	public static final String COLUMN_USER_PHONE_NUMBER = "phone_number";
	public static final String COLUMN_USER_PROFESSION = "profession";
	public static final String COLUMN_USER_FACEBOOK_ADDRESS = "facebook_address";
	public static final String COLUMN_USER_BIRTHDAY = "birthday";
	public static final String COLUMN_USER_AVATAR = "avatar";
	public static final String COLUMN_USER_COVER_PHOTO = "cover_photo";

	private static final String DATABASE_CREATE_USER = "create table "
			+ TABLE_USER + "(" + COLUMN_USER_ID
			+ " integer primary key autoincrement," + COLUMN_USER_PASSWORD
			+ " text not null," + COLUMN_USER_EMAIL
			+ " text not null," + COLUMN_USER_FIRST_NAME
			+ " text not null," + COLUMN_USER_LAST_NAME
			+ " text not null," + COLUMN_USER_PHONE_NUMBER
			+ " integer" + COLUMN_USER_PROFESSION
			+ " text," + COLUMN_USER_BIRTHDAY
			+ " text," + COLUMN_USER_FACEBOOK_ADDRESS
			+ " text," + COLUMN_USER_AVATAR
			+ " text," + COLUMN_USER_COVER_PHOTO
			+ " text);";

	/*--Table TaskType--*/
	public static final String TABLE_TASK_TYPE = "task_type";
	public static final String COLUMN_TASK_TYPE_ID = "id";
	public static final String COLUMN_TASK_TYPE_NAME = "name";
	public static final String COLUMN_TASK_TYPE_DESCRIPTION = "description";

	private static final String DATABASE_CREATE_TASK_TYPE = "create table "
			+ TABLE_TASK_TYPE + "(" + COLUMN_TASK_TYPE_ID
			+ " integer primary key autoincrement," + COLUMN_TASK_TYPE_NAME
			+ " text not null," + COLUMN_TASK_TYPE_DESCRIPTION
			+ " text);";

	/*--Table Task--*/
	public static final String TABLE_TASK = "task";
	public static final String COLUMN_TASK_ID = "_id";
	public static final String COLUMN_TASK_TITLE = "title";
	public static final String COLUMN_TASK_DESCRIPTION = "description";
	public static final String COLUMN_TYPE_ID = "typeID";
	public static final String COLUMN_TASK_START_DATE = "start_date";
	public static final String COLUMN_TASK_DUE_DATE = "due_date";
	public static final String COLUMN_TASK_STATUS = "status";
	public static final String COLUMN_TASK_PRIORITY = "priority";
	public static final String COLUMN_TASK_PROGRESS = "progress";
	public static final String COLUMN_TASK_ESTIMATE_TIME = "estimate_time";
	public static final String COLUMN_TASK_SPEND_TIME = "spend_time";
	public static final String COLUMN_TASK_PARENT_TASK = "parent_task";
	public static final String COLUMN_TASK_LOCATION = "location";
	public static final String COLUMN_TASK_ALERT = "alert";
	public static final String COLUMN_TASK_ASSIGNEE = "assignee";
	

	private static final String DATABASE_CREATE_TASK = "create table "
			+ TABLE_TASK + "(" + COLUMN_TASK_ID
			+ " integer primary key autoincrement," + COLUMN_TASK_TITLE
			+ " text not null," + COLUMN_TASK_DESCRIPTION
			+ " text," + COLUMN_TYPE_ID
			+ " text," + COLUMN_TASK_START_DATE
			+ " text," + COLUMN_TASK_DUE_DATE
			+ " text," + COLUMN_TASK_PRIORITY
			+ " text," + COLUMN_TASK_STATUS
			+ " text," + COLUMN_TASK_PROGRESS
			+ " text," + COLUMN_TASK_ESTIMATE_TIME
			+ " text," + COLUMN_TASK_SPEND_TIME
			+ " text," + COLUMN_TASK_PARENT_TASK
			+ " text," + COLUMN_TASK_LOCATION
			+ " text," + COLUMN_TASK_ALERT
			+ " text," + COLUMN_TASK_ASSIGNEE
			+ " text,"
			+ " foreign key(" + COLUMN_TYPE_ID + ") references " + TABLE_TASK_TYPE + "( " + COLUMN_TASK_TYPE_ID + "),"
			+ " foreign key(" + COLUMN_TYPE_ID + ") references " + TABLE_TASK + "( " + COLUMN_TYPE_ID + ")"
			+ ");";



	/*--Table Category--*/
	public static final String TABLE_CATEGORY = "category";
	public static final String COLUMN_CATEGORY_ID = "id";
	public static final String COLUMN_CATEGORY_NAME = "name";
	public static final String COLUMN_CATEGORY_DESCRIPTION = "description";

	private static final String DATABASE_CREATE_CATEGORY = "create table "
			+ TABLE_CATEGORY + "(" + COLUMN_TASK_TYPE_ID
			+ " integer primary key autoincrement," + COLUMN_CATEGORY_NAME
			+ " text not null," + COLUMN_CATEGORY_DESCRIPTION
			+ " text);";

	/*--Table CheckList--*/
	public static final String TABLE_CHECK_LIST = "check_list";
	public static final String COLUMN_CHECK_LIST_ID = "id";
	public static final String COLUMN_CHECK_LIST_NAME = "name";
	public static final String COLUMN_CHECK_LIST_DESCRIPTION = "description";
	public static final String COLUMN_CHECK_LIST_USER_ID = "userID";
	public static final String COLUMN_CHECK_LIST_CATEGORY_ID = "topicID";

	private static final String DATABASE_CREATE_CHECK_LIST = "create table "
			+ TABLE_CHECK_LIST + "(" + COLUMN_CHECK_LIST_ID
			+ " integer primary key autoincrement," + COLUMN_CHECK_LIST_USER_ID
			+ " integer," + COLUMN_CHECK_LIST_NAME
			+ " text not null," + COLUMN_CHECK_LIST_CATEGORY_ID
			+ " integer,"
			+ " foreign key(" + COLUMN_CHECK_LIST_USER_ID + ") references " + TABLE_USER + "( " + COLUMN_USER_ID + "),"
			+ " foreign key(" + COLUMN_CHECK_LIST_CATEGORY_ID + ") references " + TABLE_CATEGORY + "( " + COLUMN_USER_ID + ")"
			+ " );";

	/*--Table TaskList--*/
	public static final String TABLE_TASK_LIST = "list_task";
	public static final String COLUMN_TASK_LIST_ID = "listID";
	public static final String COLUMN_TASK_LIST_ITEM_ID = "taskID";

	private static final String DATABASE_CREATE_TASK_LIST = "create table "
			+ TABLE_TASK_LIST + "(" + COLUMN_TASK_LIST_ID
			+ " integer," + COLUMN_TASK_LIST_ITEM_ID
			+ " integer,"
			+ " primary key("+ COLUMN_TASK_LIST_ID + "," + COLUMN_TASK_LIST_ITEM_ID + "),"
			+ " foreign key(" + COLUMN_TASK_LIST_ID + ") references " + TABLE_CHECK_LIST + "( " + COLUMN_CHECK_LIST_ID + "),"
			+ " foreign key(" + COLUMN_TASK_LIST_ITEM_ID + ") references " + TABLE_TASK + "( " + COLUMN_TASK_ID + ")"
			+ " );";


	/*--Table GroupJob--*/
	public static final String TABLE_GROUP = "group_job";
	public static final String COLUMN_GROUP_ID = "id";
	public static final String COLUMN_GROUP_NAME = "name";
	public static final String COLUMN_GROUP_DESCRIPTION = "description";

	private static final String DATABASE_CREATE_GROUP = "create table "
			+ TABLE_GROUP + "(" + COLUMN_GROUP_ID
			+ " integer primary key autoincrement," + COLUMN_GROUP_NAME
			+ " text not null," + COLUMN_GROUP_DESCRIPTION
			+ " text);";
	
	/*--Table Project--*/
	public static final String TABLE_PROJECT = "project";
	public static final String COLUMN_PROJECT_ID = "id";
	public static final String COLUMN_PROJECT_NAME = "name";
	public static final String COLUMN_PROJECT_DESCRIPTION = "description";

	private static final String DATABASE_CREATE_PROJECT = "create table "
			+ TABLE_PROJECT + "(" + COLUMN_PROJECT_ID
			+ " integer primary key autoincrement," + COLUMN_PROJECT_NAME
			+ " text not null," + COLUMN_PROJECT_DESCRIPTION
			+ " text);";
	
	/*--Table GroupProject--*/
	public static final String TABLE_GROUP_PROJECT = "group_project";
	public static final String COLUMN_GROUP_PROJECT_GROUP_ID = "groupID";
	public static final String COLUMN_GROUP_PROJECT_PROJECT_ID = "prjectID";

	private static final String DATABASE_CREATE_GROUP_PROJECT = "create table "
			+ TABLE_GROUP_PROJECT + "(" + COLUMN_GROUP_PROJECT_GROUP_ID
			+ " integer," + COLUMN_GROUP_PROJECT_PROJECT_ID
			+ " integer,"
			+ " primary key("+ COLUMN_GROUP_PROJECT_GROUP_ID + "," + COLUMN_GROUP_PROJECT_PROJECT_ID + "),"
			+ " foreign key(" + COLUMN_GROUP_PROJECT_GROUP_ID + ") references " + TABLE_GROUP + "( " + COLUMN_GROUP_ID + "),"
			+ " foreign key(" + COLUMN_GROUP_PROJECT_PROJECT_ID + ") references " + TABLE_PROJECT + "( " + COLUMN_PROJECT_ID + ")"
			+ " );";
	
	/*--Table GroupMember--*/
	public static final String TABLE_GROUP_MEMBER = "group_member";
	public static final String COLUMN_GROUP_MEMBER_ID = "groupID";
	public static final String COLUMN_GROUP_MEMBER_USER_ID = "userID";

	private static final String DATABASE_CREATE_GROUP_MEMBER = "create table "
			+ TABLE_GROUP_MEMBER + "(" + COLUMN_GROUP_MEMBER_ID
			+ " integer," + COLUMN_GROUP_MEMBER_USER_ID
			+ " integer,"
			+ " primary key("+ COLUMN_GROUP_MEMBER_ID + "," + COLUMN_GROUP_MEMBER_USER_ID + "),"
			+ " foreign key(" + COLUMN_GROUP_MEMBER_ID + ") references " + TABLE_GROUP + "( " + COLUMN_GROUP_ID + "),"
			+ " foreign key(" + COLUMN_GROUP_MEMBER_USER_ID + ") references " + TABLE_USER + "( " + COLUMN_USER_ID + ")"
			+ " );";
	
	/*--Table GroupTask--*/
	public static final String TABLE_GROUP_TASK = "group_task";
	public static final String COLUMN_GROUP_TASK_GROUP_ID = "groupID";
	public static final String COLUMN_GROUP_TASK_TASK_ID = "taskID";
	public static final String COLUMN_GROUP_TASK_ASSIGNEE = "assignee";
	public static final String COLUMN_GROUP_TASK_CREATED_USER = "created_user";

	private static final String DATABASE_CREATE_GROUP_TASK = "create table "
			+ TABLE_GROUP_TASK + "(" + COLUMN_GROUP_TASK_GROUP_ID
			+ " integer," + COLUMN_GROUP_TASK_TASK_ID
			+ " integer," + COLUMN_GROUP_TASK_ASSIGNEE
			+ " integer," + COLUMN_GROUP_TASK_CREATED_USER
			+ " integer,"
			+ " primary key("+ COLUMN_GROUP_TASK_GROUP_ID + "," + COLUMN_GROUP_TASK_TASK_ID + "),"
			+ " foreign key(" + COLUMN_GROUP_TASK_ASSIGNEE + ") references " + TABLE_USER + "( " + COLUMN_USER_ID + "),"
			+ " foreign key(" + COLUMN_GROUP_TASK_CREATED_USER + ") references " + TABLE_USER + "( " + COLUMN_USER_ID + ")"
			+ " );";
	
	/*--Table GroupActivity--*/
	public static final String TABLE_GROUP_ACTIVITY = "group_activity";
	public static final String COLUMN_GROUP_ACTIVITY_ID = "id";
	public static final String COLUMN_GROUP_ACTIVITY_GROUP_ID = "groupID";
	public static final String COLUMN_GROUP_ACTIVITY_TASK_ID = "taskID";
	public static final String COLUMN_GROUP_ACTIVITY_USER_ID = "userID";
	public static final String COLUMN_GROUP_ACTIVITY_TIME = "time";
	public static final String COLUMN_GROUP_ACTIVITY_ACTIVITY = "activity";

	private static final String DATABASE_CREATE_GROUP_ACTIVITY = "create table "
			+ TABLE_GROUP_ACTIVITY + "(" + COLUMN_GROUP_ACTIVITY_ID
			+ " integer primary key autoincrement," + COLUMN_GROUP_ACTIVITY_GROUP_ID
			+ " integer," + COLUMN_GROUP_ACTIVITY_TASK_ID
			+ " integer," + COLUMN_GROUP_ACTIVITY_USER_ID
			+ " integer," + COLUMN_GROUP_ACTIVITY_TIME
			+ " text," + COLUMN_GROUP_ACTIVITY_ACTIVITY
			+ " text,"
			+ " foreign key(" + COLUMN_GROUP_ACTIVITY_GROUP_ID + ") references " + TABLE_GROUP + "( " + COLUMN_GROUP_ID + "),"
			+ " foreign key(" + COLUMN_GROUP_ACTIVITY_TASK_ID + ") references " + TABLE_TASK + "( " + COLUMN_TASK_ID + "),"
			+ " foreign key(" + COLUMN_GROUP_ACTIVITY_USER_ID + ") references " + TABLE_USER + "( " + COLUMN_USER_ID + ")"
			+ " );";

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_USER);
		database.execSQL(DATABASE_CREATE_TASK_TYPE);
		database.execSQL(DATABASE_CREATE_TASK);
		database.execSQL(DATABASE_CREATE_CATEGORY);
		database.execSQL(DATABASE_CREATE_CHECK_LIST);
		database.execSQL(DATABASE_CREATE_TASK_LIST);
		database.execSQL(DATABASE_CREATE_GROUP);
		database.execSQL(DATABASE_CREATE_PROJECT);
		database.execSQL(DATABASE_CREATE_GROUP_PROJECT);
		database.execSQL(DATABASE_CREATE_GROUP_MEMBER);
		database.execSQL(DATABASE_CREATE_GROUP_TASK);
		database.execSQL(DATABASE_CREATE_GROUP_ACTIVITY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(DatabaseConstruction.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_TYPE);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECK_LIST);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_LIST);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_PROJECT);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_MEMBER);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_TASK);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_ACTIVITY);
	    onCreate(db);
	  }

}
