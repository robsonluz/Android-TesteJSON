/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.fae.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

/**
 * Class containing some static utility methods.
 */
public class Utils {
	
	//private static Map<String, String> webViewHeaders;
	
    private Utils() {};

//    @TargetApi(11)
//    public static void enableStrictMode() {
//        if (Utils.hasGingerbread()) {
//            StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
//                    new StrictMode.ThreadPolicy.Builder()
//                            .detectAll()
//                            .penaltyLog();
//            StrictMode.VmPolicy.Builder vmPolicyBuilder =
//                    new StrictMode.VmPolicy.Builder()
//                            .detectAll()
//                            .penaltyLog();
//
//            if (Utils.hasHoneycomb()) {
//                threadPolicyBuilder.penaltyFlashScreen();
//                vmPolicyBuilder
//                        .setClassInstanceLimit(ImageGridActivity.class, 1)
//                        .setClassInstanceLimit(ImageDetailActivity.class, 1);
//            }
//            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
//            StrictMode.setVmPolicy(vmPolicyBuilder.build());
//        }
//    }
    
    
    public static String getHttpUserAgent(Context context) {
    	String userAgent = "Android - FAEConnectApp";
    	try {
			String versionName = context.getPackageManager()
				    .getPackageInfo(context.getPackageName(), 0).versionName;
			if(versionName!=null) {
				userAgent += "/"+versionName; 
			}
	    	
		} catch (NameNotFoundException e) {
		}
    	
    	userAgent += " (" + Build.MODEL + "; "+ Build.MANUFACTURER + "; "+ Build.VERSION.RELEASE+")";
    	
    	return userAgent;
    }
    
	public static String streamToString(InputStream is) throws IOException {

		byte[] bytes = new byte[1024];
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int lidos;
		while ((lidos = is.read(bytes)) > 0) {
			bout.write(bytes, 0, lidos);
		}
		return new String(bout.toByteArray());
	}    

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
    
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } 
    }    
    
    /**
     * This method converts dp unit to equivalent pixels, depending on device density. 
     * 
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(float dp){
        // Get the screen's density scale
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     * 
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px){
    	 return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }    
    
    public static void showAlert(String message, Context context) {
    	showAlert(message, context,
    		new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {
				}
			}    			
    	);
    }
    
    public static void showAlert(String message, Context context, DialogInterface.OnClickListener okListener) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setPositiveButton(android.R.string.ok, okListener);
		
		builder.create().show();    	
    	
    }
    
    public static void showAlertConfirm(String message, Context context, DialogInterface.OnClickListener yesListener) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setPositiveButton(android.R.string.yes,yesListener)
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
							}
						});
		
		builder.create().show();    	
    }
    
	public static boolean isTabletDevice(Context context) {
		TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE;
	}
	
	public static int getScreenOrientation(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		return display.getRotation();
	}
	
	/**
	 * Verificar se é para mostrar o icone do aplicativo como botão de acão
	 * Mostra como açao em: celular, e tablet portrait
	 * @param actionBar
	 * @param context
	 */
	public static void checkDisplayHomeAsUpEnabled(ActionBar actionBar, Context context) {
		if(Utils.isTabletDevice(context) && Utils.getScreenOrientation(context)==0) {
			actionBar.setDisplayHomeAsUpEnabled(false);
		}else{
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}
	
	public static boolean verificaURLInstituicao(String url) {
		if(url!=null) {
		    //Trata URLs externas
		    if(url.startsWith("http://")) {
		        //URLs da FAE e do Moodle são abertas dentro do webview
		        if(url.indexOf("fae.edu") != -1 || url.startsWith("http://www.eadfranciscanos.com.br/")) {
		            return true;
		        }
		    }		
		}
		return false;
	}
}
