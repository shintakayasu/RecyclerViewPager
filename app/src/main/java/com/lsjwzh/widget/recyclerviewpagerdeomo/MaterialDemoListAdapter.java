package com.lsjwzh.widget.recyclerviewpagerdeomo;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lsjwzh.R;
import com.lsjwzh.databinding.PagerItemBinding;
import com.lsjwzh.widget.recyclerviewpager.TabLayoutSupport;

import java.util.ArrayList;
import java.util.List;

public class MaterialDemoListAdapter extends ListAdapter<PagerItemViewModel, MaterialDemoListAdapter.ViewHolder>
		implements TabLayoutSupport.ViewPagerTabLayoutAdapter{
	
	private static final String TAG = MaterialDemoListAdapter.class.getSimpleName();
	private final LifecycleOwner lifecycleOwner;
	
	private static final DiffUtil.ItemCallback<PagerItemViewModel> diffCallback = new DiffUtil.ItemCallback<PagerItemViewModel>() {
		@Override
		public boolean areItemsTheSame(@NonNull PagerItemViewModel itemViewData, @NonNull PagerItemViewModel t1) {
			return false;
		}
		
		@Override
		public boolean areContentsTheSame(@NonNull PagerItemViewModel itemViewData, @NonNull PagerItemViewModel t1) {
			return false;
		}
	};
	
	
	
	public MaterialDemoListAdapter(LifecycleOwner lifecycleOwner) {
		super(diffCallback);
		this.lifecycleOwner = lifecycleOwner;
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//			return new ViewHolder(DataBindingUtil.setContentView(activity, R.layout.list_item));
		return new ViewHolder(
				DataBindingUtil.inflate(
						LayoutInflater.from(viewGroup.getContext()),
						R.layout.pager_item,
						viewGroup,
						false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
		PagerItemBinding itemBinding = viewHolder.getPagerItemBinding();
		
		PagerItemViewModel pagerItemViewModel = getItem(i);
		itemBinding.setViewModel(pagerItemViewModel);
		
		itemBinding.getRoot().setOnClickListener(v -> {
			Log.d(TAG, "pos= "+ i + "  itemCount= " + itemBinding.cheeseRecyclerView.getAdapter().getItemCount());
			
			List<Cheeses> tmpList = pagerItemViewModel.getCheeseListLiveData().getValue();
			tmpList.add(new Cheeses());
			MutableLiveData<List<Cheeses>> tmpLiveData = new MutableLiveData<>();
			tmpLiveData.setValue(tmpList);
			pagerItemViewModel.setCheeseListLiveData(tmpLiveData);
		});
		
		InnerRVListAdapter innerAdapter = new InnerRVListAdapter();
		if(pagerItemViewModel.getCheeseListLiveData().getValue() != null) {
			innerAdapter.submitList(pagerItemViewModel.getCheeseListLiveData().getValue());
		}
		itemBinding.cheeseRecyclerView.setAdapter(innerAdapter);
		
		pagerItemViewModel.getCheeseListLiveData().observe(lifecycleOwner, cheeses -> {
			if (cheeses != null) {
				Log.d(TAG, "pos= "+ i + "  cheeses size= " + cheeses.size());
				innerAdapter.submitList(cheeses);
			}
		});
		
		
	}
	
	@Override
	public String getPageTitle(int position) {
		return "hoge:" + position;
	}
	
	class ViewHolder extends RecyclerView.ViewHolder{
		
		PagerItemBinding pagerItemBinding;
		
		public ViewHolder(PagerItemBinding listItemBinding) {
			super(listItemBinding.getRoot());
			this.pagerItemBinding = listItemBinding;
		}
		
		public PagerItemBinding getPagerItemBinding() {
			return pagerItemBinding;
		}
	}
}