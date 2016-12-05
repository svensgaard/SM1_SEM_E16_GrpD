package Wrappers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Comment;

import Database.DatabaseHelper;
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
        final CommentWrapper commentWrapper = data[position];
        if (rowView == null) {

            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = vi.inflate(layoutResourceId, null);

            holder = new CommentHolder();
            holder.commentTextView = (TextView) rowView.findViewById(R.id.commentTextView);
            holder.scoreTextView = (TextView) rowView.findViewById(R.id.scoreTextView);
            holder.upvoteButton = (Button) rowView.findViewById(R.id.upvoteCommentButton);
            holder.downvoteButton = (Button) rowView.findViewById(R.id.downvoteCommentButton);
            holder.commentImageView = (ImageView) rowView.findViewById(R.id.imageViewComment);


            rowView.setTag(holder);
        } else {
            holder = (CommentHolder) rowView.getTag();
        }

        holder.commentTextView.setText(commentWrapper.getText());
        holder.scoreTextView.setText("Score: " + commentWrapper.getPoints());
        if(commentWrapper.getImage() != null) {
            holder.commentImageView.setImageBitmap(commentWrapper.getImage());
        }
        //Set button listeners
        holder.upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(context);
                commentWrapper.setPoints(dbHelper.upvoteComment(commentWrapper.getId(), commentWrapper.getPoints()));
                Log.d(this.toString(), "Upvoted" + String.valueOf(commentWrapper.getPoints()));
            }
        });
        holder.downvoteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(context);
                commentWrapper.setPoints(dbHelper.downvoteComment(commentWrapper.getId(), commentWrapper.getPoints()));
                Log.d(this.toString(), "Downvoted" + String.valueOf(commentWrapper.getPoints()));
            }
        });

        return rowView;
    }
    @Override
    public int getCount() {
        return data == null ? 0 : data.length;
    }


    static class CommentHolder {
        TextView commentTextView;
        TextView scoreTextView;
        ImageView commentImageView;
        Button upvoteButton;
        Button downvoteButton;
    }
}
