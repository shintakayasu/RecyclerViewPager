/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lsjwzh.widget.recyclerviewpagerdeomo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lsjwzh.R;
import com.squareup.picasso.Picasso;

public class CheeseListFragment extends Fragment {
    private int mIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_cheese_list, container, false);
        setupRecyclerView(rv);
        if (savedInstanceState == null) {
            mIndex = getArguments().getInt("index");
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setTitle("from arguments:" + mIndex);
        } else {
            mIndex = savedInstanceState.getInt("index");
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setTitle("from savedInstanceState:" + mIndex);
        }
        return rv;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", mIndex);
        Log.d("test", "call onSaveInstanceState:" + mIndex);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                getRandomSublist(30)));
    }

    private List<Cheeses> getRandomSublist(int amount) {
        ArrayList<Cheeses> list = new ArrayList<>(amount);
        while (list.size() < amount) {
            list.add(new Cheeses());
        }
        return list;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Cheeses> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public Cheeses getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<Cheeses> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position).getText();
            holder.mTextView.setText(mValues.get(position).getText());
            holder.mImageView.setTag(holder.hashCode()+""+position);

            holder.mView.setOnClickListener(v -> Toast.makeText(v.getContext(), "Item Clicked!", Toast.LENGTH_SHORT).show());
    
            Picasso.with(holder.mImageView.getContext())
                    .load(mValues.get(position).getImageResId())
                    .centerInside()
                    .fit()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
