package com.example.martinlamby.parking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by martinlamby on 25.08.15.
 */
public class CustomContactListAdapter extends ArrayAdapter<Contact> {

    private static final int SELECTED_ITEM_COLOR = Color.rgb(255,255,255);
    private ArrayList<Contact> contacts;

    public CustomContactListAdapter(Context context, int resource, ArrayList<Contact> contacts) {
        super(context, resource, contacts);
        this.contacts = contacts;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View rowView = convertView;

        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            rowView = layoutInflater.inflate(R.layout.contacts_list_item, parent, false);
        }
        final TextView contactName = (TextView) rowView.findViewById(R.id.contactNameTextView);

        final View finalRowView = rowView;
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contacts.get(position).getIsSelected()==false) {
                    finalRowView.setBackgroundColor(SELECTED_ITEM_COLOR);
                    contacts.get(position).setIsSelected(true);
                } else {
                    finalRowView.setBackgroundColor(Color.TRANSPARENT);
                    contacts.get(position).setIsSelected(false);
                }
            }
        });

        contactName.setText(contacts.get(position).getName());

        return rowView;

    }

    public ArrayList<Contact> getSelectedContacts(){
        ArrayList<Contact> selectedContacts = new ArrayList<>();

        for(int i = 0;i<contacts.size();i++){
            if(contacts.get(i).getIsSelected()==true){
                selectedContacts.add(contacts.get(i));
            }
        }

        return selectedContacts;
    }
}
