package benawad.com.todolist.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import benawad.com.todolist.MainActivity;
import benawad.com.todolist.NoteActivity;
import benawad.com.todolist.R;
import benawad.com.todolist.contentprovider.NoteContentProvider;

/**
 * Created by benawad on 4/14/15.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ContactViewHolder> {

    private static final String TAG = CardViewAdapter.class.getSimpleName();

    private List<String> noteList;
    private List<Integer> columnId;
    private List<String> slashes;
    Context mContext;
    MainActivity mMainActivity;

    public CardViewAdapter(List<String> noteList, MainActivity context,
                           List<Integer> columnId, List<String> slashes) {
        this.noteList = noteList;
        mContext = context;
        this.columnId = columnId;
        this.slashes = slashes;
        mMainActivity = context;
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    @Override
    @SuppressLint("NewApi")
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        String stringJsonArray = noteList.get(i);
        try {
            JSONArray jsonArray = new JSONArray(stringJsonArray);
            JSONArray jsonSlashes = new JSONArray(slashes.get(i));
            if (jsonArray.get(0) != null) {
                contactViewHolder.mTitle.setText(jsonArray.get(0).toString());
            }
            if (jsonArray.get(1) != null) {
                if(Integer.parseInt(jsonSlashes.get(0).toString()) == NoteActivity.SLASHED){
                    contactViewHolder.mItem1.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.mItem1.setText(jsonArray.get(1).toString());
            }
            if (jsonArray.get(2) != null) {
                if(Integer.parseInt(jsonSlashes.get(1).toString()) == NoteActivity.SLASHED){
                    contactViewHolder.mItem2.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.mItem2.setText(jsonArray.get(2).toString());
            }
            if (jsonArray.get(3) != null) {
                if(Integer.parseInt(jsonSlashes.get(2).toString()) == NoteActivity.SLASHED){
                    contactViewHolder.mItem3.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.mItem3.setText(jsonArray.get(3).toString());
            }
            if (jsonArray.get(4) != null) {
                if(Integer.parseInt(jsonSlashes.get(3).toString()) == NoteActivity.SLASHED){
                    contactViewHolder.mItem4.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.mItem4.setText(jsonArray.get(4).toString());
                //set left drawable
            }
        } catch (JSONException ignore) {
        }

        contactViewHolder.mCardView.setTag(i);

        contactViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, NoteActivity.class);
                Uri noteUri = Uri.parse(NoteContentProvider.CONTENT_URI + "/" + columnId.get((int)v.getTag()));
                i.putExtra(NoteContentProvider.CONTENT_ITEM_TYPE, noteUri);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    mContext.startActivity(i, ActivityOptions
//                            .makeSceneTransitionAnimation(mMainActivity).toBundle());
                    mContext.startActivity(i);
                    //make animation
                }
                else{
                    mContext.startActivity(i);
                }
            }
        });

    }

    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected CardView mCardView;
        protected TextView mTitle;
        protected TextView mItem1;
        protected TextView mItem2;
        protected TextView mItem3;
        protected TextView mItem4;

        public ContactViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTitle = (TextView) v.findViewById(R.id.cardview_note_title);
            mItem1 = (TextView) v.findViewById(R.id.note_item1);
            mItem2 = (TextView) v.findViewById(R.id.note_item2);
            mItem3 = (TextView) v.findViewById(R.id.note_item3);
            mItem4 = (TextView) v.findViewById(R.id.note_item4);

        }
    }

}
