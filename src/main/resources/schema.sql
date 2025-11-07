/**
 保険種別テーブル

 保険毎の保険料、保険内容を保存する。
*/
CREATE TABLE IF NOT EXISTS INSURANCE_TYPE (
    INSURANCE_TYPE INTEGER NOT NULL,                      -- 保険種別
    INSURANCE_NAME VARCHAR(120) NOT NULL,                 -- 保険名
    MONTHLY_FEE INTEGER NOT NULL,                         -- 月額保険料
    PRIMARY KEY (INSURANCE_TYPE)
);

/**
 年齢調整率テーブル

 保険料は年齢によって変動するため、年齢毎の調整率を保存する。
*/
CREATE TABLE IF NOT EXISTS AGE_ADJUSTMENT_RATE (
    AGE INTEGER NOT NULL,                                    -- 年齢
    ADJUSTMENT_RATE DECIMAL(5,2) NOT NULL,                   -- 調整率
    PRIMARY KEY (AGE)
);


/*
 保険申し込みテーブル

 保険申込みの申請内容を保存する。
*/
CREATE TABLE IF NOT EXISTS INSURANCE_ORDER (
    RECEIPT_NO NUMBER(9) NOT NULL DEFAULT 0 AUTO_INCREMENT,   -- 受付番号
    INSURANCE_TYPE NUMBER NOT NULL,                           -- 保険種別
    KANJI_NAME VARCHAR(120) NOT NULL,                         -- 漢字氏名
    KANA_NAME VARCHAR(180) NOT NULL,                          -- カナ氏名
    DATE_OF_BIRTH CHAR(10) NOT NULL,                          -- 生年月日
    ADDRESS VARCHAR(510) NOT NULL,                            -- 住所
    TEL VARCHAR(13) NOT NULL,                                 -- 電話番号
    EMAIL_ADDRESS VARCHAR(255) NOT NULL,                      -- メールアドレス
    MARRIED INTEGER NOT NULL,                                 -- 配偶者有無
    JOB INTEGER NOT NULL,                                     -- 職業
    INCOME NUMBER(9) NOT NULL,                                -- 所得金額
    DISCOVERY_SOURCE INTEGER NOT NULL,                        -- 保険認知経路
    TREATED INTEGER NOT NULL,                                 -- 病歴有無
    MEDICAL_HISTORY VARCHAR(240),                             -- 病歴
    PRIMARY KEY (RECEIPT_NO),
    FOREIGN KEY (INSURANCE_TYPE) REFERENCES INSURANCE_TYPE(INSURANCE_TYPE)
);
