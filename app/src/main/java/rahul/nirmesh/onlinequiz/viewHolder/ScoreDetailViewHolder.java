package rahul.nirmesh.onlinequiz.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rahul.nirmesh.onlinequiz.R;

/**
 * Created by NIRMESH on 05-Jan-18.
 */

public class ScoreDetailViewHolder extends RecyclerView.ViewHolder {

    public TextView textCategoryName;
    public TextView textCategoryScore;

    public ScoreDetailViewHolder(View itemView) {
        super(itemView);

        textCategoryName = itemView.findViewById(R.id.textCategoryName);
        textCategoryScore = itemView.findViewById(R.id.textCategoryScore);
    }
}
