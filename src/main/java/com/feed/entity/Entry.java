package com.feed.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Vadym_Vlasenko on 09.08.2016.
 */
@Entity
@Data
@NoArgsConstructor
public class Entry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 1024)
    private String content;
    @Column(name = "creation_date")
    private Date creationDate;

}
