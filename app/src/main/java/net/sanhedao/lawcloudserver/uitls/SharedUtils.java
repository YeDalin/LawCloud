package net.sanhedao.lawcloudserver.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.WebSettings.TextSize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

public class SharedUtils {
	public static String NAME = "SET";

	public static void setBooleanPrefs(Context context, String name, boolean value) {
//		SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		getSharedPreferences(context, NAME).edit().putBoolean(name, value).commit();
	}

	public static boolean getBooleanPrefs(Context context, String name, boolean def) {
//		SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		boolean b = getSharedPreferences(context, NAME).getBoolean(name, def);
		return b;
	}

	public static void setStringPrefs(Context context, String name, String value) {
//		SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		getSharedPreferences(context, NAME).edit().putString(name, value).commit();
	}

	public static String getStringPrefs(Context context, String name, String def) {
//		SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		String b = getSharedPreferences(context, NAME).getString(name, def);
		return b;
	}

	public static void setTextSizePrefs(Context context, String name, TextSize value) {
//		SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		getSharedPreferences(context, NAME).edit().putString(name, value.toString()).commit();
	}

	public static String getTextSizePrefs(Context context, String name, TextSize def) {
//		SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		String b = getSharedPreferences(context, NAME).getString(name, def.toString());
		return b;
	}

	public static void setIntPrefs(Context context, String name, int value) {
		getSharedPreferences(context, NAME).edit().putInt(name, value).commit();
	}

	public static int getIntPrefs(Context context, String name, int def) {
		int b = getSharedPreferences(context, NAME).getInt(name, def);
		return b;
	}

	public static void setLongPrefs(Context context, String name, long value) {
		getSharedPreferences(context, NAME).edit().putLong(name, value).commit();
	}

	public static long getLongPrefs(Context context, String name, long def) {
		long b = getSharedPreferences(context, NAME).getLong(name, def);
		return b;
	}

	/**
	 * collection 必须要implements Serializable，因为ObjectOutputStream的缘故
	 */
	public static void setCollectionPrefs(Context context, Collection collection, String collectionName) throws IOException {
		SharedPreferences.Editor edit=getSharedPreferences(context, NAME).edit();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(collection);
		String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));//利用Base64防止乱码
		edit.putString(collectionName,string);
		edit.apply();
		objectOutputStream.close();
	}
	public static Collection getCollectionPrefs(Context context, String collectionName) throws IOException, ClassNotFoundException {
		String string = getSharedPreferences(context, NAME).getString(collectionName, "");
		if (TextUtils.isEmpty(string)|| TextUtils.isEmpty(string.trim())){
			return null;
		}
		byte[] decodeBytes = Base64.decode(string.getBytes(), Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodeBytes);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		Collection collection = (Collection) objectInputStream.readObject();
		objectInputStream.close();
		return collection;
	}

	public static SharedPreferences getSharedPreferences(Context context, String spName){
		return context.getSharedPreferences(spName, Context.MODE_PRIVATE);
	}
}
