package com.tiscon11.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiscon11.dao.EstimateDao;
import com.tiscon11.domain.InsuranceOrder;
import com.tiscon11.domain.InsuranceType;

/**
 * 保険見積もり機能において業務処理を担当するクラス。
 *
 * @author TIS Taro
 */
@Service
public class EstimateService {

    /**
     * 見積もりDAO
     */
    @Autowired
    private EstimateDao estimateDAO;

    /**
     * 保険種別テーブルに登録されているすべての保険種別を取得する。
     *
     * @return すべての保険種別
     */
    public List<InsuranceType> getInsurances() {
        return estimateDAO.getAllInsurances();
    }

    /**
     * 保険種別名を取得する。
     *
     * @param insuranceType 保険種別タイプ
     * @return 保険種別名
     */
    public String findInsuranceName(Integer insuranceType) {
        return estimateDAO.findInsuranceName(insuranceType);
    }

    /**
     * 生年月日と保険種別から保険料（年額）の見積もりを算出する。
     *
     * @param insuranceType 保険種別タイプ
     * @param dateOfBirth 生年月日
     * @return 見積もり結果
     */
    public EstimateResult calculateInsuranceFee(Integer insuranceType, LocalDate dateOfBirth) {
        // ユーザーが選択した保険種別の月額保険料を取得する。
        int monthlyFee = estimateDAO.findMonthlyFee(insuranceType);

        // ユーザーが選択した生年月日と現在日付から年齢を取得する。
        int age = calculateAge(dateOfBirth);

        // 年齢による調整率を取得する。
        double adjustmentRateByAge = estimateDAO.findAdjustmentRateByAge(age);

        // 年間保険料（割戻し前）
        double annualFeeWithoutRefund = (monthlyFee * 12 * adjustmentRateByAge);

        // 割戻し金額を計算する
        double refundRate = findRefundRate(insuranceType);
        double refundFee = annualFeeWithoutRefund * refundRate;
        int refundFeeInt = (int) refundFee;  // 小数点以下切り捨て

        // 保険料（年額）を計算する。
        int annualFee = (int) (monthlyFee * 12 * adjustmentRateByAge);

        // 見積もり結果を返す。
        EstimateResult estimateResult = new EstimateResult(annualFee, adjustmentRateByAge, age, refundFeeInt);
        return estimateResult;

    }

    /**
     * 生年月日と現在日付から年齢を計算し、年齢が20歳以上100歳以下であるかを判定する。
     *
     * @param dateOfBirth 生年月日
     * @return 年齢が20歳以上100歳以下である場合、真
     */
    public boolean isAgeValid(LocalDate dateOfBirth) {
        int age = calculateAge(dateOfBirth);
        return age >= 20 && age <= 100;
    }

    /**
     * 生年月日と現在日付から年齢を計算する。
     *
     * @param dateOfBirth 生年月日
     * @return 現在の年齢
     */
    private int calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        return period.getYears();
    }

    /**
     * データベースに見積もり依頼を登録する。
     *
     * @param insuranceOrder 見積もり依頼情報
     */
    @Transactional
    public void registerOrder(InsuranceOrder insuranceOrder) {
        estimateDAO.insertInsuranceOrder(insuranceOrder);
    }

    /** 医療保険の割戻し率 */
    private static final double IRYOU_REFUND_RATE = 0.2;
    /** 死亡保険の割戻し率 */
    private  static final double SHIBOU_REFUND_RATE = 0.15;
    /** がん保険の割戻し率 */
    private static final double GAN_REFUND_RATE = 0.35;

    /**
     * 割戻し率を取得する。
     * 割戻し率は年度毎に異なるが、ここでは簡略化のため去年実績値の固定値とする。
     *
     * 医療保険：20%
     * がん保険：15%
     * 死亡保険：35%
     *
     * @param insuranceType 保険種別
     * @return 割戻し率
     */
    private double findRefundRate(Integer insuranceType) {
        double refundRate = switch (insuranceType) {
            case 1 -> IRYOU_REFUND_RATE;   // 医療保険
            case 2 -> SHIBOU_REFUND_RATE;  // 死亡保険
            case 3 -> GAN_REFUND_RATE;  // がん保険
            default -> throw new IllegalArgumentException("Invalid insurance type: " + insuranceType);
        };
        return refundRate;
    }
}
