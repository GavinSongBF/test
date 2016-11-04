package gavin.widget;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

public class YearMonthPicker extends DatePickerDialog {

	public YearMonthPicker(Context context, int theme,
			OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth) {
		super(context, theme, callBack, year, monthOfYear, dayOfMonth);
		init(year, monthOfYear, dayOfMonth);
	}

	public YearMonthPicker(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
		init(year, monthOfYear, dayOfMonth);
	}

	private void init(int year, int month, int dayOfMonth) {
		hideDayView();
		hideCalendarView();
		setTitle(year, month);
	}

	@SuppressLint("NewApi")
	private void hideCalendarView() {
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			getDatePicker().setCalendarViewShown(false);
		}
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		setTitle(year, month);
	}

	private void setTitle(int year, int month) {
		month += 1;
		String yearStr = String.valueOf(year);
		String monthStr = (month < 10) ? ("0" + month) : ("" + month);
		setTitle(yearStr + "/" + monthStr);
	}

	private void hideDayView() {
		try {
			Field[] datePickerDialogFields = getClass().getSuperclass()
					.getDeclaredFields();
			for (Field datePickerDialogField : datePickerDialogFields) {
				if (datePickerDialogField.getName().equals("mDatePicker")) {
					datePickerDialogField.setAccessible(true);
					DatePicker datePicker = (DatePicker) datePickerDialogField
							.get(this);
					Field datePickerFields[] = datePickerDialogField.getType()
							.getDeclaredFields();
					for (Field datePickerField : datePickerFields) {
						if ("mDayPicker".equals(datePickerField.getName())
								|| "mDaySpinner".equals(datePickerField
										.getName())) {
							datePickerField.setAccessible(true);
							Object dayPicker = new Object();
							dayPicker = datePickerField.get(datePicker);
							((View) dayPicker).setVisibility(View.GONE);
						}
					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
