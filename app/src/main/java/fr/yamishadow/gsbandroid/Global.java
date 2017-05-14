package fr.yamishadow.gsbandroid;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

public abstract class Global {

	// tableau d'informations mémorisées
	public static Hashtable<Integer, FraisMois> listFraisMois = new Hashtable<Integer, FraisMois>() ;

	// fichier contenant les informations sérialisées
	public static final String filename = new String("save.fic") ;

	/**
	 * Modification de l'affichage de la date (juste le mois et l'année, sans le jour)
	 */
	public static void changeAfficheDate(DatePicker datePicker) {
		//try {
				DatePicker dp_mes = (DatePicker) datePicker;

				int year    = dp_mes.getYear();
				int month   = dp_mes.getMonth();
				int day     = dp_mes.getDayOfMonth();

				dp_mes.init(year, month, day, new DatePicker.OnDateChangedListener() {
					@Override
					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					}
				});

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
					int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
					if (daySpinnerId != 0)
					{
						View daySpinner = dp_mes.findViewById(daySpinnerId);
						if (daySpinner != null)
						{
							daySpinner.setVisibility(View.GONE);
						}
					}

					int monthSpinnerId = Resources.getSystem().getIdentifier("month", "id", "android");
					if (monthSpinnerId != 0)
					{
						View monthSpinner = dp_mes.findViewById(monthSpinnerId);
						if (monthSpinner != null)
						{
							monthSpinner.setVisibility(View.VISIBLE);
						}
					}

					int yearSpinnerId = Resources.getSystem().getIdentifier("year", "id", "android");
					if (yearSpinnerId != 0)
					{
						View yearSpinner = dp_mes.findViewById(yearSpinnerId);
						if (yearSpinner != null)
						{
							yearSpinner.setVisibility(View.VISIBLE);
						}
					}
				} else { //Older SDK versions
					Field f[] = dp_mes.getClass().getDeclaredFields();
					for (Field field : f)
					{
						if(field.getName().equals("mDayPicker") || field.getName().equals("mDaySpinner"))
						{
							field.setAccessible(true);
							Object dayPicker = null;
							try {
								dayPicker = field.get(dp_mes);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							((View) dayPicker).setVisibility(View.GONE);
						}

						if(field.getName().equals("mMonthPicker") || field.getName().equals("mMonthSpinner"))
						{
							field.setAccessible(true);
							Object monthPicker = null;
							try {
								monthPicker = field.get(dp_mes);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							((View) monthPicker).setVisibility(View.VISIBLE);
						}

						if(field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner"))
						{
							field.setAccessible(true);
							Object yearPicker = null;
							try {
								yearPicker = field.get(dp_mes);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							((View) yearPicker).setVisibility(View.VISIBLE);
						}
					}
				}
		//    }
		//} catch (SecurityException e) {
		//    Log.d("ERROR", e.getMessage());
		//} catch (IllegalArgumentException e) {
		 //   Log.d("ERROR", e.getMessage());
		//} catch (IllegalAccessException e) {
		 //   Log.d("ERROR", e.getMessage());
	//	}
	}
	
}
