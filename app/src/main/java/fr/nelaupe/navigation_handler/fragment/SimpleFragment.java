/**
 * Copyright
 */
package fr.nelaupe.navigation_handler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.nelaupe.navigation_handler.R;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public class SimpleFragment extends BaseFragment {

    private int mId;
    private int mDeep;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mId = getArguments().getInt("fragment", 0);
            mDeep = getArguments().getInt("deep", 0);
        } else {
            mId = 0;
            mDeep = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText("Fragment : " + mId + " / Deep : " + mDeep);

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("fragment", mId);
                args.putInt("deep", mDeep + 1);
                navigationHandler().pushContent(new SimpleFragment(), args);
            }
        });
    }
}
