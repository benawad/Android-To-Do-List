package benawad.com.todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import benawad.com.todolist.adapters.CardViewAdapter;
import benawad.com.todolist.contentprovider.NoteContentProvider;
import benawad.com.todolist.database.NoteTable;


public class MainActivity extends Activity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private SimpleCursorAdapter adapter;
    private ListView listView;
    private FloatingActionButton fab;
    RecyclerView recListLeft;
    RecyclerView recListRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recListLeft = (RecyclerView) findViewById(R.id.cardList_left);
        recListRight = (RecyclerView) findViewById(R.id.cardList_right);
        LinearLayoutManager llmLeft = new LinearLayoutManager(this);
        LinearLayoutManager llmRight = new LinearLayoutManager(this);
        llmLeft.setOrientation(LinearLayoutManager.VERTICAL);
        llmRight.setOrientation(LinearLayoutManager.VERTICAL);
        recListLeft.setLayoutManager(llmLeft);
        recListRight.setLayoutManager(llmRight);

        listView = (ListView) findViewById(R.id.home_list);
        fab = (FloatingActionButton) findViewById(R.id.addNewNote);
        fab.attachToListView(listView);
        fillData();
        //registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, NoteActivity.class);
                Uri noteUri = Uri.parse(NoteContentProvider.CONTENT_URI + "/" + id);
                i.putExtra(NoteContentProvider.CONTENT_ITEM_TYPE, noteUri);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(NoteContentProvider.CONTENT_URI + "/"
                        + id);
                getContentResolver().delete(uri, null, null);
                fillData();
                return false;
            }
        });

    }

    private void fillData() {

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { NoteTable.COLUMN_ITEMS };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.label };

        getLoaderManager().initLoader(0, null, this);

        adapter = new SimpleCursorAdapter(this, R.layout.homelist_row, null, from,
                to, 0);

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds mItems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    public void toNoteActivity(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("source", "newNote");
        if(Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= 21) {
            startActivity(intent, ActivityOptions
                    .makeSceneTransitionAnimation(this).toBundle());
        }
        else{
            startActivity(intent);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { NoteTable.COLUMN_ID, NoteTable.COLUMN_ITEMS, NoteTable.COLUMN_SLASHED };
        CursorLoader cursorLoader = new CursorLoader(this,
                NoteContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);

//        recListLeft.setHasFixedSize(true);
//        recListRight.setHasFixedSize(true);

        List<String> mainlist = new ArrayList<String>();
        List<Integer> mainColId = new ArrayList<Integer>();
        List<String> mainSlashes = new ArrayList<String>();
        List<String> leftList = new ArrayList<String>();
        List<Integer> leftColId = new ArrayList<Integer>();
        List<String> leftSlashes = new ArrayList<String>();
        List<String> rightList = new ArrayList<String>();
        List<Integer> rightColId = new ArrayList<Integer>();
        List<String> rightSlashes = new ArrayList<String>();

        if(data != null) {
            Log.v(TAG, "count" + data.getCount());
            for (int c = 0; c < data.getCount(); c++) {
                data.moveToNext();
                String sItems = data.getString(data
                        .getColumnIndexOrThrow(NoteTable.COLUMN_ITEMS));
                String sColId = data.getString(data.getColumnIndexOrThrow(NoteTable.COLUMN_ID));

                String sSlashes = data.getString(data.getColumnIndexOrThrow(NoteTable.COLUMN_SLASHED));

                mainSlashes.add(sSlashes);
                mainColId.add(Integer.parseInt(sColId));
                mainlist.add(sItems);
            }
        }
        for (int i = 0; i < mainlist.size(); i++) {
            if (i % 2 == 0) {
                leftList.add(mainlist.get(i));
                leftColId.add(mainColId.get(i));
                leftSlashes.add(mainSlashes.get(i));
            } else {
                rightList.add(mainlist.get(i));
                rightColId.add(mainColId.get(i));
                rightSlashes.add(mainSlashes.get(i));
            }
        }

        CardViewAdapter leftCardViewAdapter = new CardViewAdapter(leftList, this, leftColId, leftSlashes);
        CardViewAdapter RightCardViewAdapter = new CardViewAdapter(rightList, this, rightColId, rightSlashes);

        recListLeft.setAdapter(leftCardViewAdapter);
        recListRight.setAdapter(RightCardViewAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.v(TAG, "onLoaderReset method called");
        adapter.swapCursor(null);
    }
}
