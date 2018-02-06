package com.bigzindustries.brochefbakercompanion.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bigzindustries.brochefbakercompanion.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProTipsActivity extends KeepScreenOnActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_protips);

        ListView list;
        List<Map<String, String>> data = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.protip_titles);
        String[] paragraphs = getResources().getStringArray(R.array.protip_paragraphs);

        for (int i = 0; i < titles.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", titles[i]);
            map.put("paragraph", paragraphs[i]);
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.pro_tip_item,
                new String[] {"title", "paragraph"},
                new int[] {R.id.title, R.id.paragraph}
        );

        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
    }
}
