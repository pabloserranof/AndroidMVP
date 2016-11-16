package com.pabloserrano.androidmvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pabloserrano.androidmvp.MyApplication;
import com.pabloserrano.androidmvp.R;
import com.pabloserrano.androidmvp.model.Book;
import com.pabloserrano.androidmvp.presentation.MainPresenter;
import com.pabloserrano.androidmvp.view.MainView;
import com.pabloserrano.androidmvp.view.adapter.AdapterGrid;
import com.pabloserrano.androidmvp.view.adapter.AdapterList;

import java.util.List;

import javax.inject.Inject;

import static com.pabloserrano.androidmvp.view.fragment.MainFragment.LayoutManagerType.GRID_LAYOUT_MANAGER;
import static com.pabloserrano.androidmvp.view.fragment.MainFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;

public class MainFragment extends Fragment implements MainView {

    @Inject
    MainPresenter userPresenter;

    public enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    private static final int SPAN_COUNT = 2;

    private LayoutManagerType currentLayoutManagerType;

    private List<Book> listBooks;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter currentAdapter;
    private RecyclerView.Adapter gridAdapter;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) getActivity().getApplication()).getComponent().inject(this);
        userPresenter.setView(this);
    }

    public void setLayoutManager(LayoutManagerType layoutManagerType) {
        currentLayoutManagerType = layoutManagerType;
        setRecyclerViewLayoutManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        currentLayoutManagerType = LINEAR_LAYOUT_MANAGER;

        return fragmentView;
    }

    private void setRecyclerViewLayoutManager() {

        int scrollPosition = 0;
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (currentLayoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                currentLayoutManagerType = GRID_LAYOUT_MANAGER;
                if (gridAdapter == null) {
                    gridAdapter = new AdapterGrid(getActivity(), listBooks);
                }
                currentAdapter = gridAdapter;
                break;
            case LINEAR_LAYOUT_MANAGER:
                layoutManager = new LinearLayoutManager(getActivity());
                currentLayoutManagerType = LINEAR_LAYOUT_MANAGER;
                if (listAdapter == null) {
                    listAdapter = new AdapterList(getActivity(), listBooks);
                }
                currentAdapter = listAdapter;
                break;
            default:
                layoutManager = new LinearLayoutManager(getActivity());
                currentLayoutManagerType = LINEAR_LAYOUT_MANAGER;
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(currentAdapter);
        recyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void loadBooks(List<Book> listBooks) {
        this.listBooks = listBooks;
        currentAdapter = new AdapterList(getActivity(), listBooks);
        recyclerView.setAdapter(currentAdapter);
    }

    @Override
    public void showBooksNotFound() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
    }
}
