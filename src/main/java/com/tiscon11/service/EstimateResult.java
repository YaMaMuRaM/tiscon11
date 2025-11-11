package com.tiscon11.service;

/**
 * 見積もり結果を表すクラス。
 *
 * @param annualFee 保険料（年額）
 * @param adjustmentRateByAge 年齢による調整率
 * @param age 年齢
 * @param refundFee 保険料の割戻し
 */
public record EstimateResult(

    int annualFee,   // 保険料（年額）
    double adjustmentRateByAge,  // 年齢による調整率
    int age,  // 年齢
    double refundFeeInt // 保険料の割戻し

) {
}

