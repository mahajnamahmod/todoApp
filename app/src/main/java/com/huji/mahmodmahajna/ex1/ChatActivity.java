package com.huji.mahmodmahajna.ex1;

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
import android.widget.Toast;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private static final String RECYCLER_CONTENT = "recycler_content";
    final String RECYCLER_STATE = "recycler_state";
    private RecyclerView recyclerView;
    private Parcelable recyclerViewState;
    private RecyclerView.LayoutManager cLayoutManager;
    private ChatAdapter cAdapter;
    private ArrayList<Todo> todos;
    private  EditText editText;

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
        editText = (EditText) findViewById(R.id.editText);
//        initializeButton(editText);
    }

    private void initializeButton(final EditText editText) {
        Button button = (Button) findViewById(R.id.add_todo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")) {
                    cAdapter.addItemToList(new Todo(editText.getText().toString()));
                    editText.setText("");
                    recyclerView.scrollToPosition(cAdapter.getItemCount() - 1 );
                }
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
                if (!editText.getText().toString().equals("")) {
                    cAdapter.addItemToList(new Todo(editText.getText().toString()));
                    editText.setText("");
                    recyclerView.scrollToPosition(cAdapter.getItemCount() - 1 );
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}