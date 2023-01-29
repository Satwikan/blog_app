package com.example.user_management.blog;

import com.example.user_management.appuser.AppUser;
import com.example.user_management.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final AppUserRepository appUserRepository;

    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalStateException("Blog not found"));
    }

    public Blog postBlog(Blog blog) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        AppUser appUser = appUserRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalStateException("User not found"));
        blog.setAuthor(appUser);
        blog.setCreatedAt(System.currentTimeMillis());
        return blogRepository.save(blog);
    }

    public Blog deleteBlog(Long id) {
        boolean exists = blogRepository.existsById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        AppUser appUser = appUserRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalStateException("User not found"));
        if (!exists) {
            throw new IllegalStateException("Blog with id " + id + " does not exist");
        }
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalStateException("Blog not found"));
        if (blog.getAuthor() != appUser) {
            throw new IllegalStateException("Blog with id " + id + " does not belong to user " + appUser.getUsername());
        }
        blogRepository.deleteById(id);
        return blog;
    }

    public Blog updateBlog(Long id, Blog blog) {
        boolean exists = blogRepository.existsById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        AppUser appUser = appUserRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalStateException("User not found"));
        if (!exists) {
            throw new IllegalStateException("Blog with id " + id + " does not exist");
        }
        Blog blogToUpdate = blogRepository.findById(id).orElseThrow(() -> new IllegalStateException("Blog not found"));
        if (blogToUpdate.getAuthor() != appUser) {
            throw new IllegalStateException("Blog with id " + id + " does not belong to user " + appUser.getUsername());
        }
        blogToUpdate.setTitle(blog.getTitle());
        blogToUpdate.setBody(blog.getBody());
        blogToUpdate.setUpdatedAt(System.currentTimeMillis());
        blogRepository.save(blogToUpdate);
        return blogToUpdate;
    }

}
