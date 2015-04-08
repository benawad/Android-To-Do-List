package benawad.com.todolist.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import benawad.com.todolist.NoteActivity;
import benawad.com.todolist.R;

/**
 * Created by Ben on 3/25/2015.
 */
public class ItemsArrayAdapter extends ArrayAdapter<String> {

    public static final String TAG = ItemsArrayAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<String> mArrayList;
    ArrayList<String> mSlashes;
    NoteActivity mNoteActivity;

    final int INVALID_ID = -1;

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public ItemsArrayAdapter
            (NoteActivity context, ArrayList<String> arrayList, ArrayList<String> slashes){
        super(context, R.layout.item_row,arrayList);
        mContext = context;
        mArrayList = arrayList;
        mNoteActivity = context;
        mSlashes = slashes;
        for (int i = 0; i < arrayList.size(); ++i) {
            mIdMap.put(arrayList.get(i), i);
        }
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_row, parent, false);
            holder = new ViewHolder();

            holder.itemName = (TextView)convertView.findViewById(R.id.itemText);

            for (int i = 0; i < mArrayList.size(); ++i) {
                mIdMap.put(mArrayList.get(i), i);
            }

//            holder.delete = (ImageView)convertView.findViewById(R.id.delete_item);
//            holder.edit = (ImageView)convertView.findViewById(R.id.edit_item);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemName.setText(mArrayList.get(position));
        if( Integer.parseInt(mSlashes.get(position)) == NoteActivity.SLASHED){
            holder.itemName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mNoteActivity.deleteItem(position);
//            }
//        });
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mNoteActivity.editItem(position);
//            }
//        });


        return convertView;
    }

    private static class ViewHolder {
        TextView itemName;
        ImageView edit;
        ImageView delete;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
