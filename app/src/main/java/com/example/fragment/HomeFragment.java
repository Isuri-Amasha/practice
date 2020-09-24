package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private View homeView;
    private RecyclerView home;

    private DatabaseReference homeref;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_home,container,false);

        home = (RecyclerView) homeView.findViewById(R.id.recycler_home);
        home.setLayoutManager(new LinearLayoutManager(getContext()));

        homeref = FirebaseDatabase.getInstance().getReference().child("Category");

        return homeView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Category>().setQuery(homeref,Category.class).build();

        FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull Category model) {

                holder.category_name.setText(model.getName());
                Glide.with(holder.category_image.getContext()).load(model.getImageUrl()).into(holder.category_image);
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
                CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
                return categoryViewHolder;
            }
        };

        home.setAdapter(adapter);
        adapter.startListening();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        TextView category_name;
        ImageView category_image;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            category_name = (TextView)itemView.findViewById(R.id.category_name);
            category_image = (ImageView) itemView.findViewById(R.id.category_image);

        }
    }
}
