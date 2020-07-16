package com.mike.blog.exception;

public class SpringBlogException extends RuntimeException {
    public SpringBlogException(Exception exception, String message) {
        super(message, exception);
    }

    public SpringBlogException(String message) {
        super(message);
    }
}
