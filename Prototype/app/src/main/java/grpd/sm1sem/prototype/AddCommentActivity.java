package grpd.sm1sem.prototype;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import Database.DatabaseHelper;
import Wrappers.CommentWrapper;

public class AddCommentActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PHOTO = 100;
    public static final String EXTRAS_ID = "ID";

    private ImageView commentImageView;
    private EditText commentText;
    private int reportID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        //Get id of report
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            reportID = extras.getInt(EXTRAS_ID);
        }

        //Get views
        commentImageView = (ImageView) this.findViewById(R.id.commentImageView);
        commentText = (EditText) this.findViewById(R.id.commentEditText);
        Button takePictureButton = (Button) this.findViewById(R.id.takPictureButton);
        Button uploadPictureButton = (Button) this.findViewById(R.id.uploadButton);
        Button okButton = (Button) this.findViewById(R.id.okButton);

        //Add button listeners
        assert takePictureButton != null;
        takePictureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        assert uploadPictureButton != null;
        uploadPictureButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }

        });

        assert okButton != null;
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(AddCommentActivity.this);
                if(commentImageView.getDrawable() != null) {
                    Bitmap commentImage = ((BitmapDrawable)commentImageView.getDrawable()).getBitmap();
                    dbHelper.insertComment(dbHelper.getWritableDatabase() , new CommentWrapper(0, commentText.getText().toString(), commentImage, 0, reportID));
                    finish();
                } else {
                    dbHelper.insertComment(dbHelper.getWritableDatabase() , new CommentWrapper(0, commentText.getText().toString(), 0, reportID));
                    finish();
                }

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            commentImageView.setImageBitmap(photo);
        } else if(requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap commentImage = BitmapFactory.decodeStream(imageStream);
            commentImageView.setImageBitmap(commentImage);// To display selected image in image view
        }
    }
}
