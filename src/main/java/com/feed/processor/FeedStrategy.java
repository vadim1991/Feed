package com.feed.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@Service
public class FeedStrategy implements Strategy {

    @Autowired
    @Qualifier("validationProcessor")
    private Processor validationProcessor;
    @Autowired
    @Qualifier("parseXMLProcessor")
    private Processor parseXMLProcessor;
    @Autowired
    @Qualifier("saveEntryProcessor")
    private Processor saveEntryProcessor;
    @Autowired
    @Qualifier("moveFilesProcessor")
    private Processor moveFilesProcessor;

    @Override
    public List<Processor> getProcessors() {
        return asList(
                validationProcessor,
                parseXMLProcessor,
                saveEntryProcessor,
                moveFilesProcessor
        );
    }
}
