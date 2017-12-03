package com.github.kazuhito_m.javasamples.surrogatepair;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.Deflater;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ZipInflateDeflateTest {

    @Test
    public void 文字列をURLEncodeしてDecodesして圧縮してbase64する() throws UnsupportedEncodingException {

        final String SAMPLE_TEXT = "@startuml\nclass 日本語\n@enduml";

        String encoded = URLEncoder.encode(SAMPLE_TEXT, "UTF-8");
        assertThat(encoded, is("%40startuml%0Aclass+%E6%97%A5%E6%9C%AC%E8%AA%9E%0A%40enduml"));

        String decoded = URLDecoder.decode(encoded, "UTF-8");
        assertThat(decoded, is("@startuml\nclass 日本語\n@enduml"));

        Deflater deflater = new Deflater(9);
        deflater.setInput(decoded.getBytes());
        deflater.finish();
        byte[] output = new byte[100];
        int size = deflater.deflate(output);
        byte[] deflatedBytes = Arrays.copyOfRange(output, 0, size);
        String deflated = new String(deflatedBytes);
        assertThat(deflated.length(), is(38));

        String base64 = Base64.getEncoder().encodeToString(deflatedBytes);
        assertThat(base64, is("eNpzKC5JLCopzc3hSs5JLC5WeDZ96bM5a16smsflkJqXAhQHAPp1D0w="));
    }

    @Test
    public void plantUmlのサイトのJSと同じ処理をエミュレーションする() throws UnsupportedEncodingException {

        final String SAMPLE_TEXT = "@startuml\nclass 日本語\n@enduml";

        String encoded = URLEncoder.encode(SAMPLE_TEXT, "UTF-8");
        assertThat(encoded, is("%40startuml%0Aclass+%E6%97%A5%E6%9C%AC%E8%AA%9E%0A%40enduml"));

        String decoded = URLDecoder.decode(encoded, "ISO-8859-1");
        assertThat(decoded, is("@startuml\nclass æ\u0097¥æ\u009C¬èª\u009E\n@enduml"));

        Deflater deflater = new Deflater(9);
        deflater.setInput(decoded.getBytes());
        deflater.finish();
        byte[] output = new byte[100];
        int size = deflater.deflate(output);
        byte[] deflatedBytes = Arrays.copyOfRange(output, 0, size);
        String deflated = new String(deflatedBytes, "ISO-8859-1");
        System.out.println("deflated:" + deflated);
        assertThat(deflated, is("s(.I,*)ÍÍáJÎI,.Vx6}é³9k^¬\u009AÇå\u0090\u009A\u0097\u0002\u0014\u0007"));

        SpecialEncoderForPlantUmlSite encoder = new SpecialEncoderForPlantUmlSite();
        String base64 = encoder.encode64(deflated);
        assertThat(base64, is("SoWkIImgAStDuKhEIImkLdWsVUcpEMjUh9h7vP2Qbm8K1m00"));
    }


}
