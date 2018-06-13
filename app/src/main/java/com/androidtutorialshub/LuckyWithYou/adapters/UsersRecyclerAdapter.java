package com.androidtutorialshub.LuckyWithYou.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.model.User;

/**
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>{

    private User currentuser;

    public UsersRecyclerAdapter(User currentuser) {
        this.currentuser = currentuser;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewName.setText(currentuser.getName());
        holder.textViewEmail.setText(currentuser.getEmail());
        holder.textViewPassword.setText(currentuser.getPassword());
        holder.textViewScore.setText(currentuser.getScore());
        holder.textViewCancerType.setText(currentuser.getCancerType());

    }

    @Override
    public int getItemCount() {
       // Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+currentuser.size());
        return 1;//listUsers.size();
    }

    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder  {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;
        public AppCompatTextView textViewScore;
        public AppCompatTextView textViewCancerType;

        public UserViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewPassword = (AppCompatTextView) view.findViewById(R.id.textViewPassword);
            textViewScore = (AppCompatTextView) view.findViewById(R.id.textViewScore);
            textViewCancerType = (AppCompatTextView) view.findViewById(R.id.textViewCancerType);

        }


    }
}
