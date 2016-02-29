package com.tokenlab.murilorodrigues.tokenlab.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tokenlab.murilorodrigues.tokenlab.R;
import com.tokenlab.murilorodrigues.tokenlab.model.People;

import java.util.List;

/**
 * Created by rodrigues on 27/02/16.
 * Adapter para criar lista de entidades People
 */
public class ListPeopleAdapter extends ArrayAdapter<People> {
    private LayoutInflater inflate;

    public ListPeopleAdapter(Activity activity, List<People> listPeople) {
        super(activity, R.layout.cell_people, listPeople);
        inflate = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null ){

            // Infla uma nova célula na lista
            convertView = inflate.inflate(R.layout.cell_people, null);
            holder = new ViewHolder();
            holder.imgPeople = (ImageView) convertView.findViewById(R.id.imgPeople);
            holder.txtPeople = (TextView) convertView.findViewById(R.id.txtPeople);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        People people = getItem(position);
        holder.txtPeople.setText((CharSequence) people.toString());

        switch (people.getName().trim().substring(0,1).toUpperCase()){
            case "A":
                holder.imgPeople.setImageResource(R.drawable.a);
                break;
            case "B":
                holder.imgPeople.setImageResource(R.drawable.b);
                break;
            case "C":
                holder.imgPeople.setImageResource(R.drawable.c);
                break;

        }

        return convertView;
    }

    static class ViewHolder {
        public ImageView imgPeople;
        public TextView txtPeople;
    }
}
