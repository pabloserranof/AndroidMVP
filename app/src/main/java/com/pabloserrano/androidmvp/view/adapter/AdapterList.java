package com.pabloserrano.androidmvp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.pabloserrano.androidmvp.MyApplication;
import com.pabloserrano.androidmvp.R;
import com.pabloserrano.androidmvp.model.Book;
import com.pabloserrano.androidmvp.view.activity.DetailsScreenActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolderList> {

    @Inject
    Picasso picasso;

    private final List<Book> listBooks;

    public AdapterList(Activity context, List<Book> listItems) {
        ((MyApplication) context.getApplication()).getComponent().inject(this);
        this.listBooks = listItems;
    }

    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_list, parent, false);

        return new ViewHolderList(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderList holder, int position) {
        holder.render(listBooks.get(position));
    }

    @Override
    public int getItemCount() {
        if (listBooks == null) {
            return 0;
        }
        return listBooks.size();
    }

    final class ViewHolderList extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;

        private Book currentBook;

        private final TextView title;
        private final TextView description;
        private final RoundedImageView imageView;

        public ViewHolderList(View view) {
            super(view);

            context = view.getContext();

            title = (TextView) view.findViewById(R.id.itemTitle);
            description = (TextView) view.findViewById(R.id.itemDescription);
            imageView = (RoundedImageView) view.findViewById(R.id.itemImageView);

            view.setOnClickListener(this);
        }

        public void render(Book book) {

            currentBook = book;
            if (currentBook != null) {
                title.setText(currentBook.getTitle());
                description.setText(currentBook.getDescription());
                picasso.load(currentBook.getImageURL()).resizeDimen(R.dimen.book_image_width, R.dimen.book_image_height).into(imageView);
            }
        }

        @Override
        public void onClick(View view) {
            DetailsScreenActivity.startActivity(context, currentBook.getImageURL());
        }
    }
}

