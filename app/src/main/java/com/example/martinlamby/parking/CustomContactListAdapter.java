package com.example.martinlamby.parking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by martinlamby on 25.08.15.
 */
public class CustomContactListAdapter extends ArrayAdapter<Contact> {

    private static final int SELECTED_ITEM_COLOR = Color.rgb(255,255,255);
    private ArrayList<Contact> contacts;
    private ListView list;

    public CustomContactListAdapter(Context context, int resource, ArrayList<Contact> contacts, ListView list) {
        super(context, resource, contacts);
        this.contacts = contacts;
        this.list = list;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View rowView = convertView;

        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            rowView = layoutInflater.inflate(R.layout.contacts_list_item, parent, false);
        }
        final TextView contactName = (TextView) rowView.findViewById(R.id.contactNameTextView);
        final TextView emailType = (TextView) rowView.findViewById(R.id.emailTypeTextView);

        final View finalRowView = rowView;
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;i<contacts.size();i++){
                    contacts.get(i).setIsSelected(false);
                    list.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                if (contacts.get(position).getIsSelected()==false) {
                    finalRowView.setBackgroundColor(SELECTED_ITEM_COLOR);
                    contacts.get(position).setIsSelected(true);
                }
            }
        });
        System.out.println(contacts.get(position).getEmailType());
        contactName.setText(contacts.get(position).getName());
        emailType.setText(contacts.get(position).getEmailType());

        return rowView;

    }

    public Contact getSelectedContact(){
        Contact selected = null;
        for(int i = 0;i<contacts.size();i++){
            if(contacts.get(i).getIsSelected()==true){
                selected = contacts.get(i);
            }
        }
        return selected;
    }
}
