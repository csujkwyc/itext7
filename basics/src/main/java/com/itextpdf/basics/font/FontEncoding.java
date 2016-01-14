package com.itextpdf.basics.font;

import com.itextpdf.basics.IntHashtable;
import com.itextpdf.basics.Utilities;

import java.util.StringTokenizer;

public class FontEncoding {

    private static final byte[] emptyBytes = new byte[0];

    public static String FontSpecific = "FontSpecific";
    /**
     * Base font encoding.
     */
    protected String baseEncoding;
    /**
     * {@code true} if the font must use its built in encoding. In that case
     * the {@code encoding} is only used to map a char to the position inside the font, not to the expected char name.
     */
    protected boolean fontSpecific;

    /**
     * Mapping map from unicode to simple code according to the encoding.
     */
    protected IntHashtable unicodeToCode;

    protected Integer[] codeToUnicode;

    /**
     * Encoding names.
     */
    protected String[] differences;
    /**
     * Encodings unicode differences
     */
    protected IntHashtable unicodeDifferences;

    protected FontEncoding() {
        unicodeToCode = new IntHashtable(256);
        codeToUnicode = new Integer[256];
        unicodeDifferences = new IntHashtable(256);
        fontSpecific = false;
    }

    public static FontEncoding createFontEncoding(String baseEncoding) {
        FontEncoding encoding = new FontEncoding();
        encoding.baseEncoding = normalizeEncoding(baseEncoding);
        if (encoding.baseEncoding.startsWith("#")) {
            encoding.fillCustomEncoding();
        } else {
            encoding.fillNamedEncoding();
        }
        return encoding;
    }

    /**
     * This encoding will base on font encoding (FontSpecific encoding in Type 1 terminology)
     */
    public static FontEncoding createFontSpecificEncoding() {
        FontEncoding encoding = new FontEncoding();
        encoding.fontSpecific = true;
        for (int ch = 0; ch < 256; ch++) {
            encoding.unicodeToCode.put(ch, ch);
            encoding.codeToUnicode[ch] = ch;
            encoding.unicodeDifferences.put(ch, ch);
        }
        return encoding;
    }

    public String getBaseEncoding() {
        return baseEncoding;
    }

    public boolean isFontSpecific() {
        return fontSpecific;
    }

    public Integer getUnicode(int index) {
        return codeToUnicode[index];
    }

    public int getUnicodeDifference(int index) {
        return unicodeDifferences.get(index);
    }

    public boolean hasDifferences() {
        return differences != null;
    }

    public String getDifferences(int index) {
        return differences != null ? differences[index] : null;
    }

    /**
     * Converts a {@code String} to a {@code byte} array according to the encoding.
     * String could contain a unicode symbols or font specific codes.
     *
     * @param text the {@code String} to be converted.
     * @return an array of {@code byte} representing the conversion according to the encoding
     */
    public byte[] convertToBytes(String text) {
        if (text == null || text.length() == 0) {
            return emptyBytes;
        }
        int ptr = 0;
        byte[] bytes = new byte[text.length()];
        for (int i = 0; i < text.length(); i++) {
            if (unicodeToCode.containsKey(text.charAt(i))) {
                bytes[ptr++] = convertToByte(text.charAt(i));
            }
        }
        return  Utilities.shortenArray(bytes, ptr);
    }

    /**
     * Converts a unicode symbol or font specific code
     * to {@code byte} according to the encoding.
     *
     * @param ch a unicode symbol or FontSpecif code to be converted.
     * @return a {@code byte} representing the conversion according to the encoding
     */
    public byte convertToByte(int ch) {
        return (byte) unicodeToCode.get(ch);
    }

    /**
     * Check whether a unicode symbol or font specific code can be converted
     * to {@code byte} according to the encoding.
     *
     * @param ch a unicode symbol or font specific code to be checked.
     * @return {@code true} if {@code ch}
     */
    public boolean canEncode(int ch) {
        return unicodeToCode.containsKey(ch);
    }

