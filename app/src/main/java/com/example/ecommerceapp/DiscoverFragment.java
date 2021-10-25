package com.example.ecommerceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.model.CarouselType;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
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

//    private Button btnNextCategory, btnPreviousCategory;
//    private EditText etSearch;
    private ImageCarousel carousel;
    private List<CarouselItem> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_discover, container, false);

        //My logic
//        etSearch = root.findViewById(R.id.etSearchDiscover);
//        btnPreviousCategory = root.findViewById(R.id)

        carousel = root.findViewById(R.id.category_carousel);

        // Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragments it will be viewLifecycleOwner/getViewLifecycleOwner().
        carousel.registerLifecycle(getViewLifecycleOwner());

        list = new ArrayList<>();

        list.add(
          new CarouselItem(
                  "https://d2r9epyceweg5n.cloudfront.net/stores/001/089/364/products/equipo-power-cl-l181-4bbf0f95a331b8d7a416151472100801-640-0.png",
                  "image1"
          )
        );

        list.add(
                new CarouselItem(
                        "https://d2r9epyceweg5n.cloudfront.net/stores/001/089/364/products/camara-metalica-para-exterior-dahua-wifi1-ef2ffa79f0a5157a0116043464987493-640-0.png",
                        "image2"
                )
        );

        list.add(
                new CarouselItem(
                        "https://d2r9epyceweg5n.cloudfront.net/stores/001/089/364/products/xbox-one-s-1tb-1to1-7ae14a7ceb96517e7716151549524220-640-0.jpg",
                        "image3"
                )
        );

        carousel.addData(list);

        carousel.setAutoPlay(true);
        carousel.setAutoPlayDelay(3000);
//        carousel.setCarouselType(CarouselType.SHOWCASE);

        return root;
    }
}