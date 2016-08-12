package com.feed.service;

import com.feed.entity.Entry;
import com.feed.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Vadym_Vlasenko on 12.08.2016.
 */
@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryRepository repository;


    @Override
    public Entry save(Entry entry) {
        return repository.save(entry);
    }

    @Override
    public void batchSave(List<Entry> entries) {
        if (CollectionUtils.isEmpty(entries)) {
            entries.stream().forEach(this::save);
        }
    }
}