    protected void fillCustomEncoding() {
        differences = new String[256];
        StringTokenizer tok = new StringTokenizer(baseEncoding.substring(1), " ,\t\n\r\f");
        if (tok.nextToken().equals("full")) {
            while (tok.hasMoreTokens()) {
                String order = tok.nextToken();
                String name = tok.nextToken();
                char uni = (char) Integer.parseInt(tok.nextToken(), 16);
                Integer uniName = AdobeGlyphList.nameToUnicode(name);
                int orderK;
                if (order.startsWith("'")) {
                    orderK = order.charAt(1);
                } else {
                    orderK = Integer.parseInt(order);
                }
                orderK %= 256;
                unicodeToCode.put(uni, orderK);
                codeToUnicode[orderK] = (int) uni;
                differences[orderK] = name;
                unicodeDifferences.put(uni, uniName != null ? uniName : -1);
            }
        } else {
            int k = 0;
            if (tok.hasMoreTokens()) {
                k = Integer.parseInt(tok.nextToken());
            }
            while (tok.hasMoreTokens() && k < 256) {
                String hex = tok.nextToken();
                int uni = Integer.parseInt(hex, 16) % 0x10000;
                String name = AdobeGlyphList.unicodeToName(uni);
                if (name == null) {
                    name = "uni" + hex;
                }
                unicodeToCode.put(uni, k);
                codeToUnicode[k] = uni;
                differences[k] = name;
                unicodeDifferences.put(uni, uni);
                k++;
            }
        }
        for (int k = 0; k < 256; k++) {
            if (differences[k] == null) {
                differences[k] = FontConstants.notdef;
            }
        }
    }

    protected void fillNamedEncoding() {
        PdfEncodings.convertToBytes(" ", baseEncoding); // check if the encoding exists
        boolean stdEncoding = PdfEncodings.WINANSI.equals(baseEncoding) || PdfEncodings.MACROMAN.equals(baseEncoding);
        if (!stdEncoding && differences == null) {
            differences = new String[256];
        }

        byte[] b = new byte[256];
        for (int k = 0; k < 256; ++k) {
            b[k] = (byte) k;
        }
        String str = PdfEncodings.convertToString(b, baseEncoding);
        char[] encoded = str.toCharArray();
        for (int ch = 0; ch < 256; ++ch) {
            char uni = encoded[ch];
            String name = AdobeGlyphList.unicodeToName(uni);
            if (name == null) {
                name = FontConstants.notdef;
            } else {
                unicodeToCode.put(uni, ch);
                codeToUnicode[ch] = (int) uni;
                unicodeDifferences.put(uni, uni);
            }
            if (differences != null) {
                differences[ch] = name;
            }
        }
    }

    protected void fillStandardEncoding() {
        int[] encoded = PdfEncodings.standardEncoding;
        for (int ch = 0; ch < 256; ++ch) {
            int uni = encoded[ch];
            String name = AdobeGlyphList.unicodeToName(uni);
            if (name == null) {
                name = FontConstants.notdef;
            } else {
                unicodeToCode.put(uni, ch);
                codeToUnicode[ch] = (int) uni;
                unicodeDifferences.put(uni, uni);
            }
            if (differences != null) {
                differences[ch] = name;
            }
        }
    }

    /**
     * Normalize the encoding names. "winansi" is changed to "Cp1252" and
     * "macroman" is changed to "MacRoman".
     *
     * @param enc the encoding to be normalized
     * @return the normalized encoding
     */
    protected static String normalizeEncoding(String enc) {
        if (enc == null || enc.toLowerCase().equals("winansi") || enc.equals("")) {
            return PdfEncodings.WINANSI;
        } else if (enc.toLowerCase().equals("macroman")) {
            return PdfEncodings.MACROMAN;
        } else {
            return enc;
        }
    }
}
