package com.diary.main.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArchivesMonth {
    private  String month;
    private List<ArchivesVo> archivesVoList;

    public ArchivesMonth(String month, List<ArchivesVo> archivesVoList) {
        this.month = month;
        this.archivesVoList = archivesVoList;
    }
}
