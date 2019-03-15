package datafiles;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.swiftqube.soccergist.R;

public class MessageAdapter extends FirestoreRecyclerAdapter<Message, MessageAdapter.MessageHolder>{


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
            .setQuery(query, Chat.class)
            .build();

    public MessageAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull Message model) {

    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        TextView mText;
        TextView mUser;
        TextView mTime;
        TextView mLikesCount;
        ImageView imgProfile, imgDropdown, imgLikes;
        public MessageHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.message_text);
            mUser = itemView.findViewById(R.id.message_user);
            mTime = itemView.findViewById(R.id.message_time);
            mLikesCount = itemView.findViewById(R.id.message_Likes);
            imgProfile = itemView.findViewById(R.id.imgDps);
            imgLikes = itemView.findViewById(R.id.imgLikes);
            imgDropdown = itemView.findViewById(R.id.imgDropdwon);
        }
    }
}