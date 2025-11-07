package com.tiscon11.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.tiscon11.domain.InsuranceOrder;
import com.tiscon11.domain.InsuranceType;

/**
 * 保険見積もり機能においてDBとのやり取りを行うクラス。
 *
 * @author TIS Taro
 */
@Component
public class EstimateDao {

    /**
     * データベース・アクセスAPIである「JDBC」を使い、名前付きパラメータを用いてSQLを実行するクラス
     */
    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    /**
     * 保険種別テーブルに登録されているすべての保険種別を取得する。
     *
     * @return すべての保険種別
     */
    public List<InsuranceType> getAllInsurances() {
        String sql = "SELECT INSURANCE_TYPE, INSURANCE_NAME, MONTHLY_FEE FROM INSURANCE_TYPE";
        return parameterJdbcTemplate.query(sql, DataClassRowMapper.newInstance(InsuranceType.class));
    }

    /**
     * 保険種別名を取得する。
     *
     * @param insuranceType 保険種別タイプ
     * @return 保険種別名
     */
    public String findInsuranceName(Integer insuranceType) {
        String sql = "SELECT INSURANCE_NAME FROM INSURANCE_TYPE WHERE INSURANCE_TYPE = :insuranceType";
        SqlParameterSource paramSource = new MapSqlParameterSource("insuranceType", insuranceType);
        return parameterJdbcTemplate.queryForObject(sql, paramSource, String.class);
    }

    /**
     * ユーザーが選択した保険種別の保険料を取得する。
     *
     * @param insuranceType 保険種別タイプ
     * @return 保険料
     */
    public int findMonthlyFee(Integer insuranceType) {
        String sql = "SELECT MONTHLY_FEE FROM INSURANCE_TYPE WHERE INSURANCE_TYPE = :insuranceType";
        SqlParameterSource paramSource = new MapSqlParameterSource("insuranceType", insuranceType);
        return parameterJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
    }

    /**
     * ユーザーの年齢に合致する調整率を取得する。
     *
     * @param age 年齢
     * @return 保険料年齢別調整率
     */
    public double findAdjustmentRateByAge(int age) {
        String sql = "SELECT ADJUSTMENT_RATE FROM AGE_ADJUSTMENT_RATE WHERE AGE = :age";
        SqlParameterSource paramSource = new MapSqlParameterSource("age", age);
        return parameterJdbcTemplate.queryForObject(sql, paramSource, Double.class);
    }

    /**
     * データベースに見積もり依頼を登録する。
     *
     * @param insuranceOrder 見積もり依頼情報
     */
    public void insertInsuranceOrder(InsuranceOrder insuranceOrder) {
        String sql = """
            INSERT INTO INSURANCE_ORDER(
                INSURANCE_TYPE, KANJI_NAME, KANA_NAME, DATE_OF_BIRTH, ADDRESS, TEL, EMAIL_ADDRESS, MARRIED, JOB, INCOME, DISCOVERY_SOURCE, TREATED, MEDICAL_HISTORY
            )
            VALUES(
                :insuranceType, :kanjiName, :kanaName, :dateOfBirth, :address, :tel, :email, :marriedType, :jobType, :income, :discoverySourceType, :treatedType, :medicalHistory
            )
            """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(insuranceOrder), keyHolder);
    }
}
