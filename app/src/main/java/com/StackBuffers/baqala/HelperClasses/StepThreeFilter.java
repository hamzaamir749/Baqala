package com.StackBuffers.baqala.HelperClasses;

import com.StackBuffers.baqala.Adapters.StepThreeAdapter;
import com.StackBuffers.baqala.ModelClasses.StepThreeModel;

import java.util.ArrayList;
import java.util.List;

import android.widget.Filter;

public class StepThreeFilter extends Filter {
    StepThreeAdapter adapter;
    public List<StepThreeModel> filterlist;

    public StepThreeFilter(StepThreeAdapter adapter, List<StepThreeModel> filterlist) {
        this.adapter = adapter;
        this.filterlist = filterlist;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString();
            List<StepThreeModel> filteredList = new ArrayList<>();
            for (StepThreeModel u : filterlist) {
                if (u.getName().toLowerCase().contains(constraint) || u.getBarcode().contains(constraint)) {
                    filteredList.add(u);
                }
            }
            results.count = filteredList.size();
            results.values = filteredList;
            return results;

        }
        results.count = filterlist.size();
        results.values = filterlist;
        return results;

    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.list = (List<StepThreeModel>) results.values;
        adapter.notifyDataSetChanged();
    }
}
