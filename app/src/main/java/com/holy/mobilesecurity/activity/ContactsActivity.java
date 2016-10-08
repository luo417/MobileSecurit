package com.holy.mobilesecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.holy.mobilesecurity.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends Activity {

    List<String> nameList = new ArrayList<String>();
    List<String> numberList =  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        readContacts();
        initUI();
    }

    private void readContacts() {
        Cursor cursor = null;
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            nameList.add(name);
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            numberList.add(number);
        }
    }

    private void initUI() {
        ListView contactList = (ListView) findViewById(R.id.lv_contacts);
        contactList.setAdapter(new MyAdapter());
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phoneNumber = numberList.get(position).replaceAll("-", " ").replaceAll(" ", "");

                Intent intent = new Intent();
                intent.putExtra("number", phoneNumber);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return nameList.size();
        }

        @Override
        public Object getItem(int position) {
            return nameList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.view_contacts_item, null);
            TextView tvContactNamne = (TextView) view.findViewById(R.id.tv_contact_name);
            tvContactNamne.setText(nameList.get(position));
            TextView tvContactPhone = (TextView) view.findViewById(R.id.tv_contact_phone);
            tvContactPhone.setText(numberList.get(position));
            return view;
        }
    }
}
