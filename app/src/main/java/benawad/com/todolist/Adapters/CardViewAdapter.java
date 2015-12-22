package benawad.com.todolist.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private List<List<String>> noteList;
    private List<List<Integer>> columnId;
    private List<List<String>> slashes;
    private List<List<String>> titles;
    Context mContext;
    MainActivity mMainActivity;

    public CardViewAdapter(List<List<String>> noteList, MainActivity context,
                           List<List<Integer>> columnId, List<List<String>> slashes, List<List<String>> titles) {
        this.noteList = noteList;
        mContext = context;
        this.columnId = columnId;
        this.slashes = slashes;
        mMainActivity = context;
        this.titles = titles;
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    @Override
    @SuppressLint("NewApi")
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, int i) {
        try {
            JSONArray jsonArray = new JSONArray(noteList.get(i).get(0));
            JSONArray jsonSlashes = new JSONArray(slashes.get(i).get(0));
            try{
                contactViewHolder.mTitle.setText(titles.get(i).get(0));
            } catch (Exception ignored){}
            try{
                if (Integer.parseInt(jsonSlashes.get(0).toString()) == NoteActivity.SLASHED) {
                    contactViewHolder.mItem1.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.mItem1.setText(jsonArray.get(0).toString());
            } catch (Exception ignored){}
            try{
                if (Integer.parseInt(jsonSlashes.get(1).toString()) == NoteActivity.SLASHED) {
                    contactViewHolder.mItem2.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.mItem2.setText(jsonArray.get(1).toString());
            } catch (Exception ignored){}
            try{
                if (Integer.parseInt(jsonSlashes.get(2).toString()) == NoteActivity.SLASHED) {
                    contactViewHolder.mItem3.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.mItem3.setText(jsonArray.get(2).toString());
            } catch (Exception ignored){}
            try{
                if (Integer.parseInt(jsonSlashes.get(3).toString()) == NoteActivity.SLASHED) {
                    contactViewHolder.mItem4.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.mItem4.setText(jsonArray.get(3).toString());
                //set left drawable
            } catch (Exception ignored){}
        } catch (JSONException ignore) {
        }

        contactViewHolder.mCardView.setTag(i);

        contactViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, NoteActivity.class);
                Uri noteUri = Uri.parse(NoteContentProvider.CONTENT_URI + "/" + columnId.get((int) v.getTag()).get(0));
                i.putExtra(NoteContentProvider.CONTENT_ITEM_TYPE, noteUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    mContext.startActivity(i, ActivityOptions
//                            .makeSceneTransitionAnimation(mMainActivity).toBundle());
                    mContext.startActivity(i);
                    //make animation
                } else {
                    mContext.startActivity(i);
                }
            }
        });

        contactViewHolder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.v(TAG, "onLongClick");
                mMainActivity.selectedId = columnId.get((int) v.getTag()).get(0);
                mMainActivity.selectedNotePosition = (int) v.getTag();
                mMainActivity.noteFocused(1, v);
                //mMainActivity.deleteNote(columnId.get((int) v.getTag()).get(0));
                return false;
            }
        });

        if (noteList.get(i).size() == 2) {
            contactViewHolder.mCardView2.setVisibility(View.VISIBLE);
            try {
                JSONArray jsonArray = new JSONArray(noteList.get(i).get(1));
                JSONArray jsonSlashes = new JSONArray(slashes.get(i).get(1));
                try{
                    contactViewHolder.mTitle2.setText(titles.get(i).get(1));
                } catch (Exception ignored){}
                try{
                    if (Integer.parseInt(jsonSlashes.get(0).toString()) == NoteActivity.SLASHED) {
                        contactViewHolder.mItem12.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    contactViewHolder.mItem12.setText(jsonArray.get(0).toString());
                } catch (Exception ignored){}
                try{
                    if (Integer.parseInt(jsonSlashes.get(1).toString()) == NoteActivity.SLASHED) {
                        contactViewHolder.mItem22.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    contactViewHolder.mItem22.setText(jsonArray.get(1).toString());
                } catch (Exception ignored){}
                try{
                    if (Integer.parseInt(jsonSlashes.get(2).toString()) == NoteActivity.SLASHED) {
                        contactViewHolder.mItem32.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    contactViewHolder.mItem32.setText(jsonArray.get(2).toString());
                } catch (Exception ignored){}
                try{
                    if (Integer.parseInt(jsonSlashes.get(3).toString()) == NoteActivity.SLASHED) {
                        contactViewHolder.mItem42.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    contactViewHolder.mItem42.setText(jsonArray.get(3).toString());
                    //set left drawable
                } catch (Exception ignored){}
            } catch (JSONException ignore) {
                Log.e(TAG, "Exception caught=", ignore);
            }

            contactViewHolder.mCardView2.setTag(i);

            contactViewHolder.mCardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, NoteActivity.class);
                    Uri noteUri = Uri.parse(NoteContentProvider.CONTENT_URI + "/" + columnId.get((int) v.getTag()).get(1));
                    i.putExtra(NoteContentProvider.CONTENT_ITEM_TYPE, noteUri);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    mContext.startActivity(i, ActivityOptions
//                            .makeSceneTransitionAnimation(mMainActivity).toBundle());
                        mContext.startActivity(i);
                        //make animation
                    } else {
                        mContext.startActivity(i);
                    }
                }
            });
            contactViewHolder.mCardView2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mMainActivity.selectedId = columnId.get((int) v.getTag()).get(1);
                    mMainActivity.selectedNotePosition = (int) v.getTag();
                    mMainActivity.noteFocused(2, v);
                    return false;
                }
            });
        }


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
        protected CardView mCardView2;
        protected TextView mTitle2;
        protected TextView mItem12;
        protected TextView mItem22;
        protected TextView mItem32;
        protected TextView mItem42;

        public ContactViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTitle = (TextView) v.findViewById(R.id.cardview_note_title);
            mItem1 = (TextView) v.findViewById(R.id.note_item1);
            mItem2 = (TextView) v.findViewById(R.id.note_item2);
            mItem3 = (TextView) v.findViewById(R.id.note_item3);
            mItem4 = (TextView) v.findViewById(R.id.note_item4);
            mCardView2 = (CardView) v.findViewById(R.id.card_view2);
            mTitle2 = (TextView) v.findViewById(R.id.cardview_note_title2);
            mItem12 = (TextView) v.findViewById(R.id.note_item12);
            mItem22 = (TextView) v.findViewById(R.id.note_item22);
            mItem32 = (TextView) v.findViewById(R.id.note_item32);
            mItem42 = (TextView) v.findViewById(R.id.note_item42);

        }
    }

}
