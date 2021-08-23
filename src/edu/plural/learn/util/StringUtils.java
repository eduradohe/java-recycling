package edu.plural.learn.util;

import java.util.Optional;

public class StringUtils {

    public static String toString( final Object o ) {
        return o == null ? "(value not provided)" : o.toString();
    }

    public static boolean isNotNull( final String string ) {
        return !isNull(string);
    }

    public static boolean isNull( final String string ) {
        return string == null;
    }

    public static String trim( final String string ) {
        return isNotNull(string) ? string.trim() : Optional.ofNullable(string).orElse(new String());
    }

    public static boolean isEmpty( final String string ) {
        return isEmpty(string, Boolean.FALSE);
    }

    public static boolean trimmedIsEmpty( final String string ) {
        return isEmpty(string, Boolean.TRUE);
    }

    public static boolean isEmpty( final String string, final Boolean trimmed ) {
        return trimmed ?
                trim(string).isEmpty()
                :
                isNull(string) || string.isEmpty();
    }
}
