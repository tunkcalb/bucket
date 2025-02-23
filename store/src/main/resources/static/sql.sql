a. mysql
SELECT
    DATE_FORMAT(order_date, '%Y-%m') AS month, -- 월별 그룹화
    COUNT(DISTINCT user_id) AS user_count,     -- 주문 회원 수 (중복 제외)
    COUNT(*) AS order_count,                   -- 주문 수
    SUM(order_amount) AS total_order_amount,   -- 주문 금액 합계
    SUM(discount_amount) AS total_discount,    -- 할인 금액 합계
    SUM(shipping_charge) AS total_shipping_charge     -- 배송비 합계
FROM
    orders
GROUP BY
    DATE_FORMAT(order_date, '%Y-%m')          -- 월별 그룹화
ORDER BY
    month;                                    -- 월별 정렬

a. oracle
SELECT
    TO_CHAR(order_date, 'YYYY-MM') AS month,  -- 월별 그룹화
    COUNT(DISTINCT user_id) AS user_count,     -- 주문 회원 수 (중복 제외)
    COUNT(*) AS order_count,                   -- 주문 수
    SUM(order_amount) AS total_order_amount,   -- 주문 금액 합계
    SUM(discount_amount) AS total_discount,    -- 할인 금액 합계
    SUM(shipping_charge) AS total_shipping     -- 배송비 합계
FROM
    orders
GROUP BY
    TO_CHAR(order_date, 'YYYY-MM')            -- 월별 그룹화
ORDER BY
    month;                                    -- 월별 정렬

b-1. mysql

SELECT
    DATE_FORMAT(order_date, '%Y-%m') AS month,
    COUNT(DISTINCT user_id) AS active_user_count
FROM
    orders
GROUP BY
    DATE_FORMAT(order_date, '%Y-%m')
ORDER BY
    month;

b-2.mysql
WITH MonthlyOrderSummary AS (
    SELECT
        user_id,
        DATE_FORMAT(order_date, '%Y-%m') AS month,
        SUM(order_amount) AS monthly_order_amount
    FROM
        orders
    GROUP BY
        user_id,
        DATE_FORMAT(order_date, '%Y-%m')
),
RecentThreeMonthsAverage AS (
    SELECT
        user_id,
        month,
        AVG(monthly_order_amount) OVER (
            PARTITION BY user_id
            ORDER BY month
            ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
        ) AS avg_order_amount_last_3_months
    FROM
        MonthlyOrderSummary
)
SELECT
    month,
    COUNT(DISTINCT user_id) AS high_value_user_count
FROM
    RecentThreeMonthsAverage
WHERE
    avg_order_amount_last_3_months > 1000000
GROUP BY
    month
ORDER BY
    month;

b-1. oracle
SELECT
    TO_CHAR(order_date, 'YYYY-MM') AS month,
    COUNT(DISTINCT user_id) AS active_user_count
FROM
    orders
GROUP BY
    TO_CHAR(order_date, 'YYYY-MM')
ORDER BY
    month

b-2. oracle
WITH MonthlyOrderSummary AS (
    SELECT
        user_id,
        TO_CHAR(order_date, 'YYYY-MM') AS month,
        SUM(order_amount) AS monthly_order_amount
    FROM
        orders
    GROUP BY
        user_id,
        TO_CHAR(order_date, 'YYYY-MM')
),
RecentThreeMonthsAverage AS (
    SELECT
        user_id,
        month,
        AVG(monthly_order_amount) OVER (
            PARTITION BY user_id
            ORDER BY month
            ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
        ) AS avg_order_amount_last_3_months
    FROM
        MonthlyOrderSummary
)
SELECT
    month,
    COUNT(DISTINCT user_id) AS high_value_user_count
FROM
    RecentThreeMonthsAverage
WHERE
    avg_order_amount_last_3_months > 1000000
GROUP BY
    month
ORDER BY
    month;

c. mysql

SELECT
    COUNT(DISTINCT o.user_id) AS user_count, -- 회원 수
    COUNT(*) AS order_count, -- 주문 수
    SUM(o.order_amount) AS total_order_amount, -- 주문 금액 합계
    SUM(o.discount_amount) AS total_discount, -- 할인 금액 합계
    SUM(o.shipping_charge) AS total_shipping -- 배송비 합계
