package org.example.vegetableshopping.api;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.dto.response.BlogResponse;
import org.example.vegetableshopping.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blogs")
public class BlogApi {

    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<Page<BlogResponse>> getBlogs(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "15") int size,
                                                       @RequestParam(required = false) String sort) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogService.getBlogs(page, size, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlog(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogService.getBlog(id));
    }


}
