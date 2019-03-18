package datafiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.swiftqube.soccergist.R;

public class MessageAdapter extends FirestoreRecyclerAdapter<Message, MessageAdapter.MessageHolder>{
    private final String TAG = "MessageAdaper";
    Context context;
    String userId;

    public MessageAdapter(@NonNull Context context, Query query, String userID) {
        /*
        Configure recycler adapter options:
        query defines the request made to Firestore
        Message.class instructs the adapter to convert each DocumentSnapshot to a Message object
        */
        super(new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build());

        Log.i(TAG, "MessageAdapter: created");
        this.context = context;
        this.userId = userID;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull Message model) {
        Log.i(TAG, "onBindViewHolder: executed");
        final TextView mText = holder.mText;
        final TextView mUsername = holder.mUsername;
        final TextView mTime = holder.mTime;
        final TextView mLikesCount = holder.mLikesCount;
        final ImageView imgProfile = holder.imgProfile;
        final ImageView imgDropdown = holder.imgDropdown;
        final ImageView imgLikes = holder.imgLikes;

        mText.setText(model.getMessageText());
        mUsername.setText(model.getMessageUser());

    }

    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: created");
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.message for each item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mssg, parent, false);
        return new MessageHolder(view);
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        TextView mText;
        TextView mUsername;
        TextView mTime;
        TextView mLikesCount;
        ImageView imgProfile, imgDropdown, imgLikes;
        public MessageHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.message_text);
            mUsername = itemView.findViewById(R.id.message_user);
            mTime = itemView.findViewById(R.id.message_time);
            mLikesCount = itemView.findViewById(R.id.message_Likes);
            imgProfile = itemView.findViewById(R.id.imgDps);
            imgLikes = itemView.findViewById(R.id.imgLikes);
            imgDropdown = itemView.findViewById(R.id.imgDropdwon);
        }
    }
}