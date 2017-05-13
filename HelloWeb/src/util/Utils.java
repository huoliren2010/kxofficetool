package util;

import java.util.List;

import org.json.JSONArray;

import info.UserInfo;

public class Utils {
	public static JSONArray transFrom(List<UserInfo> list){
		if(list == null)return null;
		if(list.isEmpty())return null;
		JSONArray jsonArray = new JSONArray();
		for(UserInfo user: list){
			jsonArray.put(user.toJSONObject());
		}
		return jsonArray;
	}
}
