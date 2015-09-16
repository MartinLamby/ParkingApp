package com.example.martinlamby.parking;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShareCarPositionActivity extends AppCompatActivity {

    private ArrayList<Contact> contacts;
    private CustomContactListAdapter customContactListAdapter;
    private Contact selectedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_car_position);

        contacts = new ArrayList<>();
        retrieveEmailContacts();

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

    //retrieve all Contacts that have an eMail Address
    public void retrieveEmailContacts(){
        Uri uriCoarse = ContactsContract.Contacts.CONTENT_URI;
        String projectionCoarse[] = {ContactsContract.Contacts.LOOKUP_KEY};
        Cursor contactCursorCoarse = getContentResolver().query(uriCoarse,projectionCoarse,null,null,null);

        if(contactCursorCoarse.getCount()>0){
            while(contactCursorCoarse.moveToNext()){
                String id = contactCursorCoarse.getString(contactCursorCoarse.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                Uri uriFine = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
                String projectionFine[] = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Email.DATA, ContactsContract.CommonDataKinds.Email.TYPE};
                String filterFine = ContactsContract.CommonDataKinds.Email.LOOKUP_KEY + " = ?";

                Cursor contactCursorFine = getContentResolver().query(uriFine, projectionFine, filterFine, new String[]{id}, null);

                while (contactCursorFine.moveToNext()){
                    String name=contactCursorFine.getString(contactCursorFine.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    if(!name.contains("@")) {
                        Log.e("Name :", name);
                        String email = contactCursorFine.getString(contactCursorFine.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        int emailType = Integer.valueOf(contactCursorFine.getString(contactCursorFine.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)));
                        contacts.add(new Contact(name,email,parseEmailType(emailType)));
                        Log.e("Email", email);
                        Log.e("Type", ""+ parseEmailType(emailType));
                    }
                }
                contactCursorFine.close();
            }
            contactCursorCoarse.close();
        }
        }

    public String parseEmailType(int type){
        switch (type) {
            case 1:
                return "HOME";
            case 2:
                return "WORK";
            case 4:
                return "MOBILE";

            default:
                return "";
        }
    }

    public void sendSMS() {
        selectedContact = customContactListAdapter.getSelectedContact();
        if (selectedContact != null) {
            System.out.println(selectedContact.getName());
            //should work with sms and maybe email
            //google url should also define zoom (z)
            String emailBody = "Here is my car \n \nhttp://maps.google.com?q" + GeoLocationService.getLastLocationLatitude() + "," + GeoLocationService.getLastLocationLongitude() + "&z=12";
            System.out.println("googleMapsLocationUri");

            //email is implemented for test pruposes
            try {
                //SmsManager smsManager = SmsManager.getDefault();
                //smsManager.sendTextMessage(selectedContact.getMobilePhoneNumber(), null, googleMapsLocationUri, null, null);
                //System.out.println("SMS send");

                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Location of my Car"); //put as String ressource
                intent.putExtra(Intent.EXTRA_TEXT, emailBody);
                intent.setData(Uri.parse("mailto:" + selectedContact.getEmailAddress())); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);

            } catch (Exception e) {
                System.out.println("Error:  " + e.getMessage());
            }
        } else {
            Toast noContactSelected = Toast.makeText(getApplicationContext(), R.string.no_contact_selected, Toast.LENGTH_SHORT);
            noContactSelected.show();
        }
    }
}
