# 📱 Social Media REST API

A full-featured REST API built using Java Spring Boot, implementing the Richardson Maturity Model Level 3 (HATEOAS) and covering modern API concepts such as:
 - Pagination, sorting, filtering, searching
- Content negotiation (JSON / XML with coefficients)
- API versioning (URL-based)
- Caching & Compression
- i18n (English / French)
- Fully documented using Swagger UI
- HATEOAS links on all resources

This project was created as part of the REST APIs & DevOps coursework.

## Features
### Users
- Create, read, update, delete (CRUD)
- Pagination (page, size)
- Sorting (by registration date, default DESC)
- Filtering (title)
- Searching (email)
- Full HATEOAS links: self, users, posts, comments
### Posts
- CRUD operations
- Pagination + search
- Sorting (by publish date, default DESC)
- Get posts by user
- Get posts by tag
- HATEOAS links: self, user (owner), comments, tags
### Comments
- Create + delete
- Sorting (by publish date, default DESC)
- List all comments
- Get comments for a user
- Get comments for a post
- Filter by date range
- HATEOAS links: self, post, user (owner)

### Tags
- List all tags (flat list)
- List posts for a tag (paginated, HATEOAS)
