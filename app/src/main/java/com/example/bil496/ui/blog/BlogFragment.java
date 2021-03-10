package com.example.bil496.ui.blog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bil496.R;
import com.example.bil496.forFirebase.Blog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BlogFragment extends Fragment {

    Dialog addPostScreen;
    EditText postHeader, postDescription;
    Button postButton;
    private BlogViewModel blogViewModel;


    private FirebaseRecyclerOptions<Blog> posts;
    private FirebaseRecyclerAdapter<Blog, MyViewHolder> adapter;
    private RecyclerView recyclerView;

    private DatabaseReference reference;
    private String currentUserID;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        blogViewModel =
                ViewModelProviders.of(this).get(BlogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_blog, container, false);


        reference = FirebaseDatabase.getInstance().getReference();
        currentUserID = FirebaseAuth.getInstance().getUid();

        recyclerView = (RecyclerView) root.findViewById(R.id.blogposts);
        displayPosts();

        return root;
    }

    //display blog posts of user
    private void displayPosts() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        posts = new FirebaseRecyclerOptions.Builder<Blog>().setQuery(reference.child("Users").child(currentUserID).child("blog"), Blog.class).build();
        adapter = new FirebaseRecyclerAdapter<Blog, MyViewHolder>(posts) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Blog model) {
                holder.date.setText(model.getDate());
                holder.header.setText(model.getBlogtext().getHeader());
                holder.description.setText(model.getBlogtext().getText());
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout, parent, false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

    }

    public void initAddPostScreen(final View view) {

        addPostScreen = new Dialog(this.getActivity());
        addPostScreen.setContentView(R.layout.add_blog_post);
        addPostScreen.getWindow();
        addPostScreen.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        addPostScreen.getWindow().getAttributes().gravity = Gravity.TOP;

        postHeader = addPostScreen.findViewById(R.id.blogpost_header);
        postDescription = addPostScreen.findViewById(R.id.blogpost_description);
        postButton = addPostScreen.findViewById(R.id.post);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}