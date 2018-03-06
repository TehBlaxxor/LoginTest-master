package com.example.mihaelasolomon.login;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

    Context context;
    int layoutResourceId;
    ArrayList<User> users = null;

    public UserAdapter(@NonNull Context context, int resource, ArrayList<User> users) {
        super(context, resource);
        this.context=context;
        this.users=users;
        layoutResourceId=resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UserHolder();
            holder.name = (TextView)row.findViewById(R.id.textView3);
            holder.mail = (TextView)row.findViewById(R.id.textView4);
            holder.delete = (Button)row.findViewById(R.id.button4);

            row.setTag(holder);
        }
        else
        {
            holder = (UserHolder) row.getTag();
        }

        final User user = this.users.get(position);
        holder.name.setText(user.user);
        holder.mail.setText(user.email);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbhelper = new DBHelper(getContext());
                dbhelper.deleteUser(user.user, dbhelper, getContext());
            }
        });

        return row;
    }

    static class UserHolder
    {
        TextView name;
        TextView mail;
        Button delete;
    }
}
