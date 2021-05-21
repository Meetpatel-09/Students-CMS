package com.example.studentscms.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.studentscms.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {

    private ViewPager viewPager;
    private BranchAdapter adapter;
    private List<BranchModel> list;

    private ImageView map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        map = view.findViewById(R.id.map);

        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.civil, "Civil Engineering", "The department of Civil Engineering established in 2006 with intake capacity of 60 students. The department is having well equipped and furnished labs as per norms laid down by Gujarat State Board of Technical Education Gandhinagar."));
        list.add(new BranchModel(R.drawable.computer, "Computer Engineering", "The department will be center of excellence in the area of Computer Engineering to meet the needs of industry and society."));
        list.add(new BranchModel(R.drawable.mechanical, "Mechanical Engineering", "Mechanical Department is established in the year of 2006. We provide an education that builds within students a solid foundation in mechanical engineering principles, expands the reasoning, communication and problem solving abilities of students."));

        adapter = new BranchAdapter(getContext(), list);

        viewPager = view.findViewById(R.id.view_pager);

        viewPager.setAdapter(adapter);

        ImageView imageView = view.findViewById(R.id.college_image);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/admin-ems-25fe6.appspot.com/o/campus.jpg?alt=media&token=6ef5a496-15f2-4afd-a1f9-66d756733cdd").into(imageView);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

        return view;
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q=Government Polytechnic, Waghai");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        Intent chooser = Intent.createChooser(intent, "Launch Maps");
        startActivity(chooser);
    }
}