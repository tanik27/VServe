package com.vserve2019.vserve.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vserve2019.vserve.Listener.OnRecyclerListener;
import com.vserve2019.vserve.Model.Services;
import com.vserve2019.vserve.R;
import java.util.ArrayList;
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.viewHolder> {
    Context context;
    int resource;
    ArrayList<Services> objects;
    OnRecyclerListener recyclerListener;
    public void  setOnRecyclerItemListener(OnRecyclerListener recyclerItemListener) {
        this.recyclerListener = recyclerItemListener;
    }
    public ServiceAdapter(Context context,int resource,ArrayList<Services> objects){
        this.context= context;
        this.resource=resource;
        this.objects=objects;
    }
    @NonNull
    @Override
    public ServiceAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(context).inflate(resource,viewGroup,false);
       final ServiceAdapter.viewHolder holder=new ServiceAdapter.viewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerListener.OnIemClick(holder.getAdapterPosition());
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.viewHolder viewHolder, int position) {
        Services services=objects.get(position);
        viewHolder.txtName.setText(services.Name);
        viewHolder.txtPrice.setText(services.Price);
    }
    @Override
    public int getItemCount() {
        return objects.size();
    }
    class  viewHolder extends RecyclerView.ViewHolder{
        TextView txtName,txtPrice;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.textViewServiceName);
            txtPrice=itemView.findViewById(R.id.textViewServicePrice);
        }
    }
}
