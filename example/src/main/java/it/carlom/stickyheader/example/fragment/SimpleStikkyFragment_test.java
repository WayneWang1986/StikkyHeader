package it.carlom.stickyheader.example.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import it.carlom.stickyheader.example.R;
import it.carlom.stickyheader.example.Utils;
import it.carlom.stikkyheader.core.StikkyHeaderRootLayout;
import it.carlom.stikkyheader.core.StikkyHeaderBuilder;

public class SimpleStikkyFragment_test extends Fragment {

    private ListView mListView;

    private TextView mText1, mText2;

    private StikkyHeaderRootLayout mRoot;
    private ViewGroup mHeaderView;

    public SimpleStikkyFragment_test() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simplelistview_test, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRoot = (StikkyHeaderRootLayout) view.findViewById(R.id.layout_container);
        mListView = (ListView) view.findViewById(R.id.listview);
        mText1 = (TextView) view.findViewById(R.id.text1);
        mText2 = (TextView) view.findViewById(R.id.text2);
        mHeaderView = (ViewGroup) view.findViewById(R.id.header);
        mRoot.addClickCandidates(mHeaderView);
        mRoot.setScrollableView(mListView);

        mText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "text1 clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "text2 clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StikkyHeaderBuilder.stickTo(mListView)
                .setHeader(R.id.header, (ViewGroup) getView())
                .minHeightHeader(0)
                .build();

        Utils.populateListView(mListView);
    }
}
