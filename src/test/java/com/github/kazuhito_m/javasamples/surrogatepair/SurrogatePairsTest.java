package com.github.kazuhito_m.javasamples.surrogatepair;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * http://www.kanazawa-net.ne.jp/~pmansato/others/other_surrogate.htm
 * http://blog.unfindable.net/archives/3406
 * https://www.ibm.com/developerworks/jp/ysl/library/java/j-unicode_surrogate/index.html
 */
public class SurrogatePairsTest {

    private static final Logger log = LoggerFactory.getLogger(SurrogatePairsTest.class);

    /**
     * https://kadoppe.com/archives/2011/03/java-string-char-conversio.html
     * https://qiita.com/msakamoto_sf/items/bdc62f95f6d342705001
     */
    @Test
    public void 文字コード指定でサロゲートペア文字列を作成する() {
        // サカナ偏に花
        char[] surrogatePairChars = new char[]{0xd867, 0xde3d};
        String surrogatePair = String.valueOf(surrogatePairChars);
        log.info(surrogatePair);
        assertThat(surrogatePair, is("𩸽"));

        // デザレット文字 Aを逆にしてひねったようなヤツ http://www.asahi-net.or.jp/~ax2s-kmtn/ref/unicode/u10400.html
        surrogatePairChars = new char[]{0xd801, 0xdc37};
        surrogatePair = String.valueOf(surrogatePairChars);
        log.info(surrogatePair);
    }

    /**
     * http://blog.sarabande.jp/post/103624680728
     * https://diary.sshida.com/20150317-19-サロゲートペア文字の例%20-%20%26#x20bb7; (U%2B20b
     */
    @Test
    public void コードポイントからサロゲートペア文字を作成する() {
        String surrogatePair = "𩸽";
        int codePoint = surrogatePair.codePointAt(0);
        assertThat(codePoint, is(0x29e3d));

        String restoration = new String(Character.toChars(codePoint));
        assertThat(restoration, is(surrogatePair));
    }

    private boolean hasSurrogatePair(String text) {
        return text.length() != text.codePoints().toArray().length;
    }

    @Test
    public void 文字列中にサロゲートペア文字列が含まれているか確認する() {
        boolean hasSurrogatePair = hasSurrogatePair("abc𩸽def");
        assertThat(hasSurrogatePair, is(true));

        hasSurrogatePair = hasSurrogatePair("abc三浦def");
        assertThat(hasSurrogatePair, is(false));
    }

}
