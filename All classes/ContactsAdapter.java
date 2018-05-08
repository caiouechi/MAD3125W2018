package com.jk.fun;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import static android.Manifest.permission.READ_CONTACTS;


/**
 * Created by jkp on 2018-04-17.
 */

public class ContactsAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater layoutInflater;
    int totalContacts=0;
    TextView txtInitial;
    TextView txtPhone;
    ArrayList<String> arName;
    ArrayList<String> arPhone;


    public ContactsAdapter(Context c) {

        mContext = c;
        arName = new ArrayList<String>();
        arPhone = new ArrayList<String>();
        getContactList();
        layoutInflater = (LayoutInflater.from(c));
    }

    public int getCount() {
        return totalContacts;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.contact_grid_item,null);

        txtInitial = convertView.findViewById(R.id.txtInitial);
        txtInitial.setText(String.valueOf(arName.get(position).charAt(0)));

        txtPhone = convertView.findViewById(R.id.txtPhoneNo);
        txtPhone.setText(arPhone.get(position));

        return convertView;
    }


    private void getContactList() {
        ContentResolver cr = mContext.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cursor != null ? cursor.getCount() : 0) > 0) {
            totalContacts = cursor.getCount();
            
            while (cursor != null && cursor.moveToNext()) {
                String id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cursor.getInt(cursor.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        arName.add(name);
                        arPhone.add(phoneNo);
                        Log.i("contactList", "Name: " + name);
                        Log.i("contactList", "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if(cursor!=null){
            cursor.close();
        }

    }
}
