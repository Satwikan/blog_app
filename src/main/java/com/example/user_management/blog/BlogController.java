package com.example.user_management.blog;

import com.example.user_management.appuser.AppUser;
import com.example.user_management.appuser.AppUserRepository;
import com.sun.net.httpserver.Authenticator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/blog")
public class BlogController {
    private static final int PAGE_SIZE = 10;
    private final BlogRepository blogRepository;
    private final BlogService blogService;

    @GetMapping()
    public List<Blog> getBlog(@RequestParam("page") int page) {
        // return 10 blogs per page
        // if total blogs are less than PAGE_SIZE, return all blogs
        if (blogRepository.findAll().size() < PAGE_SIZE) {
            System.out.println("blogRepository.findAll().size() < PAGE_SIZE");
            return blogRepository.findAll();
        }
        return blogRepository.findAll().subList(page * PAGE_SIZE, page * PAGE_SIZE + PAGE_SIZE);
    }

    @PostMapping
    public Blog postBlog(@RequestBody Blog blog) {
        return blogService.postBlog(blog);
    }

    @GetMapping(path = "{blogId}")
    public Blog getBlog(@PathVariable("blogId") Long blogId) {
        return blogService.getBlog(blogId);
    }
    @DeleteMapping(path = "{blogId}")
    public Blog deleteBlog(@PathVariable("blogId") Long blogId) {
        return blogService.deleteBlog(blogId);
    }

    @PutMapping(path = "{blogId}")
    public Blog updateBlog(@PathVariable("blogId") Long blogId, @RequestBody Blog updatedBlog) {
        return blogService.updateBlog(blogId, updatedBlog);
    }

}
