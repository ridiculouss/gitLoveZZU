package zzu.themb;

import java.util.HashMap;
import java.util.List;

public class ThembRecord {

	private static HashMap<String, List<ThembModuel>> thembrecord = new HashMap<String, List<ThembModuel>>();

	// ���ֵ
	synchronized public static boolean addkeyvalue(String key, List<ThembModuel> value) {
		thembrecord.put(key, value);
		return true;
	}

	// ɾ��ֵ
	synchronized public static boolean Delkeyvalue(String key) {
		thembrecord.remove(key);
		return false;
	}

	// ��ȡֵ
	synchronized public static List<ThembModuel> getvalue(String key) {
		return thembrecord.get(key);
	}
   public static HashMap<String, List<ThembModuel>> getmap(){
	   return thembrecord;
   }
}
