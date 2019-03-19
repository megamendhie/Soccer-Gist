package datafiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.swiftqube.soccergist.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends FirestoreRecyclerAdapter<Message, MessageAdapter.MessageHolder>{
    private final String TAG = "MessageAdaper";
    Context context;
    String userId;
    StorageReference storageReference;
    private RequestOptions requestOptions = new RequestOptions();
    private final int MESSAGE_IN_VIEW_TYPE  = 1;
    private final int MESSAGE_OUT_VIEW_TYPE = 2;

    public MessageAdapter(Query query, String userID, Context context) {
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
        requestOptions.placeholder(R.drawable.ic_account_circle_black_24dp);
        storageReference = FirebaseStorage.getInstance().getReference()
                .child("profile_images");
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull Message model) {
        Log.i(TAG, "onBindViewHolder: executed");
        final TextView mText = holder.mText;
        final TextView mUsername = holder.mUsername;
        final TextView mTime = holder.mTime;
        final TextView mLikesCount = holder.mLikesCount;
        final CircleImageView imgProfile = holder.imgProfile;
        final ImageView imgDropdown = holder.imgDropdown;
        final ImageView imgLikes = holder.imgLikes;

        mUsername.setText(model.getMessageUser());
        mText.setText(model.getMessageText());
        mTime.setText(DateFormat.format("dd MMM  (h:mm a)", model.getMessageTime()));
        mLikesCount.setText(String.valueOf(model.getMessageLikesCount()));
        if(model.getMessageLikes()!=null){
            if(model.getMessageLikes().containsValue(userId)){
                imgLikes.setImageResource(R.drawable.ic_favorite_red_24dp);
            }
            else{
                imgLikes.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
        }
        GlideApp.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(storageReference.child(model.getMessageUserId()))
                .into(imgProfile);

    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).getMessageUserId().equals(userId)){
            return MESSAGE_OUT_VIEW_TYPE;
        }
        return MESSAGE_IN_VIEW_TYPE;
    }

    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        We're using two custom layouts. One for message in and other for message out
         */
        View view = null;
        if(viewType==MESSAGE_IN_VIEW_TYPE){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mssg, parent, false);
        }
        else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mssg_out, parent, false);
        }
        return new MessageHolder(view);
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        TextView mText;
        TextView mUsername;
        TextView mTime;
        TextView mLikesCount;
        CircleImageView imgProfile;
        ImageView imgDropdown, imgLikes;
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