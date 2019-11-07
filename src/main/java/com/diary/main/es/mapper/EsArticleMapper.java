package com.diary.main.es.mapper;


import com.diary.main.es.model.ArticleEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EsArticleMapper extends ElasticsearchRepository<ArticleEs, String> {

}

