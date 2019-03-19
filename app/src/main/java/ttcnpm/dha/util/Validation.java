package ttcnpm.dha.util;

import java.util.regex.Pattern;

import android.widget.EditText;

public class Validation {
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String EMAIL_MSG = "invalid email";
	private static final String PASSWORD_MSG = "Password minimum 6 characters";
	private static final String REQUIRED_MSG = "Email and password must not be empty";
	
	public static boolean isEmailAddress(EditText editText) {
		return isValid(editText, EMAIL_REGEX, EMAIL_MSG);
	}

	public static boolean isPassword(EditText editText) {
		editText.setError(null);
		if (editText.getText().length() < 6) {
			editText.setError(PASSWORD_MSG);
			return false;
		}
		return true;
	}

	private static boolean isValid(EditText editText, String regex, String errMsg) {
		String text = editText.getText().toString().trim();

		if (!hasText(editText))
			return false;
		
		if (!Pattern.matches(regex, text)) {
			editText.setError(errMsg);
			return false;
		}

		return true;
	}

	public static boolean hasText(EditText editText) {
		String text = editText.getText().toString().trim();
		editText.setError(null);

		if (text.length() == 0) {
			editText.setError(REQUIRED_MSG);
			return false;
		}

		return true;
	}
}