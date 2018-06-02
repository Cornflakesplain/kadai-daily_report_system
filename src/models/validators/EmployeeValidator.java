package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;
import utils.PropertyUtils;

public class EmployeeValidator {
    public static List<String> validate(Employee e, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();
        
        String code_error = _validateCode(e.getCode(), code_duplicate_check_flag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }
        
        String name_error = _validateName(e.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }
        
        if (password_check_flag) { 
            String password_error = _validatePassword(e.getPassword());
            if(!password_error.equals("")) {
                errors.add(password_error);
            }
        }

        return errors;

    }
    
    private static String _validateCode(String code, Boolean code_duplicate_check_flag) {
        if(code == null || code.equals("")) {
            return "社員番号を入力してください。";
        }
        
        if(code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long employees_count = (long)em.createNamedQuery(PropertyUtils.QRY_CHECK_REGISTERED_CODE, Long.class)
                                            .setParameter("code", code)
                                                .getSingleResult();
            em.close();
            if (employees_count > 0) {
                return "入力された社員番号の情報はすでに存在しています。";
            }
        }
        
        return "";
    }
    private static String _validateName(String name) {
        if(name == null || name.equals("")) {
            return "氏名を入力してください。";
        }

        return "";
    }

    private static String _validatePassword(String password) {
        if(password == null || password.equals("")) {
            return "パスワードを入力してください。";
        }
        return "";
    }
}