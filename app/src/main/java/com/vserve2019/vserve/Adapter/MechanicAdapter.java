package com.vserve2019.vserve.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vserve2019.vserve.Listener.OnRecyclerListener;
import com.vserve2019.vserve.Model.Mechanic;
import com.vserve2019.vserve.R;
import java.util.ArrayList;
public class MechanicAdapter extends RecyclerView.Adapter<MechanicAdapter.viewHolder> {
    Context context;
    int resource;
    ArrayList<Mechanic> objects;
    OnRecyclerListener recyclerItemListener;
    public void setOnRecyclerItemListener(OnRecyclerListener recyclerItemListener){
        this.recyclerItemListener=  recyclerItemListener;
    }
    public MechanicAdapter(Context context, int resource,ArrayList<Mechanic> objects){
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(resource,parent,false);
       final MechanicAdapter.viewHolder holder=new MechanicAdapter.viewHolder(view);
       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               recyclerItemListener.OnIemClick(holder.getAdapterPosition());
           }
       });
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Mechanic mechanic=objects.get(position);
        holder.txtName.setText(mechanic.Name);
        holder.txtPhone.setText(mechanic.Phone);
        holder.txtAddress.setText(mechanic.Address);
    }
    @Override
    public int getItemCount() {

        return objects.size();
    }
    class viewHolder extends  RecyclerView.ViewHolder{

        TextView txtName,txtPhone,txtAddress;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
          txtName=itemView.findViewById(R.id.textViewMName);
          txtPhone=itemView.findViewById(R.id.textViewMPhone);
          txtAddress=itemView.findViewById(R.id.textViewMAddress);
        }
    }
}
