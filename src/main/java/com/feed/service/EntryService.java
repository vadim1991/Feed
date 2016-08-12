package com.feed.service;

import com.feed.entity.Entry;

import java.util.List;

/**
 * Created by Vadym_Vlasenko on 12.08.2016.
 */
public interface EntryService {

    Entry save(Entry entry);

    void batchSave(List<Entry> entries);

}
