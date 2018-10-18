/*
 * Copyright (C) 2015 lsjwzh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lsjwzh.widget.recyclerviewpagerdeomo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsjwzh.R;
import com.lsjwzh.adapter.GenericRecyclerViewAdapter;
import com.lsjwzh.adapter.OnRecyclerViewItemClickListener;


public class MainActivity extends AppCompatActivity {

    RecyclerView mDemoRecyclerView;
    private DemoListAdapter mDemoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_list);
        mDemoRecyclerView = findViewById(R.id.demo_list);
        mDemoRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager
                .VERTICAL));
        mDemoListAdapter = new DemoListAdapter();
        mDemoRecyclerView.setAdapter(mDemoListAdapter);
        mDemoListAdapter.add(new DemoItem("Single Fling Pager(like official ViewPager)") {
            @Override
            void onClick() {
                startActivity(new Intent(MainActivity.this, SingleFlingPagerActivity.class));
            }
        });
        mDemoListAdapter.add(new DemoItem("Vertical ViewPager Demo") {
            @Override
            void onClick() {
                startActivity(new Intent(MainActivity.this, VerticalPagerActivity.class));
            }
        });
        mDemoListAdapter.add(new DemoItem("Material Demo With Fragment") {
            @Override
            void onClick() {
                startActivity(new Intent(MainActivity.this, FragmentMaterialDemoActivity.class));
            }
        });
        mDemoListAdapter.add(new DemoItem("Material Demo Without Fragment (Experimental Implementation)") {
            @Override
            void onClick() {
                startActivity(new Intent(MainActivity.this, MaterialDemoActivity.class));
            }
        });
		mDemoListAdapter.add(new DemoItem("Tmp") {
			@Override
			void onClick() {
				startActivity(new Intent(MainActivity.this, TmpActivity.class));
			}
		});
    }


    class DemoListAdapter extends GenericRecyclerViewAdapter<DemoItem, DemoListItemViewHolder> {

        DemoListAdapter() {
            setOnItemClickListener(new OnRecyclerViewItemClickListener<DemoListItemViewHolder>() {
                @Override
                public void onItemClick(View view, int position, DemoListItemViewHolder viewHolder) {
                    getItem(position).onClick();
                }
            });
        }

        @Override
        public DemoListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.demo_list_item, parent, false);
            return new DemoListItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DemoListItemViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            holder.mTextView.setText(getItem(position).mText);
        }
    }

    class DemoItem {
        String mText;

        DemoItem(String text) {
            mText = text;
        }

        void onClick() {
        }
    }

    class DemoListItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public DemoListItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text);
        }
    }

}
