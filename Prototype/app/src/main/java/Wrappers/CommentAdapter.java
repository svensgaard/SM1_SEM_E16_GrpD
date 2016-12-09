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

import java.util.ArrayList;

import Database.DatabaseHelper;
import grpd.sm1sem.prototype.R;

import static android.R.attr.button;


/**
 * Created by Mads on 29-11-2016.
 */
public class CommentAdapter extends ArrayAdapter<CommentWrapper> {

    Context context;
    int layoutResourceId;
    ArrayList<CommentWrapper> data;
    private boolean isDownvoted = false;
    private boolean isUpvoted = false;

    public CommentAdapter (Context context, int layoutResourceId, ArrayList<CommentWrapper> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        CommentHolder holder = null;
        final CommentWrapper commentWrapper = data.get(position);
        if (rowView == null) {

            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = vi.inflate(layoutResourceId, null);

            holder = new CommentHolder();
            holder.commentTextView = (TextView) rowView.findViewById(R.id.commentTextView);
            holder.scoreButton = (Button) rowView.findViewById(R.id.scoreButton);
            holder.upvoteButton = (Button) rowView.findViewById(R.id.upvoteCommentButton);
            holder.downvoteButton = (Button) rowView.findViewById(R.id.downvoteCommentButton);
            holder.commentImageView = (ImageView) rowView.findViewById(R.id.imageViewComment);


            rowView.setTag(holder);
        } else {
            holder = (CommentHolder) rowView.getTag();
        }

        holder.commentTextView.setText(commentWrapper.getText());
        holder.scoreButton.setText("" + commentWrapper.getPoints());
        if(commentWrapper.getImage() != null) {
            holder.commentImageView.setImageBitmap(commentWrapper.getImage());
        }
        final Button upvoteButton = holder.upvoteButton;
        upvoteButton.setBackgroundResource(R.color.colorDefaultButton);
        final Button downvoteButton = holder.downvoteButton;
        downvoteButton.setBackgroundResource(R.color.colorDefaultButton);
        final Button scoreButton = holder.scoreButton;
        scoreButton.setBackgroundResource(R.color.colorDefaultButton);

        //Set button listeners
        holder.upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(context);
                commentWrapper.setPoints(dbHelper.upvoteComment(commentWrapper.getId(), commentWrapper.getPoints()));
                Log.d(this.toString(), "Upvoted" + String.valueOf(commentWrapper.getPoints()));
                if (!isDownvoted) {
                    if (!isUpvoted) {
                        commentWrapper.setPoints(dbHelper.upvoteComment(commentWrapper.getId(), commentWrapper.getPoints()));
                        Log.d(this.toString(), "Upvoted" + String.valueOf(commentWrapper.getPoints()));
                        upvoteButton.setBackgroundResource(R.color.colorUpvoteSelected);
                        isUpvoted = true;
                    } else if (isUpvoted) {
                        commentWrapper.setPoints(dbHelper.downvoteComment(commentWrapper.getId(), commentWrapper.getPoints()));
                        upvoteButton.setBackgroundResource(R.color.colorDefaultButton);
                        isUpvoted = false;
                    }
                }
            }
        });
        holder.downvoteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(context);

                if (!isUpvoted) {
                    if (!isDownvoted) {
                        commentWrapper.setPoints(dbHelper.downvoteComment(commentWrapper.getId(), commentWrapper.getPoints()));
                        Log.d(this.toString(), "Downvoted" + String.valueOf(commentWrapper.getPoints()));
                        downvoteButton.setBackgroundResource(R.color.colorDownvoteSelected);
                        isDownvoted = true;
                    } else if (isDownvoted) {
                        commentWrapper.setPoints(dbHelper.upvoteComment(commentWrapper.getId(), commentWrapper.getPoints()));
                        downvoteButton.setBackgroundResource(R.color.colorDefaultButton);
                        isDownvoted = false;
                    }
                }
            }
        });

        return rowView;
    }
    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }


    static class CommentHolder {
        TextView commentTextView;
        Button scoreButton;
        ImageView commentImageView;
        Button upvoteButton;
        Button downvoteButton;
    }
}
