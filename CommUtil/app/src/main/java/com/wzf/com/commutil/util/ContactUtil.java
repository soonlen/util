package com.wzf.com.commutil.util;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactUtil {

	static ContactUtil instance;

	public static ContactUtil getInstance() {
		if(null == instance)
			instance = new ContactUtil();
		return instance;
	}
	/**
	 * 根据从系统通讯录获取的uri返回通讯录的名称，电话，邮箱
	 * @param context
	 * @param contactData
	 * @return {王五, 135********, aa@.qq.com};
	 */
	public String[] getNameAndPhoneAndEmailFromUrl(Activity context, Uri contactData) {
//		Cursor c =  context.managedQuery(contactData, null, null, null, null);
		Cursor c = context.getContentResolver().query(contactData, null, null, null, null);
		String contactName = "";
		String contactTelNum = "";
		String email = "";
		try {
			if (c.moveToFirst()) {
				contactName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
				String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (hasPhone.equalsIgnoreCase("1"))
					hasPhone = "true";
				else
					hasPhone = "false" ;
				if (Boolean.parseBoolean(hasPhone))
				{
					Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
					while (phones.moveToNext()) 
					{
						contactTelNum = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					}
					phones.close();
				}
				Cursor emails = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
						null, 
						ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId,
						null, null);
				int emailIndex = 0;
				if(emails.getCount() > 0) {
					emailIndex = emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
				}
				while(emails.moveToNext()) {
					email = emails.getString(emailIndex);
					L.i(email);
				}
				emails.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contactTelNum = contactTelNum.replaceAll(" +","");
		return new String[] {contactName, contactTelNum, email};
	}
}
