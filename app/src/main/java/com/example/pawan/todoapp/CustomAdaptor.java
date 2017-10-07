package com.example.pawan.todoapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pawan on 09-09-2017.
 */

public class CustomAdaptor extends ArrayAdapter<Todo> {
    private ArrayList<Todo> mitems;
    private Context mcontext;
    Deletebutton mdeletebutton;
    public CustomAdaptor(@NonNull Context context, ArrayList<Todo> list,Deletebutton listener) {
        super(context,0);
        this.mcontext=context;
        this.mitems=list;
        mdeletebutton=listener;
    }
    @Override
    public int getCount() {
        return mitems.size();
    }

    @NonNull
    @Override


    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.row_layout,null);
            ViewHolder viewHolder=new ViewHolder();
            TextView todo=(TextView)convertView.findViewById(R.id.todo);
            Button button=(Button)convertView.findViewById(R.id.delete);
            viewHolder.button=button;
            viewHolder.todo= todo;
            convertView.setTag(viewHolder);
        }
        ViewHolder holder=(ViewHolder) convertView.getTag();
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdeletebutton.delete(position,view);
            }
        });
        holder.todo.setText(mitems.get(position).getTodo());
        return convertView;
    }

    static class ViewHolder{
        TextView todo;
        Button button;
    }

    public interface Deletebutton{
        void delete(int position,View view);
    }
}