package nanodegree.rahall.popularmovies2.customviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import nanodegree.rahall.popularmovies2.R;

/**
 * Created by rahall on 12/9/15.
 */
public class ReviewView extends LinearLayout{

    public ReviewView(Context context,String author,String review) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.review_item, this, true);
        TextView authorTextView = (TextView) view.findViewById(R.id.authorTextView);
        TextView reviewTextView = (TextView) view.findViewById(R.id.reviewTextView);
        authorTextView.setText(author);
        reviewTextView.setText(review);

    }
}
