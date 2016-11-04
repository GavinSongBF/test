package gavin.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtils
{
	public static void hideSoftInput(Activity activity)
    {
        View view = activity.getCurrentFocus();
        if (view != null)
        {
            view.clearFocus();
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
	}

	public static void showSoftInput(EditText editText)
    {
        editText.requestFocus();
		InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}

	public static void showSoftInputDelay(final EditText editText)
    {
        editText.postDelayed(new Runnable()
        {
			@Override
			public void run()
            {
				showSoftInput(editText);
			}
		}, 200);
	}

}
