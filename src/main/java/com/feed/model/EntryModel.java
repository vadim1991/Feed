package com.feed.model;

import com.feed.entity.Entry;
import com.feed.util.DateAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@XmlRootElement(name = "Entry")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryModel {

    @XmlElement
    private String content;
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date creationDate;

    public Entry toEntity() {
        Entry entry = new Entry();
        entry.setContent(this.getContent());
        entry.setCreationDate(this.getCreationDate());
        return entry;
    }

}
