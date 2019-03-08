import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordLadderTest {

    @Test
    void generate_string() {
        assertEquals("code->cade->cate->date->data", WordLadder.generate_string("code", "data"));
    }
}