package com.example.ecommerceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryCarouselFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryCarouselFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CarouselView carouselView;
    private int[] imagesList = {
            R.drawable.pc_gamer,
            R.drawable.xbox_one_s,
            R.drawable.portatil_asus
    };

    public CategoryCarouselFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryCarouselFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryCarouselFragment newInstance(String param1, String param2) {
        CategoryCarouselFragment fragment = new CategoryCarouselFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_category_carousel, container, false);

//        int width = 150, height = 150;
//
//        for (int image:imagesList) {
//
//        }
        
        carouselView = root.findViewById(R.id.carouselView);
        carouselView.setPageCount(imagesList.length);
        carouselView.setImageListener(imageListener);

        return root;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(imagesList[position]);
        }
    };
}