FROM
    orders o
JOIN
    order_product op ON o.id = op.order_id
JOIN
    product p ON op.product_id = p.id
JOIN
	brand b on p.brand_id = b.id
WHERE
    b.brand_name = 'A' -- A 브랜드
    AND DATE_FORMAT(o.order_date, '%Y-%m') = '2024-03' -- 2024년 3월
    AND NOT EXISTS (
        SELECT 1
        FROM orders o2
        JOIN order_product op2 ON o2.id = op2.order_id
        JOIN product p2 ON op2.product_id = p2.id
        JOIN brand b2 ON p2.brand_id = b2.id
        WHERE
            b2.brand_name = 'A' -- A 브랜드
            AND o2.user_id = o.user_id
            AND o2.order_date < '2024-03-01' -- 3월 이전 주문 확인
    );

c. oracle
SELECT
    COUNT(DISTINCT o.user_id) AS user_count, -- 회원 수
    COUNT(*) AS order_count, -- 주문 수
    SUM(o.order_amount) AS total_order_amount, -- 주문 금액 합계
    SUM(o.discount_amount) AS total_discount, -- 할인 금액 합계
    SUM(o.shipping_charge) AS total_shipping -- 배송비 합계
FROM
    orders o
JOIN
    order_product op ON o.id = op.order_id
JOIN
    product p ON op.product_id = p.id
JOIN
    brand b ON p.brand_id = b.id
WHERE
    b.brand_name = 'A' -- A 브랜드
    AND TO_CHAR(o.order_date, 'YYYY-MM') = '2024-03' -- 2024년 3월
    AND NOT EXISTS (
        SELECT 1
        FROM orders o2
        JOIN order_product op2 ON o2.id = op2.order_id
        JOIN product p2 ON op2.product_id = p2.id
        JOIN brand b2 ON p2.brand_id = b2.id
        WHERE
            b2.brand_name = 'A' -- A 브랜드
            AND o2.user_id = o.user_id
            AND o2.order_date < TO_DATE('2024-03-01', 'YYYY-MM-DD') -- 3월 이전 주문 확인
    );

d. 지표1 mysql

WITH user_session AS (
    SELECT
        user_id,
        access_timestamp,
        page,
        -- 사용자별로 첫 로그부터 30분 간격으로 세션 ID 생성
        FLOOR(
            TIMESTAMPDIFF(SECOND, MIN(access_timestamp) OVER (PARTITION BY user_id), access_timestamp) / 1800
        ) AS session_id
    FROM user_log
),
session_summary AS (
    SELECT
        user_id,
        session_id,
        MIN(access_timestamp) AS session_start_time,
        MAX(access_timestamp) AS session_end_time,
        COUNT(page) AS total_pages,
        COUNT(DISTINCT page) AS unique_pages
    FROM user_session
    GROUP BY user_id, session_id
)
SELECT COUNT(*) AS total_rows
FROM session_summary;

D. 지표1 oracle
WITH user_session AS (
    SELECT
        user_id,
        access_timestamp,
        page,
        -- 사용자별로 첫 로그부터 30분 간격으로 세션 ID 생성
        FLOOR(
            (CAST(access_timestamp AS DATE) -
             MIN(CAST(access_timestamp AS DATE)) OVER (PARTITION BY user_id)) * 24 * 60 * 60 / 1800
        ) AS session_id
    FROM user_log
),
session_summary AS (
    SELECT
        user_id,
        session_id,
        MIN(access_timestamp) AS session_start_time,
        MAX(access_timestamp) AS session_end_time,
        COUNT(page) AS total_pages,
        COUNT(DISTINCT page) AS unique_pages
    FROM user_session
    GROUP BY user_id, session_id
)
SELECT COUNT(*) AS total_rows
FROM session_summary;


