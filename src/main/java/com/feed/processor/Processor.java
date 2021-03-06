package com.feed.processor;

import com.feed.model.TransferEntry;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
public interface Processor {

    void process(TransferEntry transferEntry);

    default String getName() {
        return getClass().getName();
    }

}
