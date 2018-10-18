package com.lsjwzh.widget.recyclerviewpagerdeomo;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lsjwzh.R;
import com.lsjwzh.databinding.ListItemBinding;

public class InnerRVListAdapter extends ListAdapter<Cheeses, InnerRVListAdapter.ViewHolder> {

	private static final DiffUtil.ItemCallback<Cheeses> diffCallback = new DiffUtil.ItemCallback<Cheeses>() {
		@Override
		public boolean areItemsTheSame(@NonNull Cheeses cheeses, @NonNull Cheeses t1) {
			return false;
		}
		
		@Override
		public boolean areContentsTheSame(@NonNull Cheeses cheeses, @NonNull Cheeses t1) {
			return false;
		}
	};
	
	protected InnerRVListAdapter() {
		super(diffCallback);
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		return new ViewHolder(
				DataBindingUtil.inflate(
						LayoutInflater.from(viewGroup.getContext()),
						R.layout.list_item,
						viewGroup,
						false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
		ListItemBinding listItemBinding = viewHolder.getListItemBinding();
		listItemBinding.setViewData(getItem(i));
	}
	
	
	class ViewHolder extends RecyclerView.ViewHolder{
		
		ListItemBinding listItemBinding;
		
		public ViewHolder(ListItemBinding listItemBinding) {
			super(listItemBinding.getRoot());
			this.listItemBinding = listItemBinding;
		}
		
		public ListItemBinding getListItemBinding() {
			return listItemBinding;
		}
	}
}
