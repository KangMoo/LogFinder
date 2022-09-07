import com.github.kangmoo.LogParser;
import org.junit.Test;

/**
 * @author kangmoo Heo
 */
public class LogParserTest {
    @Test
    public void test1(){
        LogParser.main(new String[]{
                "Example",
                "(?=\\[[\\w\\d\\-: \\.]+\\]\\[[\\w ]+\\] \\[[^ ]+?\\])"
        });
    }

    @Test
    public void test2(){
        LogParser.main(new String[]{
                "Example",
                "(?=\\[[\\w\\d\\-: \\.]+\\]\\[[\\w ]+\\] \\[[^ ]+?\\])",
                "INFO"
        });
    }
}
