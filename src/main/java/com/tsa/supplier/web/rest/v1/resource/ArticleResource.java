package com.tsa.supplier.web.rest.v1.resource;

import com.tsa.supplier.service.IArticleService;
import com.tsa.supplier.service.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/good/{goodId}/articles")
public class ArticleResource {

    @Autowired
    private IArticleService articleService;

    @GetMapping
    public ResponseEntity<List<Article>> getArticles(@PathVariable("goodId") long goodId) {
        return ResponseEntity.ok(articleService.getArticlesByGoodId(goodId));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Article article) {
        article = articleService.insert(article);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(article.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Article article) {
        articleService.update(article);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        articleService.delete(id);
    }

}