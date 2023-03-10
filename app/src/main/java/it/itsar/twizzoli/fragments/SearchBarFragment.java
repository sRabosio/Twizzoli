package it.itsar.twizzoli.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.itsar.twizzoli.Homepage;
import it.itsar.twizzoli.R;
import it.itsar.twizzoli.Ricerca;
import it.itsar.twizzoli.databinding.FragmentSearchBarBinding;


public class SearchBarFragment extends Fragment {

    private FragmentSearchBarBinding binding;

    public SearchBarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView searchBar = binding.searchBar;
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query == null || query.isEmpty()) return false;
                Intent intent = new Intent(getContext(), Ricerca.class);
                intent.putExtra("ricerca", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        backButton();
    }

    private void backButton() {
        if(getActivity().getClass().equals(Homepage.class)){
            binding.buttonGoback.setVisibility(View.GONE);
            return;
        }
        binding.buttonGoback.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), Homepage.class);
            startActivity(intent);
        });
    }
}