package com.project.ebank;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class EbankingBackApplicationTests {

	@Test
	void adding_whenValuesAreValid_shouldreturnAddedValue() {
int val1 = 21;
int val2 = 21;
int result = val1+val2;
Assertions.assertThat(result).isEqualTo(42);
	}

}
