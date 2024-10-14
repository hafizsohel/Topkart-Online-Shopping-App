package com.example.topkartonlineshoppingapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.topkartonlineshoppingapp.Activities.ShowAllActivity;
import com.example.topkartonlineshoppingapp.Adapters.CategoryAdapter;
import com.example.topkartonlineshoppingapp.Adapters.ImageSliderAdapter;
import com.example.topkartonlineshoppingapp.Adapters.MyCartAdapter;
import com.example.topkartonlineshoppingapp.Adapters.NewProductsAdapter;
import com.example.topkartonlineshoppingapp.Adapters.PopularProductsAdapter;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.CategoryModel;
import com.example.topkartonlineshoppingapp.models.MyCartModel;
import com.example.topkartonlineshoppingapp.models.NewProductsModel;
import com.example.topkartonlineshoppingapp.models.PopularProductsModel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {


    TextView catShowAll, popularShowAll, newProductShowAll;

    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView catRecyclerView, newProductRecyclerView, popularRecyclerView;

    //Category recyclerView
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //New Products RecyclerView
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    //Popular Products
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;
    FirebaseAuth auth;
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;

    //FireStore
    FirebaseFirestore db;
    private ViewPager viewPager;
    private LinearLayout indicatorLayout;
    private int[] slideImages = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    private ImageSliderAdapter slideAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        if (getActivity() != null) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.pink));
        }

        progressDialog = new ProgressDialog(getActivity());
        catRecyclerView = root.findViewById(R.id.rec_category);
        newProductRecyclerView = root.findViewById(R.id.new_product_rec);
        popularRecyclerView = root.findViewById(R.id.popular_rec);
        auth = FirebaseAuth.getInstance();

        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);

        viewPager = root.findViewById(R.id.viewPager);
        indicatorLayout = root.findViewById(R.id.indicatorLayout);

        slideAdapter = new ImageSliderAdapter(getContext(), slideImages);
        viewPager.setAdapter(slideAdapter);

        setupIndicator();

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });


        db = FirebaseFirestore.getInstance();

        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);


        //Image slider
        // ImageSlider imageSlider=root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner1, "Discount on Shoes Item", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2, "Discount on Perfume", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, "70%", ScaleTypes.CENTER_CROP));

        //  imageSlider.setImageList(slideModels);

        progressDialog.setTitle("Welcome to Topkart Online Shopping App");
        progressDialog.setMessage("Please wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //Category
        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //New Products
        newProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        newProductRecyclerView.setAdapter(newProductsAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //Popular Products
        popularRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductsModelList);
        popularRecyclerView.setAdapter(popularProductsAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }

    private void setupIndicator() {
        int slideCount = slideAdapter.getCount(); // Get the number of slides from the adapter
        ImageView[] indicators = new ImageView[slideCount];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        // Create arrays for selected and unselected indicator drawables
        int[] selectedIndicatorDrawables = new int[slideCount];
        int[] unselectedIndicatorDrawables = new int[slideCount];
        // Set the selected and unselected indicator drawables based on your requirements
        int selectedIndicatorDrawable = R.drawable.selected_indicator;
        int unselectedIndicatorDrawable = R.drawable.unselected_indicator;
        Arrays.fill(selectedIndicatorDrawables, selectedIndicatorDrawable);
        Arrays.fill(unselectedIndicatorDrawables, unselectedIndicatorDrawable);


        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getContext());
            indicators[i].setImageResource(R.drawable.selected_indicator);
            indicators[i].setLayoutParams(layoutParams);
            indicatorLayout.addView(indicators[i]);

        }

        setCurrentIndicator(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                  setCurrentIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // Start automatic sliding
        final int DELAY_TIME = 3000; // Delay in milliseconds
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = slideAdapter.getCount();
                int nextItem = (currentItem + 1) % totalItems;
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, DELAY_TIME);
            }
        };

        // Start automatic sliding when the view is created
        handler.postDelayed(runnable, DELAY_TIME);
    }

    private void setCurrentIndicator(int position) {
                int childCount = indicatorLayout.getChildCount();
                for (int j = 0; j < childCount; j++) {
                    ImageView imageView = (ImageView) indicatorLayout.getChildAt(j);
                    if (j == position) {
                        imageView.setImageResource(R.drawable.selected_indicator);
                    } else {
                        imageView.setImageResource(R.drawable.unselected_indicator);
                    }
                }
            }
}