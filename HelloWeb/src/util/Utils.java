package util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import info.Approval;
import info.CompanyInfo;
import info.DailySign;
import info.Message;
import info.Notice;
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

	public static JSONArray transFromListApproval(List<Approval> approval) {
		if(approval == null)return null;
		if(approval.isEmpty())return null;
		JSONArray jsonArray = new JSONArray();
		for(Approval approv: approval){
			jsonArray.put(approv.toJSONObject());
		}
		return jsonArray;
	}

	public static JSONArray transFromListNotice(List<Notice> notice) {
		if(notice == null)return null;
		if(notice.isEmpty())return null;
		JSONArray jsonArray = new JSONArray();
		for(Notice approv: notice){
			jsonArray.put(approv.toJSONObject());
		}
		return jsonArray;
	}

	public static JSONArray transFromListMessage(List<Message> list) {
		if(list == null)return null;
		if(list.isEmpty())return null;
		JSONArray jsonArray = new JSONArray();
		for(Message approv: list){
			jsonArray.put(approv.toJSONObject());
		}
		return jsonArray;
	}

	public static JSONArray transFromCompanyInfos(List<CompanyInfo> companyInfo) {
		if(companyInfo == null)return null;
		if(companyInfo.isEmpty())return null;
		JSONArray jsonArray = new JSONArray();
		for(CompanyInfo approv: companyInfo){
			jsonArray.put(approv.toJSONObject());
		}
		return jsonArray;
	}

	public static JSONArray transFromDailySigns(List<DailySign> queryDailySign) {
		if(queryDailySign == null)return null;
		if(queryDailySign.isEmpty())return null;
		JSONArray jsonArray = new JSONArray();
		for(DailySign approv: queryDailySign){
			jsonArray.put(approv.toJSONObject());
		}
		return jsonArray;
	}
}
