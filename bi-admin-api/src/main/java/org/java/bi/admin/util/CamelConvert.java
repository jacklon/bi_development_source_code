package org.java.bi.admin.util;

public class CamelConvert {
    /**
     * 将以下划线分隔的数据库字段转换为驼峰风格的字符串
     * @param columnName
     * @return
     */
    public static String changeColumnToFieldName(String columnName) {
        String[] array = columnName.split("_");
        StringBuffer sb = null;
        for (String cn : array) {
            cn = cn.toLowerCase();
            if (sb == null) {
                sb = new StringBuffer(cn);
                continue;
            }
            sb.append(cn.substring(0, 1).toUpperCase()).append(cn.substring(1));
        }
        return sb.toString();
    }

    /**
     * 将驼峰风格的字符串转换为以下划线分隔的数据库字段
     *
     * @param fieldName
     * @return
     */
    public static String changeFieldToColumnName(String fieldName) {
        if (fieldName == null) {
            return null;
        }
        StringBuffer columnName = new StringBuffer();
        int length = fieldName.length();
        for (int i = 0; i < length; i++) {
            char c = fieldName.charAt(i);
            if ('A' <= c && 'Z' >= c) {
                columnName.append("_").append((char) (c + 32));
            } else {
                columnName.append(fieldName.charAt(i));
            }
        }
        return columnName.toString();
    }
}
