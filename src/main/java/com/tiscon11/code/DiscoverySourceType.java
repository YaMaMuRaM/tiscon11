package com.tiscon11.code;

/**
 * 保険認知経路（どこでこの保険を知りましたか）を表すEnum。
 *
 * @author TIS Taro
 */
public enum DiscoverySourceType implements CodeEnum {

    /** インターネット（検索エンジン） */
    INTERNET_SEARCH(1, "インターネット（検索エンジン）"),
    /** 保険会社の公式ウェブサイト */
    OFFICIAL_WEBSITE(2, "保険会社の公式ウェブサイト"),
    /** 比較サイト／口コミサイト */
    COMPARISON_SITE(3, "比較サイト／口コミサイト"),
    /** テレビCM・ラジオCM */
    TV_RADIO_CM(4, "テレビCM・ラジオCM"),
    /** 新聞・雑誌広告 */
    NEWSPAPER_MAGAZINE(5, "新聞・雑誌広告"),
    /** SNS（Twitter、Instagram、Facebookなど） */
    SNS(6, "SNS（Twitter、Instagram、Facebookなど）"),
    /** YouTube広告／動画サイト */
    YOUTUBE_VIDEO(7, "YouTube広告／動画サイト"),
    /** 知人・家族の紹介 */
    INTRODUCED_BY_PERSON(8, "知人・家族の紹介"),
    /** 保険代理店・営業担当者 */
    AGENT(9, "保険代理店・営業担当者"),
    /** 会社・職場からの案内 */
    COMPANY_NOTICE(10, "会社・職場からの案内"),
    /** 郵送チラシ・ダイレクトメール */
    POST_MAIL(11, "郵送チラシ・ダイレクトメール"),
    /** 店頭・イベント（キャンペーンブース等） */
    EVENT(12, "店頭・イベント（キャンペーンブース等）"),
    /** その他 */
    OTHER(13, "その他");

    /** 認知経路のラベル */
    private final String label;
    /** 認知経路のコード */
    private final int code;

    /**
     * コンストラクタ。
     *
     * @param code  コード値
     * @param label ラベル
     */
    DiscoverySourceType(int code, String label) {
        this.label = label;
        this.code = code;
    }

    /**
     * ラベルを返却する。
     *
     * @return ラベル
     */
    @Override
    public String getLabel() {
        return label;
    }

    /**
     * コード値を返却する。
     *
     * @return コード値
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * コード値からenumを返却する。
     * 存在しないコード値の場合はnullを返却する。
     *
     * @param code コード値（String型）
     * @return enum
     */
    public static DiscoverySourceType getEnumFromCode(String code) {
        for (DiscoverySourceType sourceType : DiscoverySourceType.values()) {
            if (sourceType.hasCode(code)) {
                return sourceType;
            }
        }
        return null;
    }
}