import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class WordLadderTest {

    @org.junit.jupiter.api.Test
    void generate_string() {
        Set<String> s = new TreeSet<String>();
        assertEquals("code->cade->cate->date->data", WordLadder.generate_string("code", "data"));
    }
}