package com.huji.mahmodmahajna.ex1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.app.AlertDialog;


import java.util.ArrayList;
import java.util.Date;

import static android.R.id.input;

public class ChatActivity extends AppCompatActivity {

    private static final String RECYCLER_CONTENT = "recycler_content";
    final String RECYCLER_STATE = "recycler_state";
    private RecyclerView recyclerView;
    private Parcelable recyclerViewState;
    private RecyclerView.LayoutManager cLayoutManager;
    private ChatAdapter cAdapter;
    private ArrayList<Todo> todos;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initializeButtonFunctionality();
        initializeRecyclerView();
        final LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.my_button_layout);

//        buttonLayout.setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                findViewById(R.id.task_title).setBackgroundColor(34);
////                Toast.makeText(ChatActivity.this, "Long click!", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//        });

    }

    private void initializeRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cAdapter = new ChatAdapter(new ArrayList<Todo>(), ChatActivity.this);
        cLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(cLayoutManager);
        recyclerView.setAdapter(cAdapter);

    }

    private void initializeButtonFunctionality() {
//        editText = (EditText) findViewById(R.id.editText);
//        initializeButton(editText);
    }

    private void initializeButton(final EditText editText) {
        Button button = (Button) findViewById(R.id.add_todo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        recyclerViewState = cLayoutManager.onSaveInstanceState();
        //save recyclerView scroll position
        state.putParcelable(RECYCLER_STATE, recyclerViewState);

        //save recyclerView content
        state.putParcelableArrayList(RECYCLER_CONTENT, cAdapter.getTodos());

    }


    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) {
            //restore scroll position
            recyclerViewState = state.getParcelable(RECYCLER_STATE);
            //restore content
            todos = state.getParcelableArrayList(RECYCLER_CONTENT);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        //restore content
        if (todos != null) {
            cAdapter.getTodos().addAll(todos);
        }


        //restore scroll position
        if (recyclerViewState != null) {
            cLayoutManager.onRestoreInstanceState(recyclerViewState);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_todo:
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ChatActivity.this);
                    View dateView =  ChatActivity.this.getLayoutInflater().inflate(R.layout.datepicker_dialog, null);
                    final Button deleteTodo =  (Button) dateView.findViewById(R.id.delete_todo);
                    final Button addTodo =  (Button) dateView.findViewById(R.id.add_todo);
                    final EditText todoText =  (EditText) dateView.findViewById(R.id.editText2);
                    final DatePicker datePicker = (DatePicker) dateView.findViewById((R.id.date_picker));
                    builder.setView(dateView);
                    final android.support.v7.app.AlertDialog dialog = builder.create();
                    dialog.show();
                    deleteTodo.setOnClickListener(
                            new View.OnClickListener() {
                                public void onClick(View v) {
                                    dialog.dismiss();

                                }
                            }
                    );
                    addTodo.setOnClickListener(
                            new View.OnClickListener() {
                                public void onClick(View v) {
                                    cAdapter.addItemToList(
                                            new Todo(todoText.getText().toString(),
                                            new Date(datePicker.getDayOfMonth(),datePicker.getMonth() + 1,datePicker.getYear())));
                                    recyclerView.scrollToPosition(cAdapter.getItemCount() - 1 );
                                    dialog.dismiss();

                                }
                            }
                    );

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}