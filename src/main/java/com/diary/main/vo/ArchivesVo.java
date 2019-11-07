package com.diary.main.vo;/*
Created by hao on 2019/9/29
*/

import com.diary.main.es.model.ArticleEs;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Data
public class ArchivesVo  {
    private  String id;
    private  String title;
    private List<String> type;
    private  String status;
    private List<String> tag;
    private  String html;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateb;
    private  String  year;
    private  String month;

    public ArchivesVo(ArticleEs articleEs) {
        this.id = articleEs.getId();
        this.title = articleEs.getTitle();
        this.type = articleEs.getType();
        this.status = articleEs.getStatus();
        this.tag = articleEs.getTag();
        this.html = articleEs.getHtml();
        this.dateb = articleEs.getDateb();
        LocalDate localDate= articleEs.getDateb().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.year=String.valueOf(localDate.getYear());
        this.month=String.format("%02d", localDate.getMonthValue());
    }

    public ArchivesVo() {
    }
}
