

import java.util.function.Function;
public class LambdaTest {
	int offset = 100;
	Function<String, Integer> f = s -> Integer.parseInt(s) + offset;
	}
