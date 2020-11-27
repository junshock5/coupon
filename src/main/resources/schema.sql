-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- user Table Create SQL
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    `id`         BIGINT         NOT NULL    AUTO_INCREMENT COMMENT '번호',
    `password`   VARCHAR(45)    NULL        COMMENT '패스워드',
    `createdAt`  TIMESTAMP      NULL        COMMENT '생성날짜',
    `userId`     VARCHAR(45)    NULL        COMMENT '아이디',
    PRIMARY KEY (id)
);

-- user Table Create SQL
DROP TABLE IF EXISTS coupon;
CREATE TABLE coupon
(
    `id`             BIGINT         NOT NULL    AUTO_INCREMENT COMMENT '번호',
    `userId`         BIGINT         NULL        COMMENT '유저번호',
    `code`           VARCHAR(45)    NULL        COMMENT '코드',
    `discountType`   VARCHAR(45)    NULL        COMMENT '할인종류',
    `discountValue`  VARCHAR(45)    NULL        COMMENT '할인값',
    `status`         VARCHAR(45)    NULL        COMMENT '상태',
    `createdAt`      TIMESTAMP      NULL        COMMENT '생성날짜',
    `updatedAt`      TIMESTAMP      NULL        COMMENT '변경날짜',
    `endAt`          TIMESTAMP      NULL        COMMENT '만료날짜',
    `assignedAt`     TIMESTAMP      NULL        COMMENT '지급날짜',
    PRIMARY KEY (id)
);
