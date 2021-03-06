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

import com.example.bil496.R;

public class BlogFragment extends Fragment {

    Dialog addPostScreen;
    EditText postHeader, postDescription;
    Button postButton;
    private BlogViewModel blogViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        blogViewModel =
                ViewModelProviders.of(this).get(BlogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_blog, container, false);

        return root;
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