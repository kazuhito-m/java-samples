import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SurrogatePairsTest {

    private static final Logger log = LoggerFactory.getLogger(SurrogatePairsTest.class);

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
