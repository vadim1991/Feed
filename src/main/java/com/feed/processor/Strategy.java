package com.feed.processor;

import com.feed.model.TransferEntry;

import java.util.List;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
public interface Strategy {

    default void runProcess(TransferEntry transferEntry) {
        getProcessors().forEach(processor -> processor.process(transferEntry));
    }

    List<Processor> getProcessors();

}
