package com.feed.processor;

import com.feed.model.TransferEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@Slf4j
public class DefaultStrategy implements Strategy {

    private List<Processor> processors;

    public DefaultStrategy(final List<Processor> processors) {
        this.processors = processors;
    }

    @Override
    public void runProcess(TransferEntry transferEntry) {
        processors.forEach(processor -> {
            processor.process(transferEntry);
        });
    }
}