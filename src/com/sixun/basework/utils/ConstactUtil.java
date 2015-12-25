package com.sixun.basework.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class ConstactUtil {
	/**
	 * 获取通讯录所有数据
	 * 
	 * @return
	 */
	public static Map<String, String> getAllCallRecords(Context context) {
		Map<String, String> temp = new HashMap<String, String>();
		// Cursor c
		// =context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
		// null, null, null,
		// ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
		// if (c.moveToFirst()) {
		// do {
		// // 获得联系人的ID号
		// String contactId
		// =c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
		// // // 获得联系人姓名
		// String name
		// =c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		// // 查看该联系人有多少个电话号码。如果没有这返回值为0
		// int phoneCount
		// =c.getInt(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
		// String number = null;
		// if (phoneCount > 0) {
		// // 获得联系人的电话号码
		// Cursor phones =
		// context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
		// null,
		// ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " +
		// contactId, null, null);
		// if (phones.moveToFirst()) {
		// number =
		// phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		// }
		// phones.close();
		// }
		// temp.put(name, number);
		// } while (c.moveToNext());
		// }
		// c.close();
		// return temp;
		ContentResolver cr = context.getContentResolver();
		String str[] = { Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER,Phone.PHOTO_ID };
		Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, str, null,null, null);

		if (cur != null) {
			while (cur.moveToNext()) {
				// 得到手机号码
				String number = cur.getString(cur.getColumnIndex(Phone.NUMBER));
				String name = cur.getString(cur.getColumnIndex(Phone.DISPLAY_NAME));
				// contactsInfo.setContactsPhotoId(cur.getLong(cur.getColumnIndex(Phone.PHOTO_ID)));
				// long contactid =
				// cur.getLong(cur.getColumnIndex(Phone.CONTACT_ID));
				// long photoid =
				// cur.getLong(cur.getColumnIndex(Phone.PHOTO_ID));

				temp.put(name, number);

			}
		}
		cur.close();
		return temp;
	}
}

// /** 得到手机通讯录联系人信息 **/
// private List<SortModel> uploadPhoneContacts() {
// SourceDateList = new ArrayList<SortModel>();
// // List<FriendModel> contacts = new ArrayList<FriendModel>();
// ContentResolver resolver = getContentResolver();
//
// // 获取手机联系人
// Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
// PHONES_PROJECTION, null, null, null);
// if (SourceDateList != null && SourceDateList.size() > 0) {
// SourceDateList.clear();
// }
// if (phoneCursor != null) {
// while (phoneCursor.moveToNext()) {
// // 得到手机号码
// String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
// // 当手机号码为空的或者为空字段 跳过当前循环
// if (TextUtils.isEmpty(phoneNumber))
// continue;
// // 得到联系人名称
// String contactName = phoneCursor
// .getString(PHONES_DISPLAY_NAME_INDEX);
// // 得到联系人ID
// Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
// System.out.println("number = " + phoneNumber.replace(" ", "")
// + contactName);
// if (TextUtils.isEmpty(contactName)) {
// contactName = "";
// }
// if (TextUtils.isEmpty(phoneNumber)) {
// phoneNumber = "";
// }
// SourceDateList.add(new SortModel(contactName, phoneNumber
// .replace(" ", "")));
// }
// phoneCursor.close();
//
// }
// return SourceDateList;
// }
