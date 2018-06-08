package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import models.Employee;
import utils.DBUtil;
import utils.PropertyUtils;

/**
 * 従業員情報 入力チェック
 * @author J.Tamura
 *
 */
public class EmployeeValidator {
    
    /**
     * 入力チェック
     * @param e
     * @param code_duplicate_check_flag
     * @param password_check_flag
     * @return
     */
    public static List<String> validate(Employee e, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();
        
        // 社員番号チェック
        validateCode(e.getCode(), code_duplicate_check_flag, errors);
        
        // 氏名チェック
        validateName(e.getName(), errors);
        
        // パスワードチェックフラグが True である場合
        if (password_check_flag) { 
            // パスワードチェック
            validatePassword(e.getPassword(), errors);
        }
        
        // 取得したエラー情報を返却
        return errors;

    }
    
    
    /**
     * 社員番号 入力チェック
     * @param code
     * @param code_duplicate_check_flag
     * @param errors
     */
    private static void validateCode(String code, Boolean code_duplicate_check_flag, List<String> errors) {
        
        // 未入力チェック
        if(StringUtils.isEmpty(code)) {
            errors.add("社員番号を入力してください。");
        }
        
        // 重複チェック
        if(code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            
            // 入力した社員番号のデータ数を取得
            long employees_count = (long)em.createNamedQuery(PropertyUtils.QRY_CHECK_REGISTERED_CODE, Long.class)
                                            .setParameter("code", code)
                                                .getSingleResult();
            em.close();
            // 1件以上データが存在する場合
            if (employees_count > 0) {
                errors.add("入力された社員番号の情報はすでに存在しています。");
            }
        }
    }
    
    /**
     * 氏名 入力チェック
     * @param name
     * @param errors
     */
    private static void validateName(String name, List<String> errors) {
        // 未入力チェック
        if(StringUtils.isEmpty(name)) {
            errors.add("氏名を入力してください。");
        }
    }

    /**
     * パスワード 入力チェック
     * @param password
     * @param errors
     */
    private static void validatePassword(String password, List<String> errors) {
        // 未入力チェック
        if(StringUtils.isEmpty(password)) {
            errors.add("パスワードを入力してください。");
        }
    }
}