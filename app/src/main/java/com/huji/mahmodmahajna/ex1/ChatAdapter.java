package com.huji.mahmodmahajna.ex1;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahmodmahajna on 15/03/2017.
 */

class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {
    protected List<Todo> todos;
    private Context mContext;

    public ArrayList<Todo> getTodos() {
        return (ArrayList<Todo>) todos;
    }

    public ChatAdapter(List<Todo> strings, Context context) {
        this.mContext = context;
        this.todos = strings;
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list, parent, false);

        return new ChatHolder(v);
    }


    public void addItemToList(Todo item){
        todos.add(item);
        notifyDataSetChanged();
    }
    public void clearList(){
        todos.clear();
        notifyDataSetChanged();
    }
    public void deleteTodo(int position){
        todos.remove(position);
        Toast.makeText((ChatActivity)mContext, "Todo Deleted!", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();

    }
    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        holder.textView.setText(todos.get(position).getTodo());
        holder.setPos(position);
        holder.textView.setTextColor(position % 2 == 0 ? Color.RED : Color.BLUE);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }


    public class ChatHolder extends RecyclerView.ViewHolder{

        private final TextView textView;
        private int position;
        public ChatHolder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.task_title);

            view.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    AlertDialog.Builder builder = new AlertDialog.Builder((ChatActivity)mContext);
                    View mview = ((ChatActivity)mContext).getLayoutInflater().inflate(R.layout.my_dialog, null);
                    final TextView todoText =  (TextView) mview.findViewById(R.id.todo_text_edit);
                    final Button deleteTodo =  (Button) mview.findViewById(R.id.delete_todo);

                    todoText.setText(textView.getText());

                    builder.setView(mview);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    deleteTodo.setOnClickListener(
                            new View.OnClickListener() {
                                public void onClick(View v) {
                                    deleteTodo(position);
                              dialog.dismiss();

                                }
                            }
                    );
                    return true;
                }
            });
        }


        public void setText(String text)
        {

            textView.setText(text);
        }
        public void setPos(int pos){
            this.position = pos;
        }

    }



}