d. 지표2 mysql
WITH user_session AS (
    SELECT
        user_id,
        access_timestamp,
        page,
        FLOOR(
            TIMESTAMPDIFF(SECOND, MIN(access_timestamp) OVER (PARTITION BY user_id), access_timestamp) / 1800
        ) AS session_id
    FROM user_log
),
session_summary AS (
    SELECT
        user_id,
        session_id,
        MIN(access_timestamp) AS session_start_time,
        MAX(access_timestamp) AS session_end_time,
        COUNT(page) AS total_pages,
        COUNT(DISTINCT page) AS unique_pages,
        TIMESTAMPDIFF(SECOND, MIN(access_timestamp), MAX(access_timestamp)) AS session_duration_seconds
    FROM user_session
    GROUP BY user_id, session_id
)
SELECT
    ROUND(AVG(session_duration_seconds / 60), 2) AS avg_session_duration_minutes
FROM session_summary
WHERE total_pages > 1;

d. 지표2 oracle
WITH user_session AS (
    SELECT
        user_id,
        access_timestamp,
        page,
        FLOOR(
            (CAST(access_timestamp AS DATE) -
             MIN(CAST(access_timestamp AS DATE)) OVER (PARTITION BY user_id)) * 24 * 60 * 60 / 1800
        ) AS session_id
    FROM user_log
),
session_summary AS (
    SELECT
        user_id,
        session_id,
        MIN(access_timestamp) AS session_start_time,
        MAX(access_timestamp) AS session_end_time,
        COUNT(page) AS total_pages,
        COUNT(DISTINCT page) AS unique_pages,
        EXTRACT(SECOND FROM (MAX(access_timestamp) - MIN(access_timestamp))) +
        EXTRACT(MINUTE FROM (MAX(access_timestamp) - MIN(access_timestamp))) * 60 +
        EXTRACT(HOUR FROM (MAX(access_timestamp) - MIN(access_timestamp))) * 3600 +
        EXTRACT(DAY FROM (MAX(access_timestamp) - MIN(access_timestamp))) * 86400 AS session_duration_seconds
    FROM user_session
    GROUP BY user_id, session_id
)
SELECT
    ROUND(AVG(session_duration_seconds / 60), 2) AS avg_session_duration_minutes
FROM session_summary
WHERE total_pages > 1;

d. 지표3 mysql
WITH user_session AS (
    SELECT
        user_id,
        access_timestamp,
        page,
        -- 사용자별로 첫 로그부터 30분 간격으로 세션 ID 생성
        FLOOR(
            TIMESTAMPDIFF(SECOND, MIN(access_timestamp) OVER (PARTITION BY user_id), access_timestamp) / 1800
        ) AS session_id
    FROM user_log

),
session_summary AS (
    SELECT
        user_id,
        session_id,
        MIN(access_timestamp) AS session_start_time,
        MAX(access_timestamp) AS session_end_time,
        COUNT(page) AS total_pages,
        COUNT(DISTINCT page) AS unique_pages
    FROM user_session
    GROUP BY user_id, session_id
)
SELECT ROUND(SUM(total_pages) / count(*), 2) AS session_avg_page, ROUND(SUM(unique_pages) / count(*), 2) AS session_avg_page_unique
FROM session_summary;

d. 지표3 oracle

WITH user_session AS (
    SELECT
        user_id,
        access_timestamp,
        page,
        -- 사용자별로 첫 로그부터 30분 간격으로 세션 ID 생성
        FLOOR(
            (CAST(access_timestamp AS DATE) -
             MIN(CAST(access_timestamp AS DATE)) OVER (PARTITION BY user_id)) * 24 * 60 * 60 / 1800
        ) AS session_id
    FROM user_log
),
session_summary AS (
    SELECT
        user_id,
        session_id,
        MIN(access_timestamp) AS session_start_time,
        MAX(access_timestamp) AS session_end_time,
        COUNT(page) AS total_pages,
        COUNT(DISTINCT page) AS unique_pages
    FROM user_session
    GROUP BY user_id, session_id
)
SELECT
    ROUND(SUM(total_pages) / COUNT(*), 2) AS session_avg_page,
    ROUND(SUM(unique_pages) / COUNT(*), 2) AS session_avg_page_unique
FROM session_summary;