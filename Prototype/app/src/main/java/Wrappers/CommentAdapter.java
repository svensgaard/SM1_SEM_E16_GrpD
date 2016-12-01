package Wrappers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Comment;

import grpd.sm1sem.prototype.R;


/**
 * Created by Mads on 29-11-2016.
 */
public class CommentAdapter extends ArrayAdapter<CommentWrapper> {

    Context context;
    int layoutResourceId;
    CommentWrapper[] data;

    public CommentAdapter (Context context, int layoutResourceId, CommentWrapper[] data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        CommentHolder holder = null;

        if (rowView == null) {

            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = vi.inflate(layoutResourceId, null);

            holder = new CommentHolder();
            holder.commentTextView = (TextView) rowView.findViewById(R.id.commentTextView);
            holder.upvoteButton = (Button) rowView.findViewById(R.id.upvoteButton);
            holder.downvoteButton = (Button) rowView.findViewById(R.id.downvoteButton);

            rowView.setTag(holder);
        } else {
            holder = (CommentHolder) rowView.getTag();
        }

        CommentWrapper commentWrapper = data[position];
        holder.commentTextView.setText(commentWrapper.getText());

        return rowView;
    }

    static class CommentHolder {
        TextView commentTextView;
        Button upvoteButton;
        Button downvoteButton;
    }
}
