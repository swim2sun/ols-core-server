package com.thoughtworks.nho;


import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UserServiceApplicationTests {

    @Test
    void should_compare_number_correctly() {
        assertThat(2, is(1));
    }

}
