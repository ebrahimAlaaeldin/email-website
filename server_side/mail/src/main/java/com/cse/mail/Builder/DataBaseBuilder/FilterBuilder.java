package com.cse.mail.Builder.DataBaseBuilder;

import com.cse.mail.dal.model.Filter;
import com.cse.mail.dal.model.Folder;

public class FilterBuilder {

    private Folder folder;
    private String filterMode;
    private String filterData;

    public FilterBuilder setFolder(Folder folder) {
        this.folder = folder;
        return this;
    }

    public FilterBuilder setFilterMode(String filterMode) {
        this.filterMode = filterMode;
        return this;
    }

    public FilterBuilder setFilterData(String filterData) {
        this.filterData = filterData;
        return this;
    }

    public Filter build() {
        return new Filter(folder, filterMode, filterData);
    }
}
