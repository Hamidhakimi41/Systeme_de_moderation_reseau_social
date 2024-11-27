package fr.univ_lyon1.info.m1;

public class CharManipulator implements ICharManipulator {
    /**
     * @param s
     * @return String s with the order of its characters inverted
     */
    @Override
    public String invertOrder(String s) {
        if (s == null) {
            return null;
        }
        return new StringBuilder(s).reverse().toString();
    }

    /**
     * @param s
     * @return String s with the case of its characters inverted
     */
    @Override
    public String invertCase(String s) {
        if (s == null) {
            return null;
        }
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                chars[i] = Character.toLowerCase(c);
            } else if (Character.isLowerCase(c)) {
                chars[i] = Character.toUpperCase(c);
            }
        }
        return new String(chars);
    }
}