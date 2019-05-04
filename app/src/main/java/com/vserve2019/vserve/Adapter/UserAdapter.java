package com.vserve2019.vserve.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vserve2019.vserve.Listener.OnRecyclerListener;
import com.vserve2019.vserve.Model.History;
import com.vserve2019.vserve.Model.User;
import com.vserve2019.vserve.R;
import com.vserve2019.vserve.UI.LastActivity;
import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {
    Context context;
    int resource;
    ArrayList<User> objects;
    OnRecyclerListener onRecyclerListener;
    public void setRecyclerItemListener(OnRecyclerListener recyclerItemListener){
        this.onRecyclerListener=  recyclerItemListener;
    }
    public UserAdapter(Context context, int resource,ArrayList<User> objects){
        this.context=context;
        this.resource=resource;
        this.objects=objects;
        Log.i("HISTORY","HistoryAdapter Constructor");
    }

    @NonNull
    @Override
    public UserAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(resource,viewGroup,false);
        final UserAdapter.viewHolder holder=new UserAdapter.viewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerListener.OnIemClick(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewHolder viewHolder, int i) {
        User user = objects.get(i);
        viewHolder.txtName.setText(user.Name);
        viewHolder.txtPhone.setText(user.Phone);
        Log.i("HISTORY","BindView");

    }

    @Override
    public int getItemCount() {

        return objects.size();
    }
    class viewHolder extends  RecyclerView.ViewHolder{
        TextView txtName,txtPhone,txtEmail;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.textViewUserName);
            txtPhone=itemView.findViewById(R.id.textViewUserPhone);
                  }
    }
}
