package rahul.nirmesh.onlinequiz.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rahul.nirmesh.onlinequiz.Interface.ItemClickListener;
import rahul.nirmesh.onlinequiz.R;

/**
 * Created by NIRMESH on 05-Jan-18.
 */

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textName, textScore;

    private ItemClickListener itemClickListener;

    public RankingViewHolder(View itemView) {
        super(itemView);

        textName = itemView.findViewById(R.id.textName);
        textScore = itemView.findViewById(R.id.textScore);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
