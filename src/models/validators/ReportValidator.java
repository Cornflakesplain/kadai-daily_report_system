package models.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import models.Report;


/**
 * レポート 入力チェック
 * @author J.Tamura
 *
 */
public class ReportValidator {
    
    /**
     * 入力チェック
     * @param r
     * @return
     */
    public static List<String> validate(Report r) {
        List<String> errors = new ArrayList<String>();

        // タイトル 入力チェック
        validateTitle(r.getTitle(), errors);
       
        // 内容 入力チェック
        validateContent(r.getContent(), errors);
        
        return errors;
    }

    
    /**
     * タイトル 入力チェック
     * @param title
     * @param errors
     * @return
     */
    private static String validateTitle(String title, List<String> errors) {
        if(StringUtils.isEmpty(title)) {
            errors.add("タイトルを入力してください。");
            }

        return "";
    }

    /**
     * 内容 入力チェック
     * @param content
     * @param errors
     */
    private static void validateContent(String content, List<String> errors) {
        if(StringUtils.isEmpty(content)) {
            errors.add("内容を入力してください。");
            }
    }
}