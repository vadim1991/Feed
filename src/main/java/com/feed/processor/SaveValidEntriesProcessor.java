package com.feed.processor;

import com.feed.entity.Entry;
import com.feed.model.EntryModel;
import com.feed.model.TransferEntry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
public class SaveValidEntriesProcessor implements Processor {
    @Override
    public void process(TransferEntry transferEntry) {
        List<EntryModel> incomingEntries = transferEntry.getIncomingEntries();
        if (!incomingEntries.isEmpty()) {
            saveEntries(transferEntry.getIncomingEntries());
            transferEntry.getProcessedFileList().addAll(transferEntry.getValidFileList());
        }
    }

    private void saveEntries(List<EntryModel> entries) {
        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        mapEntry(entries).stream().forEach(entry -> session.save(entry));
        session.getTransaction().commit();
        session.close();
    }

    private List<Entry> mapEntry(List<EntryModel> entryModels) {
        return entryModels.stream()
                .map(EntryModel::toEntity)
                .collect(Collectors.toList());
    }

}
