package com.example.lab3pp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OutputActivity extends AppCompatActivity {
    RecyclerView list;
    ArrayList<String> output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_activity);
        output = getIntent().getStringArrayListExtra("mas"); //почему иногда тут 4 а иногда 5
        list = (RecyclerView) findViewById(R.id.outList);
        list.setAdapter(new CustomAdapter(this, output.size()));
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private LayoutInflater inflater;
        Context context;
        private int number;

        CustomAdapter(Context context, int number) {
            this.number = number;
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = inflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            String id = "", name = "", date = "";
            StringBuffer id1 = new StringBuffer(""), name1 = new StringBuffer(""), date1 = new StringBuffer("");
            split(output.get(position), id1, name1, date1); //здесь надо split встроенный взять
            id = id1.toString();
            name = name1.toString();
            date = date1.toString();
            holder.id.setText(id);
            holder.name.setText(name);
            holder.date.setText(date);
        }

        @Override
        public int getItemCount() {
            return number;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View view;
            final TextView id, name, date;

            ViewHolder(final View view) {
                super(view);
                this.view = view;
                id = (TextView) view.findViewById(R.id.id);
                name = (TextView) view.findViewById(R.id.name);
                date = (TextView) view.findViewById(R.id.date);
            }
        }

        private void split(String stroka, StringBuffer id, StringBuffer name, StringBuffer date) {
            int i = stroka.indexOf(' ');
            if (i != -1) {
                id.append(stroka.substring(0, i));
                stroka = stroka.substring(i + 1);
                i = stroka.lastIndexOf(' ');
                if (i != -1) {
                    name.append(stroka.substring(0, i - 9));
                    if (i + 1 <= stroka.length()) {
                        stroka = stroka.substring(i - 8);
                        date.append(stroka);
                    }
                } else name.append(stroka);
            } else id.append(stroka);
        }
    }
}
