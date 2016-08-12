package com.feed.repository;

import com.feed.entity.Entry;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Vadym_Vlasenko on 12.08.2016.
 */
public interface EntryRepository extends CrudRepository<Entry, Long> {
}
