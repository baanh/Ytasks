package ttcnpm.dha.dao;

import java.sql.Date;

import ttcnpm.dha.vo.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {
	// Database fields
	private SQLiteDatabase database;
	private DatabaseConstruction dbHelper;

	public UserDAO() {
		super();
	}

	public UserDAO(Context context) {
		dbHelper = new DatabaseConstruction(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public boolean createUser(User newUser) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_USER_AVATAR, newUser.getAvatar());
		values.put(DatabaseConstruction.COLUMN_USER_BIRTHDAY, newUser
				.getBirthday().toString());
		values.put(DatabaseConstruction.COLUMN_USER_COVER_PHOTO,
				newUser.getCoverPhoto());
		values.put(DatabaseConstruction.COLUMN_USER_EMAIL, newUser.getEmail());
		values.put(DatabaseConstruction.COLUMN_USER_FACEBOOK_ADDRESS,
				newUser.getFacebookAddress());
		values.put(DatabaseConstruction.COLUMN_USER_FIRST_NAME,
				newUser.getFirstName());
		values.put(DatabaseConstruction.COLUMN_USER_LAST_NAME,
				newUser.getLastName());
		values.put(DatabaseConstruction.COLUMN_USER_PASSWORD,
				newUser.getPassword());
		values.put(DatabaseConstruction.COLUMN_USER_PHONE_NUMBER,
				newUser.getPhoneNumber());
		values.put(DatabaseConstruction.COLUMN_USER_PROFESSION,
				newUser.getProfession());
		long result = database.insert(DatabaseConstruction.TABLE_USER, null,
				values);
		return (result > 0);
	}

	public boolean updateUser(User newUser) {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstruction.COLUMN_USER_AVATAR, newUser.getAvatar());
		values.put(DatabaseConstruction.COLUMN_USER_BIRTHDAY, newUser
				.getBirthday().toString());
		values.put(DatabaseConstruction.COLUMN_USER_COVER_PHOTO,
				newUser.getCoverPhoto());
		values.put(DatabaseConstruction.COLUMN_USER_EMAIL, newUser.getEmail());
		values.put(DatabaseConstruction.COLUMN_USER_FACEBOOK_ADDRESS,
				newUser.getFacebookAddress());
		values.put(DatabaseConstruction.COLUMN_USER_FIRST_NAME,
				newUser.getFirstName());
		values.put(DatabaseConstruction.COLUMN_USER_LAST_NAME,
				newUser.getLastName());
		values.put(DatabaseConstruction.COLUMN_USER_PASSWORD,
				newUser.getPassword());
		values.put(DatabaseConstruction.COLUMN_USER_PHONE_NUMBER,
				newUser.getPhoneNumber());
		values.put(DatabaseConstruction.COLUMN_USER_PROFESSION,
				newUser.getProfession());
		int result = database.update(DatabaseConstruction.TABLE_USER, values,
				null, null);
		return (result > 0);
	}

	public User getUser(int userID, SQLiteDatabase database) {
		Cursor result = database.query(DatabaseConstruction.TABLE_USER, null,
				DatabaseConstruction.COLUMN_USER_ID + "=" + userID, null, null,
				null, null);
		User user = new User();
		user.setId(userID);
		user.setAvatar(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_AVATAR)));
		user.setBirthday(Date.valueOf(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_BIRTHDAY))));
		user.setCoverPhoto(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_COVER_PHOTO)));
		user.setEmail(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_EMAIL)));
		user.setFacebookAddress(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_FACEBOOK_ADDRESS)));
		user.setFirstName(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_FIRST_NAME)));
		user.setId(result.getInt(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_ID)));
		user.setLastName(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_LAST_NAME)));
		user.setPassword(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_PASSWORD)));
		user.setPhoneNumber(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_PHONE_NUMBER)));
		user.setProfession(result.getString(result
				.getColumnIndex(DatabaseConstruction.COLUMN_USER_PROFESSION)));
		return user;
	}
}
