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
import com.vserve2019.vserve.R;
import java.util.ArrayList;
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder> {
    Context context;
    int resource;
    ArrayList<History> objects;
    OnRecyclerListener recyclerItemListener;
    public void setRecyclerItemListener(OnRecyclerListener recyclerItemListener){
        this.recyclerItemListener=  recyclerItemListener;
    }
    public HistoryAdapter(Context context, int resource,ArrayList<History> objects){
        this.context=context;
        this.resource=resource;
        this.objects=objects;
        Log.i("HISTORY","HistoryAdapter Constructor");
    }
    @NonNull
    @Override
    public HistoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(resource,viewGroup,false);
        final HistoryAdapter.viewHolder holder=new HistoryAdapter.viewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemListener.OnIemClick(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.viewHolder viewHolder, int i) {
        History history = objects.get(i);
        viewHolder.txtLocation.setText(history.Location);
        viewHolder.txtMechanicName.setText(history.MechanicName);
        viewHolder.txtMechanicPhone.setText(history.MechanicPhone);
        viewHolder.txtMechanicAddress.setText(history.MechanicAddress);
        Log.i("HISTORY","BindView");
        viewHolder.txtService.setText(history.Service);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
    class viewHolder extends  RecyclerView.ViewHolder{
        TextView txtLocation,txtMechanicName,txtMechanicPhone,txtMechanicAddress,txtService;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtLocation=itemView.findViewById(R.id.textViewLocationHistory);
            txtMechanicName=itemView.findViewById(R.id.textViewMechanicNameHistory);
            txtMechanicPhone=itemView.findViewById(R.id.textViewMechanicPhoneHistory);
            txtMechanicAddress=itemView.findViewById(R.id.textViewMechanicAddressHistory);
            txtService=itemView.findViewById(R.id.textViewServiceHistory);
        }
    }
}
