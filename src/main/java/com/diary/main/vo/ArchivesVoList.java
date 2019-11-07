package com.diary.main.vo;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class ArchivesVoList {
    private  String year;
//    private LinkedHashMap<String, List<ArchivesVo>> monthDataSort;
    private  List<ArchivesMonth> monthDataSort;
    public ArchivesVoList(String year, List<ArchivesMonth> monthDataSort) {
        this.year = year;
        this.monthDataSort = monthDataSort;
    }
}
