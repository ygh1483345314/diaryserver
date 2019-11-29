package com.diary.main.es.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "article", type = "article")
public class ArticleEs implements Serializable {

    private static final long serialVersionUID = 8712644551320360120L;

    @Id
    private  String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private  String title;
    private List<String> type;
    private  String status;
    private  List<String> tag;
    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private  String html;
    @Field(type=FieldType.Text,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss",fielddata=true)
    private Date dateb;
    private String original;
    private  String top;
    private  String comments;
//    @Field(ignoreFields = {})
//    @Field(type=FieldType.Text)
    private  Integer reading;


    public ArticleEs(String id,String title, List<String> type, String status, List<String> tag, String html,Date dateb) {
        this.id=id;
        this.title = title;
        this.type = type;
        this.status = status;
        this.tag = tag;
        this.html = html;
        this.dateb = dateb;
    }

    public ArticleEs() {
    }
}
