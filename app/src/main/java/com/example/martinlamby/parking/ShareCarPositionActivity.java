package com.example.martinlamby.parking;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ShareCarPositionActivity extends AppCompatActivity {

    private ArrayList<Contact> contacts;
    private CustomContactListAdapter customContactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_car_position);

        retrieveContacts();
        ListView contactsList = (ListView) findViewById(R.id.contactsListView);
        customContactListAdapter = new CustomContactListAdapter(getApplicationContext(),R.layout.contacts_list_item, contacts, contactsList);
        contactsList.setAdapter(customContactListAdapter);
        customContactListAdapter.notifyDataSetChanged();

        Button sendButton = (Button) findViewById(R.id.sendCarPositionButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //get Phone Number and send SMS with google-Maps Link
                sendSMS();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share_car_position, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //retrieve all Contacts that have a phone Number
    public void retrieveContacts(){
        Cursor contactsCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        contacts = new ArrayList<>();

        while(contactsCursor.moveToNext()){

            String name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String mobilePhoneNumber = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if(mobilePhoneNumber!=null) {
                contacts.add(new Contact(name, mobilePhoneNumber));
            }
        }

        System.out.println(contacts);
    }

    public void sendSMS(){
        Contact selectedContact = customContactListAdapter.getSelectedContact();
        System.out.println(selectedContact.getName());
        //should work with sms and maybe email
        //google url should also define zoom (z)
        String smsBody = "Here is my car \n http://maps.google.com?q"+GeoLocationService.getLastLocationLatitude()+","+GeoLocationService.getLastLocationLongitude()+"&z=12";
        System.out.println("googleMapsLocationUri");

        //email is implemented for test pruposes
        try {
            SmsManager smsManager = SmsManager.getDefault();
                //smsManager.sendTextMessage(selectedContact.getMobilePhoneNumber(), null, googleMapsLocationUri, null, null);
                //System.out.println("SMS send");

                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                intent.putExtra(Intent.EXTRA_TEXT, smsBody );
                intent.setData(Uri.parse("mailto:martinlamby@gmail.com")); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);

        }catch (Exception e){
            System.out.println("Error:  "+e.getMessage());
        }
    }

}
