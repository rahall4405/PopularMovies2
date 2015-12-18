package nanodegree.rahall.popularmovies2.customviews;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import nanodegree.rahall.popularmovies2.R;

/**
 * Created by rahall on 12/9/15.
 */
public class TrailorView extends LinearLayout {
    ImageButton imageTrailorButton;
    TextView trailorTextView;
    String id;
    Context context;
    public TrailorView(Context context1,String id1, String trailorText) {
        super(context1);

        id = id1;
        context = context1;
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.trailor_item, this, true);
        imageTrailorButton = (ImageButton) view1.findViewById(R.id.imageTrailorButton);
        trailorTextView = (TextView) view1.findViewById(R.id.trailorText);
        trailorTextView.setText(trailorText);

        imageTrailorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriParseStringBrowser = "http://www.youtube.com/watch?v=" + id;
                String uriParseString = "vnd.youtube:" + id;
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriParseString));
                    intent.putExtra("force_fullscreen", true);
                    context.startActivity(intent);
                } catch (ActivityNotFoundException a) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriParseStringBrowser));
                    context.startActivity(intent);
                }
            }

        });

    }
}
