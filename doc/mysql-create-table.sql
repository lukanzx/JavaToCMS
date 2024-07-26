use java_study_01_cms;

create table user
(
    user_id       int auto_increment
        primary key,
    username      varchar(255)                        not null,
    password      varchar(255)                        not null,
    user_type     enum ('管理员', '普通用户')         not null,
    user_status   enum ('正常', '禁用')               not null,
    gender        varchar(10)                         null,
    email         varchar(255)                        null,
    phone_number  varchar(20)                         null,
    avatar_id     int                                 null,
    creation_time timestamp default CURRENT_TIMESTAMP not null
);

create table images
(
    image_id     int auto_increment
        primary key,
    image_url    varchar(255)                        not null,
    image_name   varchar(255)                        not null,
    image_size   int                                 not null,
    dimensions   varchar(50)                         null,
    image_status tinyint(1)                          not null,
    image_type   enum ('png', 'jpg')                 not null,
    upload_time  timestamp default CURRENT_TIMESTAMP not null,
    uploader_id  int                                 null,
    constraint images_ibfk_1
        foreign key (uploader_id) references user (user_id)
            on delete set null
);

create index uploader_id
    on images (uploader_id);

create table category
(
    category_id          int auto_increment
        primary key,
    category_name        varchar(255)                        not null,
    category_description text                                not null,
    creation_time        timestamp default CURRENT_TIMESTAMP not null,
    cover_image_id       int       default 10000             null,
    creator_id           int                                 not null,
    constraint category_ibfk_1
        foreign key (cover_image_id) references images (image_id),
    constraint category_ibfk_2
        foreign key (creator_id) references user (user_id)
);

create index cover_image_id
    on category (cover_image_id);

create index creator_id
    on category (creator_id);

create table topic
(
    topic_id          int auto_increment
        primary key,
    topic_name        varchar(255)                        not null,
    topic_description text                                not null,
    creation_time     timestamp default CURRENT_TIMESTAMP not null,
    update_time       timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    category_id       int                                 not null,
    creator_id        int                                 not null,
    cover_image_id    int       default 10000             null,
    constraint topic_ibfk_1
        foreign key (category_id) references category (category_id),
    constraint topic_ibfk_2
        foreign key (creator_id) references user (user_id),
    constraint topic_ibfk_3
        foreign key (cover_image_id) references images (image_id)
);

create index category_id
    on topic (category_id);

create index cover_image_id
    on topic (cover_image_id);

create index creator_id
    on topic (creator_id);

create table article
(
    article_id      int auto_increment
        primary key,
    article_title   varchar(255)                        not null,
    article_content text                                not null,
    creation_time   timestamp default CURRENT_TIMESTAMP not null,
    update_time     timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    topic_id        int                                 not null,
    creator_id      int                                 not null,
    cover_image_id  int       default 10000             null,
    constraint article_ibfk_1
        foreign key (topic_id) references topic (topic_id),
    constraint article_ibfk_2
        foreign key (creator_id) references user (user_id),
    constraint article_ibfk_3
        foreign key (cover_image_id) references images (image_id)
);

create index cover_image_id
    on article (cover_image_id);

create index creator_id
    on article (creator_id);

create index topic_id
    on article (topic_id);

create table comment
(
    comment_id      int auto_increment
        primary key,
    comment_content text                                not null,
    creation_time   timestamp default CURRENT_TIMESTAMP not null,
    article_id      int                                 not null,
    creator_id      int                                 not null,
    article_title   varchar(255)                        not null,
    constraint comment_ibfk_1
        foreign key (article_id) references article (article_id),
    constraint comment_ibfk_2
        foreign key (creator_id) references user (user_id)
);

create index article_id
    on comment (article_id);

create index creator_id
    on comment (creator_id);

create table tags
(
    tag_id             int auto_increment
        primary key,
    tag_name           varchar(255)                        not null,
    tag_attribute      varchar(255)                        not null,
    tag_usage_count    int       default 0                 null,
    tag_description    text                                not null,
    created_at         timestamp default CURRENT_TIMESTAMP not null,
    updated_at         timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    created_by_user_id int                                 not null,
    constraint tags_ibfk_1
        foreign key (created_by_user_id) references user (user_id)
);

create index created_by_user_id
    on tags (created_by_user_id);

create table posters
(
    poster_id          int auto_increment
        primary key,
    poster_title       varchar(255)                                  not null,
    cover_image_id     int                                           not null,
    position           enum ('上边', '下边', '中间', '右边', '左边') not null,
    status             enum ('正常', '异常', '禁用')                 not null,
    created_by_user_id int                                           not null,
    created_at         timestamp default CURRENT_TIMESTAMP           not null,
    updated_at         timestamp default CURRENT_TIMESTAMP           not null on update CURRENT_TIMESTAMP,
    constraint posters_ibfk_1
        foreign key (cover_image_id) references images (image_id),
    constraint posters_ibfk_2
        foreign key (created_by_user_id) references user (user_id)
);

create index cover_image_id
    on posters (cover_image_id);

