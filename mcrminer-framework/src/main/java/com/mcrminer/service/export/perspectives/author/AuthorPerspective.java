package com.mcrminer.service.export.perspectives.author;

import lombok.Data;

@Data
public class AuthorPerspective {
    String email, fullname, username;
    Long reviewRequests, diffs, files;
}