create index created_by_user_id
    on posters (created_by_user_id);

-- 插入基础用户信息
INSERT INTO user (user_id, username, password, user_type, user_status, gender, email, phone_number, avatar_id)
VALUES (10000, '系统', '系统', '管理员', '正常', NULL, 'super@example.com', '10000', 10000);

INSERT INTO user (user_id, username, password, user_type, user_status, gender, email, phone_number, avatar_id)
VALUES (10001, 'admin', 'admin', '管理员', '正常', '男', 'admin@example.com', '1122334455', 10000);

-- 插入基础图片信息
INSERT INTO images (image_id, image_url, image_name, image_size, dimensions, image_status, image_type, uploader_id)
VALUES (10000, '../../../images/my/default.png', '默认头像', 26174, '1920x1080', 1, 'png', 10000);

-- 插入基础分类信息
INSERT INTO category (category_id, category_name, category_description, cover_image_id, creator_id)
VALUES (1, '默认分类', '默认分类', 10000, 10000);

-- 插入基础的专题信息
INSERT INTO topic (topic_id, topic_name, topic_description, category_id, creator_id, cover_image_id)
VALUES (1, '默认专题', '默认专题', 1, 10000, 10000);

-- 插入模拟数据
-- 插入分类数据
INSERT INTO `category` (category_id, category_name, category_description, creator_id)
VALUES (2, '编程分类', '关于编程的所有内容', 10000),
       (3, '数学分类', '关于数学的所有内容', 10000);

-- 插入专题数据
INSERT INTO `topic` (topic_id, topic_name, topic_description, category_id, creator_id)
VALUES (2, 'Java相关专题', '所有关于Java的内容', 2, 10000),
       (3, 'C++相关专题', '所有关于C++的内容', 2, 10000),
       (4,'数学分析专题', '所有关于数学分析的内容', 3, 10000),
       (5, '线性代数专题', '所有关于线性代数的内容', 3, 10000);

-- 插入文章数据
INSERT INTO `article` (article_title, article_content, topic_id, creator_id)
VALUES ('Java基础文章', 'Java基础内容', 2, 10000),
       ('Java进阶文章', 'Java进阶内容', 2, 10000),
       ('C++基础文章', 'C++基础内容', 3, 10000),
       ('C++进阶文章', 'C++进阶内容', 3, 10000),
       ('数学分析基础文章', '数学分析基础内容', 4, 10000),
       ('数学分析进阶文章', '数学分析进阶内容', 4, 10000),
       ('线性代数基础文章', '线性代数基础内容', 5, 10000),
       ('线性代数进阶文章', '线性代数进阶内容', 5, 10000);

-- 插入评论数据
-- 插入评论数据
INSERT INTO `comment` (comment_content, article_id, creator_id, article_title)
SELECT CONCAT('评论内容', LPAD(t2.i, 2, '0')),
       t1.article_id,
       t1.creator_id,
       t1.article_title
FROM (SELECT article_id, creator_id, article_title FROM article) t1,
     (SELECT @rownum := @rownum + 1 AS i
      FROM (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t,
           (SELECT @rownum := 0) r) t2;

-- 插入三条模拟数据
INSERT INTO tags (tag_name, tag_attribute, tag_usage_count, tag_description, created_by_user_id)
VALUES ('标签1', '属性1', 0, '描述1', 10000),
       ('标签2', '属性2', 0, '描述2', 10000),
       ('标签3', '属性3', 0, '描述3', 10000);

-- 插入三条模拟数据
INSERT INTO posters (poster_title, cover_image_id, position, status, created_by_user_id)
VALUES ('海报1', 10000, '上边', '正常', 10000),
       ('海报2', 10000, '中间', '异常', 10000),
       ('海报3', 10000, '下边', '禁用', 10000);

INSERT INTO images (image_id, image_url, image_name, image_size, dimensions, image_status, image_type, uploader_id)
VALUES
    (10001, '../../../images/my/default.png', '图片', 26174, '1920x1080', 1, 'png', 10000),
    (10002, '../../../images/my/default.png', '图片', 26174, '1920x1080', 1, 'png', 10000),
    (10003, '../../../images/my/default.png', '图片', 26174, '1920x1080', 1, 'png', 10000),
    (10004, '../../../images/my/default.png', '图片', 26174, '1920x1080', 1, 'png', 10000),
    (10005, '../../../images/my/default.png', '图片', 26174, '1920x1080', 1, 'png', 10000),
    (10006, '../../../images/my/default.png', '图片', 26174, '1920x1080', 1, 'png', 10000),
    (10007, '../../../images/my/default.png', '图片', 26174, '1920x1080', 1, 'png', 10000),
    (10008, '../../../images/my/default.png', '图片', 26174, '1920x1080', 1, 'png', 10000),
    (10009, '../../../images/my/default.png', '图片', 26174, '1920x1080', 1, 'png', 10000),
    (10010, '../../../images/my/default.png', '图片', 26174, '1920x1080', 1, 'png', 10000);

